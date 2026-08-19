from enum import Enum
from typing import List, Optional
from pydantic import BaseModel, Field
import time
from . import constants

class TurnAction(str, Enum):
    """
    Android TurnAction enum'u ile birebir uyumlu pedagojik aksiyon listesi.
    """
    CONTINUE = "CONTINUE"
    RETRY = "RETRY"
    GIVE_HINT = "GIVE_HINT"
    COMPLETE_SCENARIO = "COMPLETE_SCENARIO"
    SAFE_REDIRECT = "SAFE_REDIRECT"

class VoiceStreamMessageBase(BaseModel):
    """
    Tüm WebSocket wire mesajlarının ortak taban Pydantic modeli.
    """
    protocolVersion: int = Field(default=constants.PROTOCOL_VERSION)
    messageType: str
    conversationId: str
    turnId: str
    sequenceId: int
    timestampMs: int = Field(default_factory=lambda: int(time.time() * 1000))

# ==================================================
# CLIENT -> SERVER MESSAGES
# ==================================================

class StartTurnMessage(VoiceStreamMessageBase):
    messageType: str = constants.MSG_START_TURN

class AudioInputChunkMessage(VoiceStreamMessageBase):
    """
    LEGACY / TEMPORARY COMPATIBILITY: Base64 JSON audio chunk.
    Uzun vadeli hedef: Doğrudan WebSocket Binary Frames kullanımıdır.
    """
    messageType: str = constants.MSG_AUDIO_INPUT_CHUNK
    base64Audio: str

class EndUserSpeechMessage(VoiceStreamMessageBase):
    messageType: str = constants.MSG_END_USER_SPEECH

class CancelTurnMessage(VoiceStreamMessageBase):
    messageType: str = constants.MSG_CANCEL_TURN
    reason: str = "USER_BARGE_IN"

# ==================================================
# SERVER -> CLIENT MESSAGES
# ==================================================

class TurnStartedMessage(VoiceStreamMessageBase):
    messageType: str = constants.MSG_TURN_STARTED

class TranscriptReceivedMessage(VoiceStreamMessageBase):
    messageType: str = constants.MSG_TRANSCRIPT_RECEIVED
    text: str
    isFinal: bool = True

class LlmTextSegmentMessage(VoiceStreamMessageBase):
    messageType: str = constants.MSG_LLM_TEXT_SEGMENT
    segmentId: int
    textSegment: str

class AudioOutputChunkMessage(VoiceStreamMessageBase):
    """
    LEGACY / TEMPORARY COMPATIBILITY: Base64 JSON PCM chunk.
    Uzun vadeli hedef: Doğrudan WebSocket Binary Frames kullanımıdır.
    """
    messageType: str = constants.MSG_AUDIO_OUTPUT_CHUNK
    segmentId: int
    chunkIndex: int
    sampleRate: int = constants.AUDIO_SAMPLE_RATE
    channels: int = constants.AUDIO_CHANNELS
    encoding: str = constants.AUDIO_ENCODING
    pcmChunk: str

class TurnMetadataMessage(VoiceStreamMessageBase):
    messageType: str = constants.MSG_TURN_METADATA
    nextGoal: Optional[str] = None
    turnAction: TurnAction = TurnAction.CONTINUE
    usedTargetIds: List[str] = Field(default_factory=list)

class TurnCompletedMessage(VoiceStreamMessageBase):
    messageType: str = constants.MSG_TURN_COMPLETED

class TurnCancelledMessage(VoiceStreamMessageBase):
    messageType: str = constants.MSG_TURN_CANCELLED
    reason: str = "USER_BARGE_IN"

class ErrorOccurredMessage(VoiceStreamMessageBase):
    messageType: str = constants.MSG_ERROR_OCCURRED
    errorCode: str = constants.ERR_INTERNAL_SERVER_ERROR
    errorMessage: str
    retryable: bool = False
    retryAfterMs: Optional[int] = None
