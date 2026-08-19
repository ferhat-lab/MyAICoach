package com.ferhat.myaicoach.domain.lesson.sample

import com.ferhat.myaicoach.domain.lesson.CorrectionStyle
import com.ferhat.myaicoach.domain.lesson.SpeakingScenario

/**
 * A1Scenario2: CEFR A1 Ünite 2 Kapanış & Kilometre Taşı Canlı Konuşma Senaryosu ("Ordering & Supermarket Shopping")
 * Kullanıcı Vani ile süpermarkette ve kafede buluşur, sipariş verir, fiyat sorar ve en sevdiği şeylerden bahsetmektedir.
 */
val A1Scenario2 = SpeakingScenario(
    id = "a1_u2_scenario_milestone",
    title = "Vani ile Süpermarket ve Kafe Turu (Ünite 2 Kapanış)",
    description = "Vani'nin süpermarketinde alışveriş yap, fiyat sor, kafede içecek siparişi ver ve hobilerini paylaş.",
    aiRole = "A friendly AI cat coach running a shop and cafe.",
    goal = "Help learner complete Unit 2 milestones: order food, ask prices, tell time and express favorite things.",
    targetIds = listOf(
        "phrase_i_would_like",
        "phrase_please",
        "phrase_how_much_is",
        "phrase_what_time_is_it",
        "phrase_my_favorite_is",
        "grammar_would_like",
        "grammar_how_much",
        "grammar_telling_time",
        "grammar_like_love"
    ),
    maxTurns = 10,
    maxAiSentenceWords = 15,
    correctionStyle = CorrectionStyle.GENTLE
)
