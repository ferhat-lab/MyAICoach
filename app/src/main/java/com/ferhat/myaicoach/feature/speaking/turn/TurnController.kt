package com.ferhat.myaicoach.feature.speaking.turn

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

/**
 * TurnController: Konuşma turlarını (Turn State Machine) yöneten denetleyici.
 * Temel Mühendislik Kuralı: CANCELLED, FAILED veya TIMED_OUT olan bir tur ASLA tekrar PLAYING veya COMPLETED olamaz.
 */
class TurnController {

    private val _currentTurn = MutableStateFlow<ConversationTurn?>(null)
    val currentTurn: StateFlow<ConversationTurn?> = _currentTurn.asStateFlow()

    /**
     * Yeni bir konuşma turu başlatır. Varsa aktif turu iptal eder.
     */
    fun startNewTurn(conversationId: String): ConversationTurn {
        // Varsa aktif turu iptal et (Barge-in / Cancel)
        cancelActiveTurn("NEW_TURN_STARTED")

        val newTurn = ConversationTurn(
            conversationId = conversationId,
            turnId = UUID.randomUUID().toString(),
            state = TurnState.LISTENING
        )
        _currentTurn.value = newTurn
        return newTurn
    }

    /**
     * Tur durumunu günceller. İptal edilmiş veya sonlanmış turların durumunu değiştirmesini engeller.
     */
    fun transitionState(turnId: String, newState: TurnState): Boolean {
        val active = _currentTurn.value ?: return false
        if (active.turnId != turnId) return false

        // Kural: Terminal durumdaki bir tur başka duruma geçemez (Race-condition koruması)
        if (active.state.isTerminal) {
            println("⚠️ Güvenlik Uyarı: Terminal durumdaki tur ($turnId, state: ${active.state}) '$newState' durumuna geçirilemez!")
            return false
        }

        _currentTurn.update { current ->
            current?.copy(state = newState)
        }
        return true
    }

    /**
     * Kullanıcı mikrofona bastığında veya araya girdiğinde aktif turu iptal eder (User Barge-In).
     */
    fun cancelActiveTurn(reason: String = "USER_BARGE_IN"): Boolean {
        val active = _currentTurn.value ?: return false
        if (active.state.isTerminal) return false

        _currentTurn.update { current ->
            current?.copy(state = TurnState.CANCELLED)
        }
        println("🛑 Tur İptal Edildi (${active.turnId}): $reason")
        return true
    }

    /**
     * Kullanıcının söylediği son metni güncelleme.
     */
    fun updateUserTranscript(turnId: String, text: String) {
        val active = _currentTurn.value ?: return
        if (active.turnId == turnId && !active.state.isTerminal) {
            _currentTurn.update { it?.copy(userTranscript = text) }
        }
    }

    /**
     * Vani'nin ürettiği cevap metnini güncelleme.
     */
    fun updateAiResponse(turnId: String, text: String) {
        val active = _currentTurn.value ?: return
        if (active.turnId == turnId && !active.state.isTerminal) {
            _currentTurn.update { it?.copy(aiResponseText = text) }
        }
    }
}
