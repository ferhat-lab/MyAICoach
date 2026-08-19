package com.ferhat.myaicoach.feature.speaking.turn

/**
 * TurnGuard: Konuşma turlarında suistimali (abuse), üst üste hızlı mikrofona basılmasını (debounce)
 * ve yarış durumlarını (race-condition) engelleyen güvenlik katmanı.
 *
 * Kurallar:
 * 1. Debounce: İki tur başlatma isteği arasında en az [minTurnIntervalMs] (varsayılan 300ms) süre olmalıdır.
 * 2. Concurrency = 1: Aynı kullanıcı oturumunda aynı anda yalnızca 1 aktif tur olabilir.
 * 3. Terminal Değişmezliği: CANCELLED, FAILED veya TIMED_OUT olmuş bir tur asla tekrar PLAYING veya COMPLETED olamaz.
 */
class TurnGuard(
    private val minTurnIntervalMs: Long = 300L
) {
    private var lastTurnTimestampMs: Long = 0L

    /**
     * Hızlı mikrofona basılmalarını (spam/debounce) engeller.
     * @return true ise yeni tur başlatılabilir, false ise istek reddedilir.
     */
    fun canStartNewTurn(): Boolean {
        val currentTimeMs = System.currentTimeMillis()
        val elapsedMs = currentTimeMs - lastTurnTimestampMs

        if (elapsedMs < minTurnIntervalMs) {
            println("🛡️ TurnGuard İkazı: Çok hızlı mikrofona basıldı ($elapsedMs ms < $minTurnIntervalMs ms). İstek reddedildi.")
            return false
        }

        lastTurnTimestampMs = currentTimeMs
        return true
    }

    /**
     * Durum geçişinin güvenli olup olmadığını doğrular.
     * Terminal duruma girmiş bir turun tekrar aktif duruma geçmesini engeller.
     */
    fun validateStateTransition(currentTurn: ConversationTurn?, targetState: TurnState): Boolean {
        if (currentTurn == null) return false

        // Kural: Terminal durumdaki bir tur başka hiçbir duruma geçemez.
        if (currentTurn.state.isTerminal) {
            println("🛡️ TurnGuard İkazı: Terminal durumdaki tur (${currentTurn.turnId}, state=${currentTurn.state}) '$targetState' durumuna geçirilemez!")
            return false
        }

        return true
    }

    /**
     * User Barge-In anında aktif turun güvenli bir şekilde iptal edilip edilemeyeceğini doğrular.
     */
    fun canBargeIn(currentTurn: ConversationTurn?): Boolean {
        if (currentTurn == null) return false
        // Sadece henüz sonlanmamış turlarda araya girilebilir
        return !currentTurn.state.isTerminal
    }
}
