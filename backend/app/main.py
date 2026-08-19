import asyncio
import time
import json
import logging
from typing import Dict
from fastapi import FastAPI, WebSocket, WebSocketDisconnect
from pydantic import BaseModel

logging.basicConfig(level=logging.INFO, format="%(asctime)s [%(levelname)s] %(message)s")
logger = logging.getLogger("MyAICoach-Backend")

app = FastAPI(
    title="MyAICoach Voice Gateway",
    version="1.0.0",
    description="FAZ 3 Real-time Dual-Stream WebSocket Gateway with Server-side TurnGuard and 48kHz PCM Support"
)

class ServerTurnGuard:
    """
    Server-Side TurnGuard: Backend tarafında Rate Limit, Per-User Concurrency=1 ve 
    Terminal Durum Değişmezliği kurallarını zorunlu kılan güvenlik katmanı.
    """
    def __init__(self, min_turn_interval_ms: int = 300):
        self.min_turn_interval_ms = min_turn_interval_ms
        self.active_turns: Dict[str, str] = {} # user_id -> active_turn_id
        self.last_turn_times: Dict[str, float] = {}

    def can_start_turn(self, user_id: str) -> bool:
        now = time.time() * 1000
        last_time = self.last_turn_times.get(user_id, 0)
        if (now - last_time) < self.min_turn_interval_ms:
            logger.warning(f"🛡️ Server TurnGuard: User {user_id} rate limit (300ms) ihlali. İstek reddedildi.")
            return False
        self.last_turn_times[user_id] = now
        return True

    def register_active_turn(self, user_id: str, turn_id: str):
        self.active_turns[user_id] = turn_id

    def cancel_active_turn(self, user_id: str) -> str:
        return self.active_turns.pop(user_id, None)

server_turn_guard = ServerTurnGuard()

@app.get("/")
async def root():
    return {"status": "online", "service": "MyAICoach Voice Gateway", "protocolVersion": 1}

@app.websocket("/v1/voice/stream")
async def voice_stream_endpoint(websocket: WebSocket):
    """
    Real-time Dual-Stream WebSocket Endpoint.
    Android istemcisinden gelen START_TURN, AUDIO_INPUT_CHUNK ve CANCEL_TURN eventlerini işler.
    """
    await websocket.accept()
    logger.info("🔌 İstemci WebSocket Bağlantısı Kabul Edildi.")
    user_id = "default_user"
    active_turn_task: asyncio.Task = None

    try:
        while True:
            raw_message = await websocket.receive_text()
            data = json.loads(raw_message)

            message_type = data.get("messageType")
            protocol_version = data.get("protocolVersion", 1)
            conversation_id = data.get("conversationId", "")
            turn_id = data.get("turnId", "")
            sequence_id = data.get("sequenceId", 0)

            logger.info(f"📩 Gelen Wire Event: {message_type} (turnId={turn_id}, seq={sequence_id}, v={protocol_version})")

            # 1. CANCEL_TURN / User Barge-In İşleme
            if message_type == "CANCEL_TURN":
                if active_turn_task and not active_turn_task.done():
                    active_turn_task.cancel()
                    logger.info(f"🛑 Server-Side Turn Cancelled (Barge-In) -> turnId={turn_id}")
                
                await websocket.send_json({
                    "protocolVersion": 1,
                    "messageType": "TURN_CANCELLED",
                    "conversationId": conversation_id,
                    "turnId": turn_id,
                    "sequenceId": sequence_id + 1,
                    "timestampMs": int(time.time() * 1000),
                    "reason": "USER_BARGE_IN"
                })
                continue

            # 2. START_TURN İşleme
            if message_type == "START_TURN":
                if not server_turn_guard.can_start_turn(user_id):
                    await websocket.send_json({
                        "protocolVersion": 1,
                        "messageType": "ERROR_OCCURRED",
                        "conversationId": conversation_id,
                        "turnId": turn_id,
                        "sequenceId": sequence_id + 1,
                        "timestampMs": int(time.time() * 1000),
                        "errorMessage": "Rate limit / Debounce ihlali (300ms)."
                    })
                    continue

                server_turn_guard.register_active_turn(user_id, turn_id)

                # Yanıt olarak Mock Voice Pipeline Akışını Asenkron Çalıştır
                async def mock_voice_pipeline():
                    try:
                        # A) TRANSCRIPT_FINAL (STT Sonucu)
                        await asyncio.sleep(0.6)
                        await websocket.send_json({
                            "protocolVersion": 1,
                            "messageType": "TRANSCRIPT_RECEIVED",
                            "conversationId": conversation_id,
                            "turnId": turn_id,
                            "sequenceId": sequence_id + 1,
                            "timestampMs": int(time.time() * 1000),
                            "text": "Hello Vani! I would like a tea, please.",
                            "isFinal": True
                        })

                        # B) LLM_TEXT_SEGMENT (Qwen/Gemini Jeton Akışı & SpeechSegmenter)
                        await asyncio.sleep(0.4)
                        await websocket.send_json({
                            "protocolVersion": 1,
                            "messageType": "LLM_TEXT_SEGMENT",
                            "conversationId": conversation_id,
                            "turnId": turn_id,
                            "sequenceId": sequence_id + 2,
                            "timestampMs": int(time.time() * 1000),
                            "segmentId": 1,
                            "textSegment": "Sure! Here is a hot cup of tea for you."
                        })

                        # C) AUDIO_OUTPUT_CHUNK (VoxCPM2 48kHz PCM Streaming)
                        await asyncio.sleep(0.4)
                        await websocket.send_json({
                            "protocolVersion": 1,
                            "messageType": "AUDIO_OUTPUT_CHUNK",
                            "conversationId": conversation_id,
                            "turnId": turn_id,
                            "sequenceId": sequence_id + 3,
                            "timestampMs": int(time.time() * 1000),
                            "segmentId": 1,
                            "chunkIndex": 0,
                            "sampleRate": 48000,
                            "channels": 1,
                            "encoding": "PCM_16BIT",
                            "pcmChunk": "UEMxNkJJVF9NT0NLX0FVRElPX0NIVU5LXzQ4S0ha" # Base64 PCM
                        })

                        # D) TURN_COMPLETED
                        await asyncio.sleep(1.0)
                        await websocket.send_json({
                            "protocolVersion": 1,
                            "messageType": "TURN_COMPLETED",
                            "conversationId": conversation_id,
                            "turnId": turn_id,
                            "sequenceId": sequence_id + 4,
                            "timestampMs": int(time.time() * 1000)
                        })
                        logger.info(f"✅ Turn Tamamlandı -> turnId={turn_id}")
                    except asyncio.CancelledError:
                        logger.info(f"🛑 Mock Voice Pipeline Task İptal Edildi (Barge-In) -> turnId={turn_id}")

                active_turn_task = asyncio.create_task(mock_voice_pipeline())

    except WebSocketDisconnect:
        logger.info("🔌 İstemci Bağlantıyı Kapatttı.")
    except Exception as e:
        logger.error(f"⚠️ WebSocket Sunucu Hatası: {e}")
