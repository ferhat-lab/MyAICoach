package com.ferhat.myaicoach.domain.model

import com.ferhat.myaicoach.data.model.EnglishLevel

/**
 * StudentState: Öğrenci ilerleme durumu veri modeli.
 * Öğrencinin tamamladığı dersler, öğrendiği kelime ID'leri (knownTargetIds) ve zayıf konularını takip eder.
 */
data class StudentState(
    val level: EnglishLevel = EnglishLevel.A1,
    val completedLessonIds: Set<String> = setOf("a1_u1_l1"),
    val knownTargetIds: Set<String> = setOf(
        "vocab_hello",
        "vocab_name",
        "vocab_from",
        "phrase_my_name_is",
        "phrase_im_from"
    ),
    val weakTargetIds: Set<String> = emptySet(),
    val totalXp: Int = 1240,
    val streakDays: Int = 7,
    val dailyGoalMinutes: Int = 10,
    val completedMinutesToday: Int = 8
) {
    // A2 Seviyesine geçiş kontrolü (%80 ustalaşma = 400+ kelime)
    val isReadyForA2: Boolean
        get() = knownTargetIds.size >= 400
}
