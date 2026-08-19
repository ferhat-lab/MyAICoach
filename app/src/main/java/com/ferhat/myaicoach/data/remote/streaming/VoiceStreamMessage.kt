package com.ferhat.myaicoach.data.remote.streaming

/**
 * VoiceStreamMessage: Android istemcisi ile Backend arasındaki WebSocket / Dual-Stream wire kontratı.
 * Geriye dönük uyumluluk ve latency analizi için [protocolVersion] = 1, [messageType], [timestampMs],
 * [sequenceId], [segmentId] ve [chunkIndex] barındırır.
 */
sealed class VoiceStreamMessage {
    abstract val protocolVersion: Int
    abstract val messageType: String
    abstract val conversationId: String
    abstract val turnId: String
    abstract val sequenceId: Long
    abstract val timestampMs: Long

    // Client -> Server İstekleri
    data class StartTurn(
        override val protocolVersion: Int = 1,
        override val messageType: String = "START_TURN",
        override val conversationId: String,
        override val turnId: String,
        override val sequenceId: Long,
        override val timestampMs: Long = System.currentTimeMillis()
    ) : VoiceStreamMessage()

    data class AudioInputChunk(
        override val protocolVersion: Int = 1,
        override val messageType: String = "AUDIO_INPUT_CHUNK",
        override val conversationId: String,
        override val turnId: String,
        override val sequenceId: Long,
        override val timestampMs: Long = System.currentTimeMillis(),
        val base64Audio: String
    ) : VoiceStreamMessage()

    data class CancelTurn(
        override val protocolVersion: Int = 1,
        override val messageType: String = "CANCEL_TURN",
        override val conversationId: String,
        override val turnId: String,
        override val sequenceId: Long,
        override val timestampMs: Long = System.currentTimeMillis(),
        val reason: String = "USER_BARGE_IN"
    ) : VoiceStreamMessage()

    // Server -> Client Yanıtları
    data class TranscriptReceived(
        override val protocolVersion: Int = 1,
        override val messageType: String = "TRANSCRIPT_RECEIVED",
        override val conversationId: String,
        override val turnId: String,
        override val sequenceId: Long,
        override val timestampMs: Long = System.currentTimeMillis(),
        val text: String,
        val isFinal: Boolean
    ) : VoiceStreamMessage()

    data class LlmTextSegment(
        override val protocolVersion: Int = 1,
        override val messageType: String = "LLM_TEXT_SEGMENT",
        override val conversationId: String,
        override val turnId: String,
        override val sequenceId: Long,
        override val timestampMs: Long = System.currentTimeMillis(),
        val segmentId: Int,
        val textSegment: String
    ) : VoiceStreamMessage()

    data class AudioOutputChunk(
        override val protocolVersion: Int = 1,
        override val messageType: String = "AUDIO_OUTPUT_CHUNK",
        override val conversationId: String,
        override val turnId: String,
        override val sequenceId: Long,
        override val timestampMs: Long = System.currentTimeMillis(),
        val segmentId: Int,
        val chunkIndex: Int,
        val sampleRate: Int = 48000,
        val channels: Int = 1,
        val encoding: String = "PCM_16BIT",
        val pcmChunk: ByteArray
    ) : VoiceStreamMessage() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            other as AudioOutputChunk
            return turnId == other.turnId && sequenceId == other.sequenceId && pcmChunk.contentEquals(other.pcmChunk)
        }

        override fun hashCode(): Int {
            var result = turnId.hashCode()
            result = 31 * result + sequenceId.hashCode()
            result = 31 * result + pcmChunk.contentHashCode()
            return result
        }
    }

    data class TurnCompleted(
        override val protocolVersion: Int = 1,
        override val messageType: String = "TURN_COMPLETED",
        override val conversationId: String,
        override val turnId: String,
        override val sequenceId: Long,
        override val timestampMs: Long = System.currentTimeMillis()
    ) : VoiceStreamMessage()

    data class TurnCancelled(
        override val protocolVersion: Int = 1,
        override val messageType: String = "TURN_CANCELLED",
        override val conversationId: String,
        override val turnId: String,
        override val sequenceId: Long,
        override val timestampMs: Long = System.currentTimeMillis(),
        val reason: String = "USER_BARGE_IN"
    ) : VoiceStreamMessage()

    data class ErrorOccurred(
        override val protocolVersion: Int = 1,
        override val messageType: String = "ERROR_OCCURRED",
        override val conversationId: String,
        override val turnId: String,
        override val sequenceId: Long,
        override val timestampMs: Long = System.currentTimeMillis(),
        val errorMessage: String
    ) : VoiceStreamMessage()
}
