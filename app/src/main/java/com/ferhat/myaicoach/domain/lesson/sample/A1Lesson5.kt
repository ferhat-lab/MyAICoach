package com.ferhat.myaicoach.domain.lesson.sample

import com.ferhat.myaicoach.data.model.EnglishLevel
import com.ferhat.myaicoach.domain.lesson.CorrectionStyle
import com.ferhat.myaicoach.domain.lesson.DialogueLine
import com.ferhat.myaicoach.domain.lesson.Exercise
import com.ferhat.myaicoach.domain.lesson.ExerciseType
import com.ferhat.myaicoach.domain.lesson.GrammarExample
import com.ferhat.myaicoach.domain.lesson.GrammarRole
import com.ferhat.myaicoach.domain.lesson.GrammarTarget
import com.ferhat.myaicoach.domain.lesson.Lesson
import com.ferhat.myaicoach.domain.lesson.LessonObjective
import com.ferhat.myaicoach.domain.lesson.ListeningContent
import com.ferhat.myaicoach.domain.lesson.ListeningQuestion
import com.ferhat.myaicoach.domain.lesson.PhraseItem
import com.ferhat.myaicoach.domain.lesson.PhraseRole
import com.ferhat.myaicoach.domain.lesson.SpeakingScenario
import com.ferhat.myaicoach.domain.lesson.VocabularyItem
import com.ferhat.myaicoach.domain.lesson.VocabularyRole

/**
 * A1Lesson5: "Food & Drinks" (Yiyecekler ve İçecekler)
 * CEFR A1 Ünite 2 Başlangıç Dersi.
 */
