import time
import logging
from typing import Dict, Optional, Tuple

logger = logging.getLogger("MyAICoach-TurnGuard")

class ServerTurnGuard:
    """
    ServerTurnGuard: Backend seviyesinde Concurrency, Rate Limiting ve Tur İzolasyonu Güvenlik Katmanı.
    
    NOT: Bu sınıf in-memory bir iskelet uygulamasıdır. Üretim ortamında (Production) Redis / Distributed Lock,
    Sliding-Window Rate Limiter, Compute Quota ve Cancel Budget mekanizmaları eklenecek şekilde tasarlanmıştır.
    """
    def __init__(self, min_turn_interval_ms: int = 300):
        self.min_turn_interval_ms = min_turn_interval_ms
        # conversation_id -> (turn_id, start_timestamp_ms)
        self.active_turns: Dict[str, Tuple[str, float]] = {}
        # conversation_id -> last_turn_start_ms
        self.last_turn_timestamps: Dict[str, float] = {}

    def can_start_turn(self, conversation_id: str, turn_id: str) -> Tuple[bool, Optional[str]]:
        now_ms = time.time() * 1000

        # 1. Active Turn Concurrency = 1 Kontrolü
        if conversation_id in self.active_turns:
            active_turn_id, _ = self.active_turns[conversation_id]
            if active_turn_id != turn_id:
                logger.warning(f"🛡️ ServerTurnGuard: Oturumda ({conversation_id}) zaten aktif bir tur var ({active_turn_id}). Yeni tur reddedildi.")
                return False, "TURN_ALREADY_ACTIVE"

        # 2. Debounce / Rate-Limit Kontrolü (300ms)
        last_time = self.last_turn_timestamps.get(conversation_id, 0)
        if (now_ms - last_time) < self.min_turn_interval_ms:
            logger.warning(f"🛡️ ServerTurnGuard: Debounce ihlali ({now_ms - last_time:.1f}ms < {self.min_turn_interval_ms}ms). İstek reddedildi.")
            return False, "TURN_RATE_LIMITED"

        return True, None

    def register_turn(self, conversation_id: str, turn_id: str):
        now_ms = time.time() * 1000
        self.active_turns[conversation_id] = (turn_id, now_ms)
        self.last_turn_timestamps[conversation_id] = now_ms
        logger.info(f"🛡️ ServerTurnGuard: Tur Kaydedildi ({conversation_id} -> {turn_id})")

    def clear_turn(self, conversation_id: str, turn_id: str):
        if conversation_id in self.active_turns:
            active_turn_id, _ = self.active_turns[conversation_id]
            if active_turn_id == turn_id:
                del self.active_turns[conversation_id]
                logger.info(f"🛡️ ServerTurnGuard: Tur Temizlendi ({conversation_id} -> {turn_id})")

    def is_turn_active(self, conversation_id: str, turn_id: str) -> bool:
        if conversation_id not in self.active_turns:
            return False
        active_turn_id, _ = self.active_turns[conversation_id]
        return active_turn_id == turn_id
