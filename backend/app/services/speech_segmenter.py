import re
import logging
from typing import AsyncGenerator, List

logger = logging.getLogger("MyAICoach-SpeechSegmenter")

class ServerSpeechSegmenter:
    """
    ServerSpeechSegmenter: Qwen3 LLM token akışını dinleyen ve nokta/ünlem/soru işareti ('.', '!', '?', '\n')
    gördüğü anda cümleyi kesip VoxCPM2 TTS'e ANINDA fırlatan gecikme önleyici cümle bölümleme motoru.
    """
    def __init__(self, punctuation_regex: str = r"[.!?;\n]"):
        self.punctuation_regex = re.compile(punctuation_regex)
        self.buffer = ""

    def process_token(self, token: str) -> List[str]:
        """
        Gelen her token'ı tampona ekler. Eğer cümle/yan cümle bitiş noktası geldiyse
        tamamlanan cümle segmentlerini liste olarak döner.
        """
        self.buffer += token
        completed_segments = []

        # Tamamlanan cümleleri regex ile bul
        matches = list(self.punctuation_regex.finditer(self.buffer))
        if matches:
            last_end = 0
            for match in matches:
                end_pos = match.end()
                segment = self.buffer[last_end:end_pos].strip()
                if segment:
                    completed_segments.append(segment)
                last_end = end_pos

            # Tamponda kalan tamamlanmamış parçayı muhafaza et
            self.buffer = self.buffer[last_end:]

        return completed_segments

    def flush(self) -> str:
        """
        Akış bittiğinde tamponda kalan son parçayı temizler ve döner.
        """
        remaining = self.buffer.strip()
        self.buffer = ""
        return remaining
