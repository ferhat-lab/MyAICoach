package com.ferhat.myaicoach.domain.lesson.sample

import com.ferhat.myaicoach.data.model.EnglishLevel
import com.ferhat.myaicoach.domain.lesson.*

val A2Lesson3 = Lesson(
    id = "a2_u1_l3",
    level = EnglishLevel.A2,
    unitId = "a2_u1",
    order = 3,
    title = "Last Weekend",
    description = "Zaman zarfları (ago, last week, in 2020) ile ne zaman gerçekleştiğini belirtmeyi öğren.",
    estimatedMinutes = 10,

    objectives = listOf(
        LessonObjective(id = "past_time_expressions", description = "Use past time expressions accurately.")
    ),

    vocabulary = listOf(
        VocabularyItem(
            id = "vocab_ago",
            word = "ago",
            translation = "önce",
            pronunciation = "/əˈɡəʊ/",
            exampleSentence = "I graduated two years ago.",
            exampleTranslation = "İki yıl önce mezun oldum.",
            role = VocabularyRole.NEW
        )
    ),

    phrases = listOf(
        PhraseItem(
            id = "phrase_last_weekend",
            phrase = "Last weekend...",
            translation = "Geçen hafta sonu...",
            exampleSentence = "Last weekend I went shopping.",
            exampleTranslation = "Geçen hafta sonu alışverişe gittim.",
            role = PhraseRole.NEW
        )
    ),

    grammarTargets = listOf(
        GrammarTarget(
            id = "past_time_markers",
            title = "Geçmiş Zaman Belirteçleri",
            explanation = "'Ago' cümlenin sonuna gelir, 'last' ise isimden önce gelir: two days ago / last month.",
            pattern = "Time + ago / last + Time",
            examples = listOf(GrammarExample(sentence = "I arrived 3 days ago.", translation = "3 gün önce geldim.")),
            role = GrammarRole.NEW
        )
    ),

    exercises = listOf(
        Exercise(
            id = "a2_u1_l3_ex1",
            type = ExerciseType.MULTIPLE_CHOICE,
            instruction = "Boşluğu doldur.",
            prompt = "He left two hours _____.",
            options = listOf("ago", "last", "yesterday"),
            correctAnswer = "ago",
            explanation = "Saat/zaman miktarından sonra 'ago' gelir.",
            targetIds = listOf("past_time_markers"),
            order = 1
        )
    ),

    listeningContent = ListeningContent(
        id = "a2_u1_l3_listening",
        instruction = "Dinle.",
        transcript = listOf(
            DialogueLine(speaker = "Vani", text = "When did you buy this car?"),
            DialogueLine(speaker = "Alex", text = "I bought it two months ago.")
        ),
        questions = listOf(
            ListeningQuestion(
                id = "a2_u1_l3_q1",
                question = "When did Alex buy the car?",
                options = listOf("Two months ago", "Yesterday", "Last year"),
                correctAnswer = "Two months ago",
                targetIds = listOf("past_time_markers")
            )
        ),
        targetIds = listOf("past_time_markers")
    ),

    speakingScenario = SpeakingScenario(
        id = "a2_u1_l3_speaking",
        title = "🎙️ Hafta Sonu Planı Anlatımı",
        description = "Geçen hafta sonu ne yaptığını zaman belirteçleriyle anlat.",
        aiRole = "A friend asking about your activities.",
        goal = "Practice using time markers like last weekend, ago.",
        targetIds = listOf("vocab_ago", "phrase_last_weekend")
    )
)
