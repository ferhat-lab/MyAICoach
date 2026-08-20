import os
import base64
import logging
from typing import AsyncGenerator, Dict, Any, Optional
import httpx
from app.protocol import constants

logger = logging.getLogger("MyAICoach-VoxCpmTtsService")

class VoxCpmTtsService:
    """
    VoxCpmTtsService: VoxCPM2 V2 Gerçek 48 kHz PCM Streaming TTS Servis İstemcisi.
    Her cümle segmenti için voxcpm2_tts_service sunucusuna HTTP isteği gönderir
    ve 48,000 Hz, 16-bit Mono PCM ses paketlerini (chunks) alır.
    """
    def __init__(self, endpoint_url: Optional[str] = None):
        self.endpoint_url = endpoint_url or os.getenv(
            "VOXCPM_SERVICE_URL",
            "http://voxcpm2_tts_service:8002/v1/tts/stream"
        )
        self.sample_rate = constants.AUDIO_SAMPLE_RATE
        self.channels = constants.AUDIO_CHANNELS
        self.encoding = constants.AUDIO_ENCODING

    async def stream_pcm_chunks(
        self,
        text_segment: str,
        segment_id: int
    ) -> AsyncGenerator[Dict[str, Any], None]:
        """
        Gelen metin segmentini gerçek VoxCPM2 TTS servisine iletir,
        dönen 48 kHz PCM parçalarını çözer ve TurnOrchestrator'a iletir.
        """
        logger.info(f"🔊 Gerçek VoxCPM2 TTS İstemcisi Tetiklendi ({self.endpoint_url}) [Segment #{segment_id}]: \"{text_segment}\"")

        payload = {
            "textSegment": text_segment,
            "segmentId": segment_id
        }

        try:
            async with httpx.AsyncClient(timeout=30.0) as client:
                response = await client.post(self.endpoint_url, json=payload)
                if response.status_code != 200:
                    logger.error(f"⚠️ VoxCPM2 TTS Servis Hatası (Status {response.status_code})")
                    return

                res_data = response.json()
                chunks = res_data.get("chunks", [])

                for chunk in chunks:
                    b64_pcm = chunk.get("base64Pcm", "")
                    raw_bytes = base64.b64decode(b64_pcm) if b64_pcm else b""

                    yield {
                        "segmentId": chunk.get("segmentId", segment_id),
                        "chunkIndex": chunk.get("chunkIndex", 0),
                        "sampleRate": chunk.get("sampleRate", self.sample_rate),
                        "channels": chunk.get("channels", self.channels),
                        "encoding": chunk.get("encoding", self.encoding),
                        "base64Pcm": b64_pcm,
                        "rawBytes": raw_bytes
                    }

        except httpx.ConnectError:
            logger.warning(f"⚠️ VoxCPM2 TTS servisine ulaşılamadı ({self.endpoint_url}). Fallback 48kHz PCM kullanılıyor.")
            mock_payload = b"\x00\x00" * 9600
            b64_pcm = base64.b64encode(mock_payload).decode("utf-8")
            yield {
                "segmentId": segment_id,
                "chunkIndex": 0,
                "sampleRate": self.sample_rate,
                "channels": self.channels,
                "encoding": self.encoding,
                "base64Pcm": b64_pcm,
                "rawBytes": mock_payload
            }
        except Exception as e:
            logger.error(f"❌ VoxCpmTtsService İstek Hatası: {e}")
