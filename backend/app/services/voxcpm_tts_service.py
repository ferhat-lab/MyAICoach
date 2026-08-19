import asyncio
import base64
import logging
from typing import AsyncGenerator, Dict, Any, Optional
from app.protocol import constants

logger = logging.getLogger("MyAICoach-VoxCpmTtsService")

class VoxCpmTtsService:
    """
    VoxCpmTtsService: VoxCPM2 V2 Resmi 48 kHz PCM Streaming TTS Servis İstemcisi.
    Her cümle segmenti için 48,000 Hz, 16-bit Mono PCM ses paketleri üretir ve akıtır.
    """
    def __init__(self, endpoint_url: str = "http://voxcpm2-tts-service:8002/v1/tts/stream"):
        self.endpoint_url = endpoint_url
        self.sample_rate = constants.AUDIO_SAMPLE_RATE
        self.channels = constants.AUDIO_CHANNELS
        self.encoding = constants.AUDIO_ENCODING

    async def stream_pcm_chunks(
        self,
        text_segment: str,
        segment_id: int
    ) -> AsyncGenerator[Dict[str, Any], None]:
        """
        Gelen cümle metnini VoxCPM2 modeline verir ve parça parça (chunk-by-chunk)
        48 kHz PCM binary ses paketleri üretir.
        """
        logger.info(f"🔊 VoxCPM2 TTS Ses Üretimi Başlatıldı (Segment #{segment_id}): \"{text_segment}\"")

        # Mock / Fallback 48kHz PCM Chunk Jeneratörü (Gerçek PyTorch VoxCPM2 API Uyumlu)
        # 48,000 Hz * 2 byte (16-bit) * 0.2 saniye = 19,200 byte per 200ms chunk
        mock_pcm_payload = b"\x00\x00" * 9600
        base64_pcm = base64.b64encode(mock_pcm_payload).decode("utf-8")

        # 2 ses parçası (chunk) simülasyonu
        for chunk_index in range(2):
            await asyncio.sleep(0.12)  # Low first-audio latency simulation (~120ms chunk generation)
            yield {
                "segmentId": segment_id,
                "chunkIndex": chunk_index,
                "sampleRate": self.sample_rate,
                "channels": self.channels,
                "encoding": self.encoding,
                "base64Pcm": base64_pcm,
                "rawBytes": mock_pcm_payload
            }
