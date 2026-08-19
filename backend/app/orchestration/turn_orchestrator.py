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
from app.services.qwen_llm_service import QwenLlmService
from app.services.speech_segmenter import ServerSpeechSegmenter

logger = logging.getLogger("MyAICoach-TurnOrchestrator")

class TurnContext:
    """
    İçsel Tur Durum ve Sıra Takip Modeli (Internal State).
    [next_server_sequence] sunucunun ürettiği tüm event'lerin TEK OTORİTESİDİR.
    """
    def __init__(self, conversation_id: str, turn_id: str, start_sequence_id: int = 1):
        self.conversation_id = conversation_id
        self.turn_id = turn_id
        self.server_sequence_counter = 1
        self.active = True
        self.end_user_speech_received = False
        self.task: Optional[asyncio.Task] = None

    def next_server_sequence(self) -> int:
        self.server_sequence_counter += 1
        return self.server_sequence_counter

class TurnOrchestrator:
    """
    TurnOrchestrator: Konuşma turlarını, Qwen3 Token Streaming, Server-Side SpeechSegmenter,
    ve End-to-End iptal (Cancellation Propagation) mekanizmalarını yöneten ana motor.
    """
    def __init__(self, turn_guard: ServerTurnGuard, qwen_service: Optional[QwenLlmService] = None):
        self.turn_guard = turn_guard
        self.qwen_service = qwen_service or QwenLlmService()
        self.active_contexts: Dict[str, TurnContext] = {}

    async def handle_start_turn(
        self,
        conversation_id: str,
        turn_id: str,
        initial_sequence_id: int,
        send_message_fn: Callable[[dict], Awaitable[None]]
    ):
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

        self.cancel_turn(conversation_id, turn_id, reason="SUPERSEDED_BY_NEW_TURN")

        ctx = TurnContext(conversation_id, turn_id, start_sequence_id=initial_sequence_id)
        self.active_contexts[conversation_id] = ctx
        self.turn_guard.register_turn(conversation_id, turn_id)

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
        logger.info(f"🎤 END_USER_SPEECH Alındı -> Qwen3 Streaming Pipeline Başlatılıyor ({conversation_id} -> {turn_id})")

        async def qwen_streaming_pipeline():
            try:
                # 1. STT Final Transcript Simülasyonu
                await asyncio.sleep(0.4)
                if not ctx.active: return

                user_transcript = "Hello Vani! Can I order a coffee, please?"
                transcript_msg = TranscriptReceivedMessage(
                    conversationId=conversation_id,
                    turnId=turn_id,
                    sequenceId=ctx.next_server_sequence(),
                    text=user_transcript,
                    isFinal=True
                )
                await send_message_fn(transcript_msg.model_dump())
                logger.info(f"💬 TRANSCRIPT_RECEIVED Gönderildi (seq={transcript_msg.sequenceId})")

                # 2. Qwen3 Token Streaming & ServerSpeechSegmenter Boru Hattı
                segmenter = ServerSpeechSegmenter()
                segment_counter = 1

                async for token in self.qwen_service.stream_tokens(user_prompt=user_transcript):
                    if not ctx.active: break

                    # Token'ları tampona ver ve tamamlanan cümle parçalarını al
                    completed_sentences = segmenter.process_token(token)

                    for sentence in completed_sentences:
                        if not ctx.active: break

                        seg_msg = LlmTextSegmentMessage(
                            conversationId=conversation_id,
                            turnId=turn_id,
                            sequenceId=ctx.next_server_sequence(),
                            segmentId=segment_counter,
                            textSegment=sentence
                        )
                        await send_message_fn(seg_msg.model_dump())
                        logger.info(f"⚡ LLM Segment #{segment_counter} Üretildi & Fırlatıldı: \"{sentence}\"")
                        segment_counter += 1

                # Tamponda kalan son parçayı da gönder
                remaining_sentence = segmenter.flush()
                if remaining_sentence and ctx.active:
                    seg_msg = LlmTextSegmentMessage(
                        conversationId=conversation_id,
                        turnId=turn_id,
                        sequenceId=ctx.next_server_sequence(),
                        segmentId=segment_counter,
                        textSegment=remaining_sentence
                    )
                    await send_message_fn(seg_msg.model_dump())
                    logger.info(f"⚡ LLM Son Segment #{segment_counter} Üretildi & Fırlatıldı: \"{remaining_sentence}\"")

                # 3. TurnMetadata (Pedagojik Bildirim)
                if not ctx.active: return
                await asyncio.sleep(0.2)

                meta_msg = TurnMetadataMessage(
                    conversationId=conversation_id,
                    turnId=turn_id,
                    sequenceId=ctx.next_server_sequence(),
                    nextGoal="order_coffee",
                    turnAction=TurnAction.CONTINUE,
                    usedTargetIds=["phrase_can_i_order", "vocab_coffee"]
                )
                await send_message_fn(meta_msg.model_dump())

                # 4. TurnCompleted
                if not ctx.active: return
                await asyncio.sleep(0.3)

                completed_msg = TurnCompletedMessage(
                    conversationId=conversation_id,
                    turnId=turn_id,
                    sequenceId=ctx.next_server_sequence()
                )
                await send_message_fn(completed_msg.model_dump())
                self.turn_guard.clear_turn(conversation_id, turn_id)
                ctx.active = False
                logger.info(f"✅ Qwen3 Streaming Pipeline Başarıyla Tamamlandı ({conversation_id} -> {turn_id})")

            except asyncio.CancelledError:
                logger.info(f"🛑 Qwen3 Streaming Pipeline Task Gerçekten İptal Edildi ({conversation_id} -> {turn_id})")

        ctx.task = asyncio.create_task(qwen_streaming_pipeline())

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
