import asyncio
import time
import logging
from typing import Dict, Optional, Callable, Awaitable
from app.protocol import constants
from app.protocol.models import (
    TurnStartedMessage,
    TranscriptReceivedMessage,
    LlmTextSegmentMessage,
    TurnMetadataMessage,
    TurnCompletedMessage,
    TurnCancelledMessage,
    ErrorOccurredMessage,
    TurnAction
)
from app.guards.turn_guard import ServerTurnGuard

logger = logging.getLogger("MyAICoach-TurnOrchestrator")

class TurnContext:
    """
    İçsel Tur Durum ve Sıra Takip Modeli (Internal State).
    [next_server_sequence] sunucunun ürettiği tüm event'lerin TEK OTORİTESİDİR.
    """
    def __init__(self, conversation_id: str, turn_id: str, start_sequence_id: int = 1):
        self.conversation_id = conversation_id
        self.turn_id = turn_id
        self.server_sequence_counter = 1  # Server tarafı bağımsız monoton sayaç
        self.active = True
        self.end_user_speech_received = False
        self.task: Optional[asyncio.Task] = None

    def next_server_sequence(self) -> int:
        self.server_sequence_counter += 1
        return self.server_sequence_counter

class TurnOrchestrator:
    """
    TurnOrchestrator: Konuşma turlarını, sıralama (sequenceId), mock ses akışını
    ve End-to-End iptal (Cancellation Propagation) mekanizmalarını yöneten orkestratör.
    """
    def __init__(self, turn_guard: ServerTurnGuard):
        self.turn_guard = turn_guard
        # conversation_id -> TurnContext
        self.active_contexts: Dict[str, TurnContext] = {}

    async def handle_start_turn(
        self,
        conversation_id: str,
        turn_id: str,
        initial_sequence_id: int,
        send_message_fn: Callable[[dict], Awaitable[None]]
    ):
        # 1. TurnGuard Doğrulaması
        can_start, error_code = self.turn_guard.can_start_turn(conversation_id, turn_id)
        if not can_start:
            err_msg = ErrorOccurredMessage(
                conversationId=conversation_id,
                turnId=turn_id,
                sequenceId=initial_sequence_id + 1,
                errorCode=error_code or constants.ERR_TURN_ALREADY_ACTIVE,
                errorMessage="Sunucu TurnGuard: Aktif tur var veya debounce engeline takıldı.",
                retryable=True,
                retryAfterMs=300
            )
            await send_message_fn(err_msg.model_dump())
            return

        # Varsa eski tur bağlamını temizle (Cancel propagation)
        self.cancel_turn(conversation_id, turn_id, reason="SUPERSEDED_BY_NEW_TURN")

        # 2. Yeni TurnContext Oluştur ve Kaydet
        ctx = TurnContext(conversation_id, turn_id, start_sequence_id=initial_sequence_id)
        self.active_contexts[conversation_id] = ctx
        self.turn_guard.register_turn(conversation_id, turn_id)

        # 3. Server -> Client: TURN_STARTED (Server sequenceId = 2)
        started_msg = TurnStartedMessage(
            conversationId=conversation_id,
            turnId=turn_id,
            sequenceId=ctx.next_server_sequence()
        )
        await send_message_fn(started_msg.model_dump())
        logger.info(f"🚀 TURN_STARTED Gönderildi ({conversation_id} -> {turn_id}, seq={started_msg.sequenceId})")

    async def handle_end_user_speech(
        self,
        conversation_id: str,
        turn_id: str,
        send_message_fn: Callable[[dict], Awaitable[None]]
    ):
        ctx = self.active_contexts.get(conversation_id)
        if not ctx or ctx.turn_id != turn_id or not ctx.active:
            logger.warning(f"⚠️ Geçersiz veya pasif tur için END_USER_SPEECH alındı: {turn_id}")
            return

        ctx.end_user_speech_received = True
        logger.info(f"🎤 END_USER_SPEECH Alındı -> Async Mock Flow Başlatılıyor ({conversation_id} -> {turn_id})")

        # 4. END_USER_SPEECH Sonrası Async Mock Voice Pipeline Akışını Çalıştır
        async def mock_turn_flow():
            try:
                # A) TRANSCRIPT_RECEIVED (STT Finalize: 500ms)
                await asyncio.sleep(0.5)
                if not ctx.active: return

                transcript_msg = TranscriptReceivedMessage(
                    conversationId=conversation_id,
                    turnId=turn_id,
                    sequenceId=ctx.next_server_sequence(),
                    text="Hello Vani! I would like a cup of tea, please.",
                    isFinal=True
                )
                await send_message_fn(transcript_msg.model_dump())
                logger.info(f"💬 TRANSCRIPT_RECEIVED Gönderildi (seq={transcript_msg.sequenceId})")

                # B) LLM_TEXT_SEGMENT #1 (400ms -> SpeechSegmenter)
                await asyncio.sleep(0.4)
                if not ctx.active: return

                seg1_msg = LlmTextSegmentMessage(
                    conversationId=conversation_id,
                    turnId=turn_id,
                    sequenceId=ctx.next_server_sequence(),
                    segmentId=1,
                    textSegment="Sure! Here is a hot cup of tea for you."
                )
                await send_message_fn(seg1_msg.model_dump())

                # C) LLM_TEXT_SEGMENT #2 (400ms)
                await asyncio.sleep(0.4)
                if not ctx.active: return

                seg2_msg = LlmTextSegmentMessage(
                    conversationId=conversation_id,
                    turnId=turn_id,
                    sequenceId=ctx.next_server_sequence(),
                    segmentId=2,
                    textSegment="Would you like some fresh bread too?"
                )
                await send_message_fn(seg2_msg.model_dump())

                # D) TURN_METADATA (Pedagojik Bildirim)
                await asyncio.sleep(0.3)
                if not ctx.active: return

                meta_msg = TurnMetadataMessage(
                    conversationId=conversation_id,
                    turnId=turn_id,
                    sequenceId=ctx.next_server_sequence(),
                    nextGoal="order_food",
                    turnAction=TurnAction.CONTINUE,
                    usedTargetIds=["phrase_i_would_like", "vocab_tea"]
                )
                await send_message_fn(meta_msg.model_dump())

                # E) TURN_COMPLETED (Tur Başarıyla Bitti)
                await asyncio.sleep(0.5)
                if not ctx.active: return

                completed_msg = TurnCompletedMessage(
                    conversationId=conversation_id,
                    turnId=turn_id,
                    sequenceId=ctx.next_server_sequence()
                )
                await send_message_fn(completed_msg.model_dump())
                self.turn_guard.clear_turn(conversation_id, turn_id)
                ctx.active = False
                logger.info(f"✅ Mock Turn Başarıyla Tamamlandı ({conversation_id} -> {turn_id})")

            except asyncio.CancelledError:
                logger.info(f"🛑 Mock Turn Flow Task Gerçekten İptal Edildi ({conversation_id} -> {turn_id})")

        ctx.task = asyncio.create_task(mock_turn_flow())

    def cancel_turn(self, conversation_id: str, turn_id: str, reason: str = "USER_BARGE_IN") -> bool:
        ctx = self.active_contexts.get(conversation_id)
        if not ctx:
            return False

        ctx.active = False
        if ctx.task and not ctx.task.done():
            ctx.task.cancel()

        self.turn_guard.clear_turn(conversation_id, turn_id)
        if conversation_id in self.active_contexts:
            del self.active_contexts[conversation_id]

        logger.info(f"🛑 Turn Cancelled ({conversation_id} -> {turn_id}): {reason}")
        return True
