package com.ferhat.myaicoach.feature.speaking.turn

/**
 * ConversationTurn: Benzersiz kimliğe (turnId) sahip tek bir konuşma turu modeli.
 * Barge-in (araya girme) veya iptal durumlarında tur id bazlı yarış durumlarını (race condition) engeller.
 */
data class ConversationTurn(
    val conversationId: String,
    val turnId: String,
    val state: TurnState = TurnState.IDLE,
    val userTranscript: String? = null,
    val aiResponseText: String? = null,
    val startedAt: Long = System.currentTimeMillis()
)
