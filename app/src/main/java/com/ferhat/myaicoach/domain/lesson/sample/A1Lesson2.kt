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
 * A1Lesson2: "Daily Habits & Routines" (Günlük Alışkanlıklar)
 * Oxford 3000 / CEFR A1 Standartlarına Uyumlu 2. Ders Paketimiz.
 */
val A1Lesson2 = Lesson(
    id = "a1_u1_l2",
    level = EnglishLevel.A1,
    unitId = "a1_u1",
    order = 2,
    title = "Daily Habits",
    description = "Günlük rutinlerini ve zaman kalıplarını söylemeyi öğren.",
    estimatedMinutes = 10,

    objectives = listOf(
        LessonObjective(
            id = "express_routines",
            description = "Express daily activities using Present Simple (I + verb)."
        ),
        LessonObjective(
            id = "time_expressions",
            description = "Use basic time phrases like 'in the morning' and 'every day'."
        )
    ),

    vocabulary = listOf(
        VocabularyItem(
            id = "vocab_morning",
            word = "morning",
            translation = "sabah",
            pronunciation = "/ˈmɔːnɪŋ/",
            exampleSentence = "I drink coffee in the morning.",
            exampleTranslation = "Sabahları kahve içerim.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_work",
            word = "work",
            translation = "iş / çalışmak",
            pronunciation = "/wɜːk/",
            exampleSentence = "I go to work every day.",
            exampleTranslation = "Her gün işe giderim.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_coffee",
            word = "coffee",
            translation = "kahve",
            pronunciation = "/ˈkɒfi/",
            exampleSentence = "I like hot coffee.",
            exampleTranslation = "Sıcak kahve severim.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_read",
            word = "read",
            translation = "okumak",
            pronunciation = "/riːd/",
            exampleSentence = "I read books at night.",
            exampleTranslation = "Gece kitap okurum.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_night",
            word = "night",
            translation = "gece",
            pronunciation = "/naɪt/",
            exampleSentence = "Good night, see you tomorrow.",
            exampleTranslation = "İyi geceler, yarın görüşürüz.",
            role = VocabularyRole.NEW
        )
    ),

    phrases = listOf(
        PhraseItem(
            id = "phrase_in_the_morning",
            phrase = "in the morning",
            translation = "sabahleyin / sabahları",
            exampleSentence = "I wake up early in the morning.",
            exampleTranslation = "Sabahları erken uyanırım.",
            role = PhraseRole.NEW
        ),
        PhraseItem(
            id = "phrase_every_day",
            phrase = "every day",
            translation = "her gün",
            exampleSentence = "I study English every day.",
            exampleTranslation = "Her gün İngilizce çalışırım.",
            role = PhraseRole.NEW
        )
    ),

    grammarTargets = listOf(
        GrammarTarget(
            id = "grammar_present_simple_i",
            title = "Geniş Zaman (I + Eylem)",
            explanation = "Günlük rutin anlatırken eylem yalın halde kullanılır: I + eylem.",
            pattern = "I + [Fiil] + ...",
            examples = listOf(
                GrammarExample(
                    sentence = "I drink tea.",
                    translation = "Çay içerim."
                ),
                GrammarExample(
                    sentence = "I work every day.",
                    translation = "Her gün çalışırım."
                )
            ),
            role = GrammarRole.NEW
        )
    ),

    exercises = listOf(
        Exercise(
            id = "a1_u1_l2_ex1",
            type = ExerciseType.MULTIPLE_CHOICE,
            instruction = "Doğru kelimeyi seç.",
            prompt = "I drink ___ in the morning.",
            options = listOf(
                "coffee",
                "night",
                "work"
            ),
            correctAnswer = "coffee",
            explanation = "Sabahları içecek olarak \"coffee\" (kahve) kullanılır.",
            targetIds = listOf("vocab_coffee", "phrase_in_the_morning"),
            order = 1
        ),
        Exercise(
            id = "a1_u1_l2_ex2",
            type = ExerciseType.SENTENCE_BUILDER,
            instruction = "Kelimeleri doğru sıraya koy.",
            prompt = "Her gün çalışırım.",
            options = listOf(
                "every",
                "day",
                "I",
                "work"
            ),
            correctAnswer = "I work every day.",
            explanation = "İngilizcede zaman zarfı \"every day\" cümlenin sonuna gelir.",
            targetIds = listOf("phrase_every_day", "grammar_present_simple_i"),
            order = 2
        ),
        Exercise(
            id = "a1_u1_l2_ex3",
            type = ExerciseType.FILL_IN_THE_BLANK,
            instruction = "Eksik kelimeyi tamamla.",
            prompt = "I read books at ____.",
            correctAnswer = "night",
            explanation = "Geceleyin ifadesi için \"at night\" kullanılır.",
            targetIds = listOf("vocab_night", "vocab_read"),
            order = 3
        )
    ),

    listeningContent = ListeningContent(
        id = "a1_u1_l2_listening",
        instruction = "Konuşmayı dinle ve soruyu cevapla.",
        transcript = listOf(
            DialogueLine(
                speaker = "Vani",
                text = "What do you do in the morning, Alex?"
            ),
            DialogueLine(
                speaker = "Alex",
                text = "I drink coffee and read a book."
            ),
            DialogueLine(
                speaker = "Vani",
                text = "Do you go to work every day?"
            ),
            DialogueLine(
                speaker = "Alex",
                text = "Yes, I work every day."
            )
        ),
        questions = listOf(
            ListeningQuestion(
                id = "a1_u1_l2_listening_q1",
                question = "What does Alex drink in the morning?",
                options = listOf(
                    "Coffee",
                    "Tea",
                    "Water"
                ),
                correctAnswer = "Coffee",
                targetIds = listOf("vocab_coffee")
            )
        ),
        targetIds = listOf("vocab_coffee", "phrase_every_day")
    ),

    speakingScenario = SpeakingScenario(
        id = "a1_u1_l2_speaking",
        title = "Vani ile Günlük Rutin Sohbeti",
        description = "Vani ile sabah alışkanlıkların ve günlük işlerin hakkında konuş.",
        aiRole = "A friendly AI cat coach asking about daily habits.",
        goal = "Help learner talk about what they do in the morning and their work habits.",
        targetIds = listOf(
            "vocab_morning",
            "vocab_coffee",
            "phrase_every_day",
            "grammar_present_simple_i"
        ),
        maxTurns = 8,
        maxAiSentenceWords = 15,
        correctionStyle = CorrectionStyle.GENTLE
    )
)
