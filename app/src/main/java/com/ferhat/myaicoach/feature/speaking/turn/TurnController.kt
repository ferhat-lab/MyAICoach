package com.ferhat.myaicoach.feature.speaking.turn

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

/**
 * TurnController: Konuşma turlarını (Turn State Machine) yöneten denetleyici.
 * TurnGuard koruması ile debounce, per-user concurrency=1 ve terminal durum değişmezliğini garanti eder.
 */
class TurnController(
    val turnGuard: TurnGuard = TurnGuard()
) {

    private val _currentTurn = MutableStateFlow<ConversationTurn?>(null)
    val currentTurn: StateFlow<ConversationTurn?> = _currentTurn.asStateFlow()

    /**
     * Yeni bir konuşma turu başlatır. Debounce ve TurnGuard kontrollerini uygular.
     * @return Yeni oluşturulan tur veya spam/debounce durumunda null.
     */
    fun startNewTurn(conversationId: String): ConversationTurn? {
        // 1. TurnGuard Debounce Kontrolü (300ms)
        if (!turnGuard.canStartNewTurn()) {
            return null
        }

        // 2. Varsa aktif turu iptal et (User Barge-in / Concurrency = 1 kuralı)
        cancelActiveTurn("NEW_TURN_STARTED_BARGE_IN")

        val newTurn = ConversationTurn(
            conversationId = conversationId,
            turnId = UUID.randomUUID().toString(),
            state = TurnState.LISTENING
        )
        _currentTurn.value = newTurn
        println("🚀 Yeni Tur Başlatıldı (${newTurn.turnId}) - State: LISTENING")
        return newTurn
    }

    /**
     * Tur durumunu günceller. TurnGuard ile terminal durumdaki turların değişmesini engeller.
     */
    fun transitionState(turnId: String, newState: TurnState): Boolean {
        val active = _currentTurn.value ?: return false
        if (active.turnId != turnId) return false

        // 1. TurnGuard Değişmezlik Doğrulaması
        if (!turnGuard.validateStateTransition(active, newState)) {
            return false
        }

        _currentTurn.update { current ->
            current?.copy(state = newState)
        }
        println("🔄 Tur Durumu Güncellendi (${active.turnId}): ${active.state} -> $newState")
        return true
    }

    /**
     * Kullanıcı mikrofona bastığında veya araya girdiğinde aktif turu iptal eder (User Barge-In).
     */
    fun cancelActiveTurn(reason: String = "USER_BARGE_IN"): Boolean {
        val active = _currentTurn.value ?: return false

        // 1. TurnGuard Barge-In Doğrulaması
        if (!turnGuard.canBargeIn(active)) {
            return false
        }

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
