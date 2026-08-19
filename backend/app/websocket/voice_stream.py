import json
import logging
from fastapi import WebSocket, WebSocketDisconnect
from app.protocol import constants
from app.protocol.models import (
    TurnCancelledMessage,
    ErrorOccurredMessage
)
from app.orchestration.turn_orchestrator import TurnOrchestrator

logger = logging.getLogger("MyAICoach-VoiceStream")

class VoiceStreamHandler:
    """
    VoiceStreamHandler: WebSocket canlı akış bağlantısını, JSON text frame ve Binary PCM frame
    ayrımını yöneten işleyici.
    """
    def __init__(self, orchestrator: TurnOrchestrator):
        self.orchestrator = orchestrator

    async def handle_connection(self, websocket: WebSocket):
        await websocket.accept()
        logger.info("🔌 İstemci WebSocket Bağlantısı Kabul Edildi (/v1/voice/stream)")

        async def send_fn(data: dict):
            try:
                await websocket.send_json(data)
            except Exception as e:
                logger.error(f"⚠️ WebSocket Mesaj Gönderme Hatası: {e}")

        try:
            while True:
                # Text Frame (JSON) ve Binary Frame (Raw PCM) ayrımı
                message = await websocket.receive()

                if "text" in message and message["text"] is not None:
                    await self._handle_text_frame(message["text"], send_fn)
                elif "bytes" in message and message["bytes"] is not None:
                    await self._handle_binary_frame(message["bytes"])

        except WebSocketDisconnect:
            logger.info("🔌 İstemci WebSocket Bağlantıyı Kapattı.")
        except Exception as e:
            logger.error(f"⚠️ Unexpected WebSocket Error: {e}")

    async def _handle_text_frame(self, text_payload: str, send_fn):
        try:
            data = json.loads(text_payload)
        except Exception as e:
            logger.warning(f"⚠️ Geçersiz JSON formatı: {e}")
            err_msg = ErrorOccurredMessage(
                conversationId="unknown",
                turnId="unknown",
                sequenceId=0,
                errorCode=constants.ERR_INVALID_MESSAGE,
                errorMessage="Geçersiz JSON paketi alındı.",
                retryable=False
            )
            await send_fn(err_msg.model_dump())
            return

        protocol_version = data.get("protocolVersion")
        message_type = data.get("messageType")
        conversation_id = data.get("conversationId", "")
        turn_id = data.get("turnId", "")
        sequence_id = data.get("sequenceId", 0)

        # 1. Protocol Version Validation
        if protocol_version != constants.PROTOCOL_VERSION:
            logger.warning(f"⚠️ Desteklenmeyen Protokol Versiyonu: {protocol_version} (Beklenen: {constants.PROTOCOL_VERSION})")
            err_msg = ErrorOccurredMessage(
                conversationId=conversation_id,
                turnId=turn_id,
                sequenceId=sequence_id + 1,
                errorCode=constants.ERR_PROTOCOL_VERSION_UNSUPPORTED,
                errorMessage=f"Desteklenmeyen protocolVersion: {protocol_version}. Beklenen: {constants.PROTOCOL_VERSION}.",
                retryable=False
            )
            await send_fn(err_msg.model_dump())
            return

        logger.info(f"📩 Text Frame: {message_type} (convId={conversation_id}, turnId={turn_id}, seq={sequence_id})")

        # 2. Event Routing
        if message_type == constants.MSG_START_TURN:
            await self.orchestrator.handle_start_turn(
                conversation_id=conversation_id,
                turn_id=turn_id,
                initial_sequence_id=sequence_id,
                send_message_fn=send_fn
            )

        elif message_type == constants.MSG_END_USER_SPEECH:
            await self.orchestrator.handle_end_user_speech(
                conversation_id=conversation_id,
                turn_id=turn_id,
                send_message_fn=send_fn
            )

        elif message_type == constants.MSG_CANCEL_TURN:
            reason = data.get("reason", "USER_BARGE_IN")
            cancelled = self.orchestrator.cancel_turn(conversation_id, turn_id, reason=reason)
            
            cancel_msg = TurnCancelledMessage(
                conversationId=conversation_id,
                turnId=turn_id,
                sequenceId=sequence_id + 1,
                reason=reason
            )
            await send_fn(cancel_msg.model_dump())

        elif message_type == constants.MSG_AUDIO_INPUT_CHUNK:
            # LEGACY Base64 JSON audio chunk
            pass

    async def _handle_binary_frame(self, pcm_bytes: bytes):
        """
        BINARY WEBSOCKET FRAME HAZIRLIĞI:
        JSON AUDIO_INPUT_CHUNK_METADATA sonrası hemen gelen raw PCM parçası.
        """
        logger.debug(f"🎙️ Binary PCM Frame Alındı: {len(pcm_bytes)} bytes (Raw PCM 48kHz Mono)")
