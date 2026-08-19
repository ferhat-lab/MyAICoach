package com.ferhat.myaicoach.data.remote.streaming

/**
 * VoiceStreamMessage: Android istemcisi ile Backend arasındaki WebSocket / Dual-Stream kontratları.
 * Geriye dönük uyumluluk için [protocolVersion] = 1, [sequenceId], [segmentId] barındırır.
 */
sealed class VoiceStreamMessage {
    abstract val protocolVersion: Int
    abstract val conversationId: String
    abstract val turnId: String
    abstract val sequenceId: Long

    // Client -> Server İstekleri
    data class StartTurn(
        override val protocolVersion: Int = 1,
        override val conversationId: String,
        override val turnId: String,
        override val sequenceId: Long
    ) : VoiceStreamMessage()

    data class AudioInputChunk(
        override val protocolVersion: Int = 1,
        override val conversationId: String,
        override val turnId: String,
        override val sequenceId: Long,
        val base64Audio: String
    ) : VoiceStreamMessage()

    data class CancelTurn(
        override val protocolVersion: Int = 1,
        override val conversationId: String,
        override val turnId: String,
        override val sequenceId: Long,
        val reason: String = "USER_BARGE_IN"
    ) : VoiceStreamMessage()

    // Server -> Client Yanıtları
    data class TranscriptReceived(
        override val protocolVersion: Int = 1,
        override val conversationId: String,
        override val turnId: String,
        override val sequenceId: Long,
        val text: String,
        val isFinal: Boolean
    ) : VoiceStreamMessage()

    data class LlmTextSegment(
        override val protocolVersion: Int = 1,
        override val conversationId: String,
        override val turnId: String,
        override val sequenceId: Long,
        val segmentId: Int,
        val textSegment: String
    ) : VoiceStreamMessage()

    data class AudioOutputChunk(
        override val protocolVersion: Int = 1,
        override val conversationId: String,
        override val turnId: String,
        override val sequenceId: Long,
        val segmentId: Int,
        val chunkIndex: Int,
        val sampleRate: Int = 48000,
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
        override val conversationId: String,
        override val turnId: String,
        override val sequenceId: Long
    ) : VoiceStreamMessage()

    data class TurnCancelled(
        override val protocolVersion: Int = 1,
        override val conversationId: String,
        override val turnId: String,
        override val sequenceId: Long,
        val reason: String = "USER_BARGE_IN"
    ) : VoiceStreamMessage()

    data class ErrorOccurred(
        override val protocolVersion: Int = 1,
        override val conversationId: String,
        override val turnId: String,
        override val sequenceId: Long,
        val errorMessage: String
    ) : VoiceStreamMessage()
}
