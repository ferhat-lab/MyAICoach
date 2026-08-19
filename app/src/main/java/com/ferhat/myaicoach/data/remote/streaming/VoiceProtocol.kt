package com.ferhat.myaicoach.data.remote.streaming

/**
 * VoiceProtocol: Canlı Ses Akışı (Voice Streaming) merkezi sabitleri ve varsayılan ayarları.
 */
object VoiceProtocol {
    const val VERSION = 1

    const val AUDIO_SAMPLE_RATE = 48_000
    const val AUDIO_CHANNELS = 1
    const val AUDIO_ENCODING = "PCM_16BIT"

    // Client -> Server Event Tipleri
    const val MSG_START_TURN = "START_TURN"
    const val MSG_AUDIO_INPUT_CHUNK = "AUDIO_INPUT_CHUNK"
    const val MSG_END_USER_SPEECH = "END_USER_SPEECH"
    const val MSG_CANCEL_TURN = "CANCEL_TURN"

    // Server -> Client Event Tipleri
    const val MSG_TURN_STARTED = "TURN_STARTED"
    const val MSG_TRANSCRIPT_RECEIVED = "TRANSCRIPT_RECEIVED"
    const val MSG_LLM_TEXT_SEGMENT = "LLM_TEXT_SEGMENT"
    const val MSG_AUDIO_OUTPUT_CHUNK = "AUDIO_OUTPUT_CHUNK"
    const val MSG_TURN_METADATA = "TURN_METADATA"
    const val MSG_TURN_COMPLETED = "TURN_COMPLETED"
    const val MSG_TURN_CANCELLED = "TURN_CANCELLED"
    const val MSG_ERROR_OCCURRED = "ERROR_OCCURRED"

    // Standart Hata Kodları Kontratı
    object ErrorCodes {
        const val TURN_RATE_LIMITED = "TURN_RATE_LIMITED"
        const val PROTOCOL_VERSION_UNSUPPORTED = "PROTOCOL_VERSION_UNSUPPORTED"
        const val TURN_ALREADY_ACTIVE = "TURN_ALREADY_ACTIVE"
        const val INVALID_AUDIO_FORMAT = "INVALID_AUDIO_FORMAT"
        const val STT_UNAVAILABLE = "STT_UNAVAILABLE"
        const val LLM_TIMEOUT = "LLM_TIMEOUT"
        const val LLM_UNAVAILABLE = "LLM_UNAVAILABLE"
        const val TTS_UNAVAILABLE = "TTS_UNAVAILABLE"
        const val TURN_TIMED_OUT = "TURN_TIMED_OUT"
    }
}
