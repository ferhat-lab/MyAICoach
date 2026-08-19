package com.ferhat.myaicoach.domain.lesson.sample

import com.ferhat.myaicoach.domain.lesson.CorrectionStyle
import com.ferhat.myaicoach.domain.lesson.SpeakingScenario

/**
 * A1Scenario1: CEFR A1 Ünite 1 Kapanış & Kilometre Taşı Canlı Konuşma Senaryosu ("Cafe Meetup & Introductions")
 * Kullanıcı Vani ile kafede buluşur, kendini tanıtır, nereli olduğunu, ailesini ve günlük rutinini söyler.
 */
val A1Scenario1 = SpeakingScenario(
    id = "a1_u1_scenario_milestone",
    title = "Kafede Vani ile Tanışma (Ünite 1 Kapanış)",
    description = "Sanal kafede Vani ile buluş, kendini ve aileni tanıt, günlük alışkanlıklarından bahset.",
    aiRole = "A friendly AI cat coach named Vani sitting at a cozy café.",
    goal = "Help learner complete Unit 1 milestones: introduce self, say origin, mention family & daily habit.",
    targetIds = listOf(
        "phrase_my_name_is",
        "phrase_im_from",
        "phrase_i_live_in",
        "phrase_in_the_morning",
        "phrase_every_day",
        "phrase_this_is",
        "grammar_be_first_person",
        "grammar_present_simple_i",
        "grammar_possessive_my_your",
        "grammar_there_is"
    ),
    maxTurns = 10,
    maxAiSentenceWords = 15,
    correctionStyle = CorrectionStyle.GENTLE
)
