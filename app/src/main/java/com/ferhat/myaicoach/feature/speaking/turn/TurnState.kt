package com.ferhat.myaicoach.feature.speaking.turn

/**
 * TurnState: Bir Vani konuşma turunun geçebileceği kesin durumlar.
 * Race-condition önleme kuralı: CANCELLED, FAILED veya TIMED_OUT olan bir tur tekrar PLAYING olamaz.
 */
enum class TurnState {
    IDLE,
    LISTENING,
    TRANSCRIBED,
    LLM_GENERATING,
    TTS_GENERATING,
    PLAYING,
    COMPLETED,
    CANCELLED,
    FAILED,
    TIMED_OUT;

    /**
     * Turun sonlanmış (terminal) bir durumda olup olmadığını kontrol eder.
     */
    val isTerminal: Boolean
        get() = this == COMPLETED || this == CANCELLED || this == FAILED || this == TIMED_OUT
}
