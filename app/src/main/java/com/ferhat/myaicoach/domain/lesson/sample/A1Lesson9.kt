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
 * A1Lesson9: "In the City" (Şehirde ve Mekanlarda)
 * CEFR A1 Ünite 3 - 9. Ders Paketimiz.
 */
val A1Lesson9 = Lesson(
    id = "a1_u3_l9",
    level = EnglishLevel.A1,
    unitId = "a1_u3",
    order = 9,
    title = "In the City",
    description = "Şehirdeki mekanları ve gitmek istediğin yerleri söylemeyi öğren.",
    estimatedMinutes = 10,

    objectives = listOf(
        LessonObjective(
            id = "name_city_places",
            description = "Identify common places in a city like park, hospital, bus."
        ),
        LessonObjective(
            id = "express_destination",
            description = "Express destinations using 'go to...'."
        )
    ),

    vocabulary = listOf(
        VocabularyItem(
            id = "vocab_park",
            word = "park",
            translation = "park",
            pronunciation = "/pɑːk/",
            exampleSentence = "I walk in the park.",
            exampleTranslation = "Parkta yürürüm.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_hospital",
            word = "hospital",
            translation = "hastane",
            pronunciation = "/ˈhɒspɪtl/",
            exampleSentence = "The hospital is big.",
            exampleTranslation = "Hastane büyüktür.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_bus",
            word = "bus",
            translation = "otobüs",
            pronunciation = "/bʌs/",
            exampleSentence = "I take the bus to work.",
            exampleTranslation = "İşe gitmek için otobüse binerim.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_museum",
            word = "museum",
            translation = "müze",
            pronunciation = "/mjuˈziːəm/",
            exampleSentence = "This is a famous museum.",
            exampleTranslation = "Bu ünlü bir müzedir.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_go",
            word = "go",
            translation = "gitmek",
            pronunciation = "/ɡəʊ/",
            exampleSentence = "I go to the park every day.",
            exampleTranslation = "Her gün parka giderim.",
            role = VocabularyRole.NEW
        )
    ),

    phrases = listOf(
        PhraseItem(
            id = "phrase_go_to",
            phrase = "go to...",
            translation = "...'a/e gitmek",
            exampleSentence = "I want to go to the museum.",
            exampleTranslation = "Müzeye gitmek istiyorum.",
            role = PhraseRole.NEW
        ),
        PhraseItem(
            id = "phrase_by_bus",
            phrase = "by bus",
            translation = "otobüsle",
            exampleSentence = "I go by bus.",
            exampleTranslation = "Otobüsle giderim.",
            role = PhraseRole.NEW
        )
    ),

    grammarTargets = listOf(
        GrammarTarget(
            id = "grammar_go_to",
            title = "Yönelme Bildirme (go to + Mekan)",
            explanation = "Bir mekana gitmeyi ifade ederken 'go to + [Mekan]' yapısı kullanılır.",
            pattern = "go to + the + [Mekan]",
            examples = listOf(
                GrammarExample(
                    sentence = "I go to the park.",
                    translation = "Parka giderim."
                ),
                GrammarExample(
                    sentence = "She goes to the hospital.",
                    translation = "O hastaneye gider."
                )
            ),
            role = GrammarRole.NEW
        )
    ),

    exercises = listOf(
        Exercise(
            id = "a1_u3_l9_ex1",
            type = ExerciseType.MULTIPLE_CHOICE,
            instruction = "Doğru kelimeyi seç.",
            prompt = "I go to the ____ by bus.",
            options = listOf(
                "park",
                "tea",
                "price"
            ),
            correctAnswer = "park",
            explanation = "Gidilen mekan olarak \"park\" seçilir.",
            targetIds = listOf("vocab_park", "phrase_go_to"),
            order = 1
        ),
        Exercise(
            id = "a1_u3_l9_ex2",
            type = ExerciseType.SENTENCE_BUILDER,
            instruction = "Kelimeleri doğru sıraya koy.",
            prompt = "Müzeye gitmek istiyorum.",
            options = listOf(
                "go",
                "to",
                "want",
                "I",
                "the",
                "museum"
            ),
            correctAnswer = "I want to go to the museum",
            explanation = "Yönelme cümlesi \"I want to go to the...\" kalıbıyla kurulur.",
            targetIds = listOf("phrase_go_to", "grammar_go_to"),
            order = 2
        ),
        Exercise(
            id = "a1_u3_l9_ex3",
            type = ExerciseType.FILL_IN_THE_BLANK,
            instruction = "Eksik kelimeyi tamamla.",
            prompt = "I go to work by ____.",
            options = listOf(
                "bus",
                "museum",
                "hospital"
            ),
            correctAnswer = "bus",
            explanation = "Ulaşım aracı olarak \"bus\" (otobüs) kullanılır.",
            targetIds = listOf("vocab_bus", "phrase_by_bus"),
            order = 3
        )
    ),

    listeningContent = ListeningContent(
        id = "a1_u3_l9_listening",
        instruction = "Konuşmayı dinle ve soruyu cevapla.",
        transcript = listOf(
            DialogueLine(
                speaker = "Vani",
                text = "Where do you want to go today, Alex?"
            ),
            DialogueLine(
                speaker = "Alex",
                text = "I want to go to the museum by bus!"
            ),
            DialogueLine(
                speaker = "Vani",
                text = "Great! The museum is near the park."
            ),
            DialogueLine(
                speaker = "Alex",
                text = "Awesome, let's go!"
            )
        ),
        questions = listOf(
            ListeningQuestion(
                id = "a1_u3_l9_listening_q1",
                question = "Where does Alex want to go?",
                options = listOf(
                    "To the museum",
                    "To the hospital",
                    "Home"
                ),
                correctAnswer = "To the museum",
                targetIds = listOf("vocab_museum", "phrase_go_to")
            )
        ),
        targetIds = listOf("phrase_go_to", "vocab_bus")
    ),

    speakingScenario = SpeakingScenario(
        id = "a1_u3_l9_speaking",
        title = "Vani ile Şehir Turu",
        description = "Vani'ye şehirde nereye gitmek istediğini anlat.",
        aiRole = "A friendly AI cat tour guide in a big city.",
        goal = "Help learner express where they want to go using 'I want to go to...'.",
        targetIds = listOf(
            "vocab_park",
            "vocab_museum",
            "phrase_go_to",
            "grammar_go_to"
        ),
        maxTurns = 8,
        maxAiSentenceWords = 15,
        correctionStyle = CorrectionStyle.GENTLE
    )
)
