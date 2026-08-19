package com.ferhat.myaicoach.domain.lesson.sample

import com.ferhat.myaicoach.domain.lesson.CorrectionStyle
import com.ferhat.myaicoach.domain.lesson.SpeakingScenario

/**
 * A1Scenario3: CEFR A1 → A2 Seviye Bitirme Sınavı & Vani Canlı Konuşma Mezuniyet Senaryosu ("A1 Graduation Challenge & City Travel")
 * Kullanıcı Vani ile A1 seviyesinin tüm ünitelerini (Tanışma, Rutinler, Aile, Ev, Yiyecekler, Alışveriş, Şehir ve Seyahat) birleştiren mezuniyet sohbeti gerçekleştirir.
 */
val A1Scenario3 = SpeakingScenario(
    id = "a1_u3_scenario_graduation",
    title = "🎓 A1 → A2 Seviye Mezuniyet Konuşması (Vani ile Şehir & Otel Turu)",
    description = "Vani ile A1 seviyesinde öğrendiğin tüm konuları kapsayan 10 turluk mezuniyet konuşmasını tamamla ve A2 seviyesine hak kazan!",
    aiRole = "A proud AI cat coach evaluating the learner for A1 graduation.",
    goal = "Verify learner mastery across all A1 topics: self introduction, daily routine, ordering food, asking directions and hotel check-in.",
    targetIds = listOf(
        "phrase_my_name_is",
        "phrase_im_from",
        "phrase_every_day",
        "phrase_i_would_like",
        "phrase_how_much_is",
        "phrase_where_is",
        "phrase_how_is_the_weather",
        "phrase_i_have_a_reservation",
        "grammar_be_first_person",
        "grammar_present_simple_i",
        "grammar_would_like",
        "grammar_where_is",
        "grammar_have_has"
    ),
    maxTurns = 12,
    maxAiSentenceWords = 15,
    correctionStyle = CorrectionStyle.GENTLE
)
