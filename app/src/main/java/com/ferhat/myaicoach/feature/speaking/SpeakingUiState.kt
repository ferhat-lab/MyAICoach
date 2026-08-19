package com.ferhat.myaicoach.feature.speaking

import com.ferhat.myaicoach.domain.lesson.SpeakingScenario
import com.ferhat.myaicoach.domain.mascot.MascotState
import com.ferhat.myaicoach.feature.speaking.turn.ConversationTurn
import com.ferhat.myaicoach.feature.speaking.turn.TurnState

/**
 * SpeakingUiState: Canlı konuşma ekranı UI durum modeli.
 * TurnState'i doğrudan Vani'nin MascotState tepkilerine eşler.
 */
data class SpeakingUiState(
    val scenario: SpeakingScenario? = null,
    val activeTurn: ConversationTurn? = null,
    val isMicPressed: Boolean = false,
    val conversationHistory: List<ConversationTurn> = emptyList(),
    val errorMessage: String? = null
) {
    /**
     * Tur durumuna göre Vani kedi maskotunun otomatik reaktif tepkisi.
     */
    val mascotState: MascotState
        get() = when (activeTurn?.state) {
            TurnState.LISTENING -> MascotState.LISTENING
            TurnState.TRANSCRIBED -> MascotState.THINKING
            TurnState.LLM_GENERATING -> MascotState.THINKING
            TurnState.TTS_GENERATING -> MascotState.SPEAKING
            TurnState.PLAYING -> MascotState.SPEAKING
            TurnState.COMPLETED -> MascotState.HAPPY_CHEERING
            TurnState.CANCELLED -> MascotState.GENTLE_HINT
            TurnState.FAILED, TurnState.TIMED_OUT -> MascotState.GENTLE_HINT
            TurnState.IDLE, null -> MascotState.IDLE
        }
}
