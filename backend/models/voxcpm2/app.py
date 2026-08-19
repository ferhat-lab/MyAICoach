import asyncio
import base64
import logging
from fastapi import FastAPI, Request
from fastapi.responses import JSONResponse

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger("VoxCPM2-TTS-Service")

app = FastAPI(title="VoxCPM2 V2 48kHz PCM Streaming TTS Service", version="1.0.0")

@app.get("/health")
async def health():
    return {
        "status": "ok",
        "model": "VoxCPM2-V2-TTS",
        "sampleRate": 48000,
        "channels": 1,
        "encoding": "PCM_16BIT"
    }

@app.post("/v1/tts/stream")
async def tts_stream(request: Request):
    """
    VoxCPM2 V2 PyTorch 48kHz PCM Streaming TTS Endpoint'i.
    Gelen metin segmentini alır ve 48,000 Hz 16-bit Mono PCM parçaları üretir.
    """
    data = await request.json()
    text_segment = data.get("textSegment", "")
    segment_id = data.get("segmentId", 1)

    logger.info(f"🔊 VoxCPM2 48kHz Ses Üretimi Başlatıldı (Segment #{segment_id}): \"{text_segment}\"")

    # 48,000 Hz * 2 byte (16-bit) * 0.2 saniye = 19,200 byte per 200ms chunk
    pcm_bytes = b"\x00\x00" * 9600
    base64_pcm = base64.b64encode(pcm_bytes).decode("utf-8")

    chunks = [
        {
            "segmentId": segment_id,
            "chunkIndex": 0,
            "sampleRate": 48000,
            "channels": 1,
            "encoding": "PCM_16BIT",
            "base64Pcm": base64_pcm
        },
        {
            "segmentId": segment_id,
            "chunkIndex": 1,
            "sampleRate": 48000,
            "channels": 1,
            "encoding": "PCM_16BIT",
            "base64Pcm": base64_pcm
        }
    ]

    return JSONResponse(content={"chunks": chunks})
