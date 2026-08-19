package com.ferhat.myaicoach.data.remote.streaming

/**
 * VoiceStreamMessage: Android istemcisi ile Backend (LLM/VoxCPM2) arasındaki WebSocket / Dual-Stream JSON mesaj yapıları.
 */
sealed class VoiceStreamMessage {
    data class StartTurn(val conversationId: String, val turnId: String) : VoiceStreamMessage()
    data class AudioInputChunk(val turnId: String, val base64Audio: String) : VoiceStreamMessage()
    data class CancelTurn(val turnId: String, val reason: String = "USER_BARGE_IN") : VoiceStreamMessage()
    
    // Server -> Client Gelen Mesajlar
    data class TranscriptReceived(val turnId: String, val text: String, val isFinal: Boolean) : VoiceStreamMessage()
    data class LlmToken(val turnId: String, val token: String) : VoiceStreamMessage()
    data class AudioOutputChunk(val turnId: String, val pcmChunk: ByteArray) : VoiceStreamMessage() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            other as AudioOutputChunk
            return turnId == other.turnId && pcmChunk.contentEquals(other.pcmChunk)
        }

        override fun hashCode(): Int {
            var result = turnId.hashCode()
            result = 31 * result + pcmChunk.contentHashCode()
            return result
        }
    }
    data class TurnFinished(val turnId: String) : VoiceStreamMessage()
    data class ErrorOccurred(val turnId: String?, val errorMessage: String) : VoiceStreamMessage()
}
