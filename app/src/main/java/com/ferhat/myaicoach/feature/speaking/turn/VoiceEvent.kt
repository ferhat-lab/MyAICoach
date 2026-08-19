package com.ferhat.myaicoach.feature.speaking.turn

/**
 * VoiceEvent: Android ile Backend/AI Servisleri arasındaki gerçek zamanlı protokol mesajları.
 */
sealed class VoiceEvent {
    data class TurnStarted(val conversationId: String, val turnId: String) : VoiceEvent()
    data class TranscriptPartial(val turnId: String, val partialText: String) : VoiceEvent()
    data class TranscriptFinal(val turnId: String, val finalText: String) : VoiceEvent()
    data class LlmStarted(val turnId: String) : VoiceEvent()
    data class SpeechSegment(val turnId: String, val textSegment: String) : VoiceEvent()
    data class AudioChunk(val turnId: String, val pcmData: ByteArray) : VoiceEvent() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            other as AudioChunk
            return turnId == other.turnId && pcmData.contentEquals(other.pcmData)
        }

        override fun hashCode(): Int {
            var result = turnId.hashCode()
            result = 31 * result + pcmData.contentHashCode()
            return result
        }
    }
    data class TurnMetadata(val turnId: String, val metadataJson: String) : VoiceEvent()
    data class TurnCompleted(val turnId: String) : VoiceEvent()
    data class TurnCancelled(val turnId: String, val reason: String = "USER_BARGE_IN") : VoiceEvent()
    data class Error(val turnId: String?, val message: String) : VoiceEvent()
}
