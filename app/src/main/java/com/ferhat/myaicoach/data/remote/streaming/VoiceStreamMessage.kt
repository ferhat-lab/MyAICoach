package com.ferhat.myaicoach.data.remote.streaming

/**
 * VoiceStreamMessage: Android istemcisi ile Backend arasındaki WebSocket / Dual-Stream wire kontratı.
 * Geriye dönük uyumluluk ve latency analizi için [protocolVersion], [messageType], [timestampMs],
 * [sequenceId], [segmentId] ve [chunkIndex] barındırır.
 *
 * MİGRASYON NOTU: Base64 audio taşıyan JSON frame'leri ileride performans için BINARY WebSocket frame'lerine geçirilecektir.
 */
sealed class VoiceStreamMessage {
    abstract val protocolVersion: Int
    abstract val messageType: String
    abstract val conversationId: String
    abstract val turnId: String
    abstract val sequenceId: Long
    abstract val timestampMs: Long

    // ==================================================
    // CLIENT -> SERVER EVENTS
    // ==================================================

    data class StartTurn(
        override val protocolVersion: Int = VoiceProtocol.VERSION,
        override val messageType: String = VoiceProtocol.MSG_START_TURN,
        override val conversationId: String,
        override val turnId: String,
        override val sequenceId: Long,
        override val timestampMs: Long = System.currentTimeMillis()
    ) : VoiceStreamMessage()

    /**
     * Microphon'dan akan ses verisi parçası.
     * TODO: İleride JSON yerine doğrudan WebSocket Binary Frame olarak gönderilecektir.
     */
    data class AudioInputChunk(
        override val protocolVersion: Int = VoiceProtocol.VERSION,
        override val messageType: String = VoiceProtocol.MSG_AUDIO_INPUT_CHUNK,
        override val conversationId: String,
        override val turnId: String,
        override val sequenceId: Long,
        override val timestampMs: Long = System.currentTimeMillis(),
        val base64Audio: String
    ) : VoiceStreamMessage()

    /**
     * Kullanıcı mikrofondan elini çektiğinde veya konuşmayı tamamladığında gönderilen sonlandırma event'i.
     */
    data class EndUserSpeech(
        override val protocolVersion: Int = VoiceProtocol.VERSION,
        override val messageType: String = VoiceProtocol.MSG_END_USER_SPEECH,
        override val conversationId: String,
        override val turnId: String,
        override val sequenceId: Long,
        override val timestampMs: Long = System.currentTimeMillis()
    ) : VoiceStreamMessage()

    data class CancelTurn(
        override val protocolVersion: Int = VoiceProtocol.VERSION,
        override val messageType: String = VoiceProtocol.MSG_CANCEL_TURN,
        override val conversationId: String,
        override val turnId: String,
        override val sequenceId: Long,
        override val timestampMs: Long = System.currentTimeMillis(),
        val reason: String = "USER_BARGE_IN"
    ) : VoiceStreamMessage()

    // ==================================================
    // SERVER -> CLIENT EVENTS
    // ==================================================

    /**
     * Sunucunun START_TURN isteğini onaylayıp işleme aldığını bildiren event.
     */
    data class TurnStarted(
        override val protocolVersion: Int = VoiceProtocol.VERSION,
        override val messageType: String = VoiceProtocol.MSG_TURN_STARTED,
        override val conversationId: String,
        override val turnId: String,
        override val sequenceId: Long,
        override val timestampMs: Long = System.currentTimeMillis()
    ) : VoiceStreamMessage()

    data class TranscriptReceived(
        override val protocolVersion: Int = VoiceProtocol.VERSION,
        override val messageType: String = VoiceProtocol.MSG_TRANSCRIPT_RECEIVED,
        override val conversationId: String,
        override val turnId: String,
        override val sequenceId: Long,
        override val timestampMs: Long = System.currentTimeMillis(),
        val text: String,
        val isFinal: Boolean
    ) : VoiceStreamMessage()