val A1Lesson5 = Lesson(
    id = "a1_u2_l5",
    level = EnglishLevel.A1,
    unitId = "a1_u2",
    order = 5,
    title = "Food & Drinks",
    description = "Yiyecek ve içecek isimlerini söylemeyi ve kibarca istemeyi öğren.",
    estimatedMinutes = 10,

    objectives = listOf(
        LessonObjective(
            id = "order_food",
            description = "Order food and drinks politely."
        ),
        LessonObjective(
            id = "express_preferences",
            description = "Express what you would like using 'I would like'."
        )
    ),

    vocabulary = listOf(
        VocabularyItem(
            id = "vocab_water",
            word = "water",
            translation = "su",
            pronunciation = "/ˈwɔːtə/",
            exampleSentence = "I drink cold water.",
            exampleTranslation = "Soğuk su içerim.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_tea",
            word = "tea",
            translation = "çay",
            pronunciation = "/tiː/",
            exampleSentence = "I would like a cup of tea.",
            exampleTranslation = "Bir fincan çay almak isterim.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_apple",
            word = "apple",
            translation = "elma",
            pronunciation = "/ˈæpl/",
            exampleSentence = "This is a red apple.",
            exampleTranslation = "Bu kırmızı bir elmadır.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_bread",
            word = "bread",
            translation = "ekmek",
            pronunciation = "/bred/",
            exampleSentence = "I eat fresh bread.",
            exampleTranslation = "Taze ekmek yerim.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_want",
            word = "want",
            translation = "istemek",
            pronunciation = "/wɒnt/",
            exampleSentence = "I want some water, please.",
            exampleTranslation = "Biraz su istiyorum, lütfen.",
            role = VocabularyRole.NEW
        )
    ),

    phrases = listOf(
        PhraseItem(
            id = "phrase_i_would_like",
            phrase = "I would like...",
            translation = "...almak isterim",
            exampleSentence = "I would like a tea, please.",
            exampleTranslation = "Bir çay almak isterim, lütfen.",
            role = PhraseRole.NEW
        ),
        PhraseItem(
            id = "phrase_please",
            phrase = "please",
            translation = "lütfen",
            exampleSentence = "Water, please.",
            exampleTranslation = "Su, lütfen.",
            role = PhraseRole.NEW
        )
    ),

    grammarTargets = listOf(
        GrammarTarget(
            id = "grammar_would_like",
            title = "Kibar İstek (I would like...)",
            explanation = "Bir restoran veya kafede kibarca bir şey isterken 'I would like...' veya kısa şekli olan 'I'd like...' kullanılır.",
            pattern = "I would like + [İsim] + please",
            examples = listOf(
                GrammarExample(
                    sentence = "I would like water, please.",
                    translation = "Su almak isterim, lütfen."
                ),
                GrammarExample(
                    sentence = "I'd like an apple.",
                    translation = "Bir elma isterim."
                )
            ),
            role = GrammarRole.NEW
        )
    ),

    exercises = listOf(
        Exercise(
            id = "a1_u2_l5_ex1",
            type = ExerciseType.MULTIPLE_CHOICE,
            instruction = "Doğru kelimeyi seç.",
            prompt = "I would like a cup of ____, please.",
            options = listOf(
                "tea",
                "night",
                "house"
            ),
            correctAnswer = "tea",
            explanation = "Fincanla içilen içecek olarak \"tea\" (çay) seçilir.",
            targetIds = listOf("vocab_tea", "phrase_i_would_like"),
            order = 1
        ),
        Exercise(
            id = "a1_u2_l5_ex2",
            type = ExerciseType.SENTENCE_BUILDER,
            instruction = "Kelimeleri doğru sıraya koy.",
            prompt = "Biraz su almak isterim, lütfen.",
            options = listOf(
                "would",
                "I",
                "like",
                "water",
                "please"
            ),
            correctAnswer = "I would like water please",
            explanation = "Kibar istek cümlesi \"I would like... please\" kalıbıyla kurulur.",
            targetIds = listOf("phrase_i_would_like", "grammar_would_like"),
            order = 2
        ),
        Exercise(
            id = "a1_u2_l5_ex3",
            type = ExerciseType.FILL_IN_THE_BLANK,
            instruction = "Eksik kelimeyi tamamla.",
            prompt = "I eat fresh ____.",
            options = listOf(
                "bread",
                "water",
                "tea"
            ),
            correctAnswer = "bread",
            explanation = "Yenilen yiyecek olarak \"bread\" (ekmek) kullanılır.",
            targetIds = listOf("vocab_bread"),
            order = 3
        )
    ),

    listeningContent = ListeningContent(
        id = "a1_u2_l5_listening",
        instruction = "Konuşmayı dinle ve soruyu cevapla.",
        transcript = listOf(
            DialogueLine(
                speaker = "Vani",
                text = "Welcome to the cafe! What would you like?"
            ),
            DialogueLine(
                speaker = "Alex",
                text = "Hello! I would like tea and bread, please."
            ),
            DialogueLine(
                speaker = "Vani",
                text = "Sure! Would you like some water too?"
            ),
            DialogueLine(
                speaker = "Alex",
                text = "Yes, water please."
            )
        ),
        questions = listOf(
            ListeningQuestion(
                id = "a1_u2_l5_listening_q1",
                question = "What does Alex order first?",
                options = listOf(
                    "Tea and bread",
                    "Coffee and apple",
                    "Only milk"
                ),
                correctAnswer = "Tea and bread",
                targetIds = listOf("vocab_tea", "vocab_bread")
            )
        ),
        targetIds = listOf("phrase_i_would_like", "vocab_water")
    ),

    speakingScenario = SpeakingScenario(
        id = "a1_u2_l5_speaking",
        title = "Vani'nin Kafesinde Sipariş Ver",
        description = "Vani'nin işlettiği tatlı kafede içecek ve yiyecek siparişi ver.",
        aiRole = "A friendly AI cat waiter running a cozy cafe.",
        goal = "Help learner order tea, water or bread politely using 'I would like... please'.",
        targetIds = listOf(
            "vocab_water",
            "vocab_tea",
            "phrase_i_would_like",
            "grammar_would_like"
        ),
        maxTurns = 8,
        maxAiSentenceWords = 15,
        correctionStyle = CorrectionStyle.GENTLE
    )
)
