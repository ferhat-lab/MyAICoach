import logging
from fastapi import FastAPI, WebSocket
from app.protocol import constants
from app.guards.turn_guard import ServerTurnGuard
from app.orchestration.turn_orchestrator import TurnOrchestrator
from app.websocket.voice_stream import VoiceStreamHandler

logging.basicConfig(level=logging.INFO, format="%(asctime)s [%(levelname)s] %(name)s - %(message)s")
logger = logging.getLogger("MyAICoach-Main")

app = FastAPI(
    title="MyAICoach Voice Gateway",
    version="1.0.0",
    description="FAZ 3 Real-time Dual-Stream WebSocket Gateway with Server-side TurnGuard and 48kHz PCM Support"
)

# Servis Bağımlılıkları
turn_guard = ServerTurnGuard(min_turn_interval_ms=300)
turn_orchestrator = TurnOrchestrator(turn_guard=turn_guard)
voice_stream_handler = VoiceStreamHandler(orchestrator=turn_orchestrator)

@app.get("/health")
async def health_check():
    """
    Health Check Endpoint (Model servislerinden ve DB'den bağımsızdır).
    """
    return {
        "status": "ok",
        "service": "myaicoach-voice-gateway",
        "protocolVersion": constants.PROTOCOL_VERSION
    }

@app.websocket("/v1/voice/stream")
async def voice_stream_endpoint(websocket: WebSocket):
    """
    Real-time Dual-Stream WebSocket Endpoint.
    Android istemcisinden gelen text JSON event'leri ve binary PCM paketlerini işler.
    """
    await voice_stream_handler.handle_connection(websocket)