    data class LlmTextSegment(
        override val protocolVersion: Int = VoiceProtocol.VERSION,
        override val messageType: String = VoiceProtocol.MSG_LLM_TEXT_SEGMENT,
        override val conversationId: String,
        override val turnId: String,
        override val sequenceId: Long,
        override val timestampMs: Long = System.currentTimeMillis(),
        val segmentId: Int,
        val textSegment: String
    ) : VoiceStreamMessage()

    /**
     * VoxCPM2 48kHz PCM ses verisi parçası.
     * TODO: İleride JSON yerine doğrudan WebSocket Binary Frame olarak kabul edilecektir.
     */
    data class AudioOutputChunk(
        override val protocolVersion: Int = VoiceProtocol.VERSION,
        override val messageType: String = VoiceProtocol.MSG_AUDIO_OUTPUT_CHUNK,
        override val conversationId: String,
        override val turnId: String,
        override val sequenceId: Long,
        override val timestampMs: Long = System.currentTimeMillis(),
        val segmentId: Int,
        val chunkIndex: Int,
        val sampleRate: Int = VoiceProtocol.AUDIO_SAMPLE_RATE,
        val channels: Int = VoiceProtocol.AUDIO_CHANNELS,
        val encoding: String = VoiceProtocol.AUDIO_ENCODING,
        val pcmChunk: ByteArray
    ) : VoiceStreamMessage() {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as AudioOutputChunk

            if (conversationId != other.conversationId) return false
            if (turnId != other.turnId) return false
            if (sequenceId != other.sequenceId) return false
            if (segmentId != other.segmentId) return false
            if (chunkIndex != other.chunkIndex) return false
            if (!pcmChunk.contentEquals(other.pcmChunk)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = conversationId.hashCode()
            result = 31 * result + turnId.hashCode()
            result = 31 * result + sequenceId.hashCode()
            result = 31 * result + segmentId
            result = 31 * result + chunkIndex
            result = 31 * result + pcmChunk.contentHashCode()
            return result
        }
    }

    /**
     * Server-side TargetEvaluator ve Pedagoji Motoru tarafından gönderilen metadata event'i.
     */
    data class TurnMetadata(
        override val protocolVersion: Int = VoiceProtocol.VERSION,
        override val messageType: String = VoiceProtocol.MSG_TURN_METADATA,
        override val conversationId: String,
        override val turnId: String,
        override val sequenceId: Long,
        override val timestampMs: Long = System.currentTimeMillis(),
        val nextGoal: String? = null,
        val turnAction: TurnAction = TurnAction.CONTINUE,
        val usedTargetIds: List<String> = emptyList()
    ) : VoiceStreamMessage()

    data class TurnCompleted(
        override val protocolVersion: Int = VoiceProtocol.VERSION,
        override val messageType: String = VoiceProtocol.MSG_TURN_COMPLETED,
        override val conversationId: String,
        override val turnId: String,
        override val sequenceId: Long,
        override val timestampMs: Long = System.currentTimeMillis()
    ) : VoiceStreamMessage()

    data class TurnCancelled(
        override val protocolVersion: Int = VoiceProtocol.VERSION,
        override val messageType: String = VoiceProtocol.MSG_TURN_CANCELLED,
        override val conversationId: String,
        override val turnId: String,
        override val sequenceId: Long,
        override val timestampMs: Long = System.currentTimeMillis(),
        val reason: String = "USER_BARGE_IN"
    ) : VoiceStreamMessage()

    /**
     * Güçlendirilmiş Hata Kontratı.
     */
    data class ErrorOccurred(
        override val protocolVersion: Int = VoiceProtocol.VERSION,
        override val messageType: String = VoiceProtocol.MSG_ERROR_OCCURRED,
        override val conversationId: String,
        override val turnId: String,
        override val sequenceId: Long,
        override val timestampMs: Long = System.currentTimeMillis(),
        val errorCode: String = "GENERAL_ERROR",
        val errorMessage: String,
        val retryable: Boolean = false,
        val retryAfterMs: Long? = null
    ) : VoiceStreamMessage()
}
