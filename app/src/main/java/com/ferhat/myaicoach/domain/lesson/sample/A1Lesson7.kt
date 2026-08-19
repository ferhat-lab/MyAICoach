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
 * A1Lesson7: "Numbers & Telling Time" (Sayılar ve Saat Sorma)
 * Oxford 3000 / CEFR A1 Ünite 2 - 7. Ders Paketimiz.
 */
val A1Lesson7 = Lesson(
    id = "a1_u2_l7",
    level = EnglishLevel.A1,
    unitId = "a1_u2",
    order = 7,
    title = "Numbers & Telling Time",
    description = "Sayıları ve saati söylemeyi öğren.",
    estimatedMinutes = 10,

    objectives = listOf(
        LessonObjective(
            id = "tell_time",
            description = "Ask and tell the time using 'What time is it?'."
        ),
        LessonObjective(
            id = "use_numbers",
            description = "Count items using basic English numbers."
        )
    ),

    vocabulary = listOf(
        VocabularyItem(
            id = "vocab_time",
            word = "time",
            translation = "zaman / saat",
            pronunciation = "/taɪm/",
            exampleSentence = "What time is it?",
            exampleTranslation = "Saat kaç?",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_clock",
            word = "clock",
            translation = "duvar saati",
            pronunciation = "/klɒk/",
            exampleSentence = "Look at the clock.",
            exampleTranslation = "Duvar saatine bak.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_hour",
            word = "hour",
            translation = "saat (süre)",
            pronunciation = "/ˈaʊə/",
            exampleSentence = "I study for one hour.",
            exampleTranslation = "Bir saat boyunca çalışırım.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_ten",
            word = "ten",
            translation = "on (10)",
            pronunciation = "/ten/",
            exampleSentence = "It is ten o'clock.",
            exampleTranslation = "Saat on.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_early",
            word = "early",
            translation = "erken",
            pronunciation = "/ˈɜːli/",
            exampleSentence = "I wake up early.",
            exampleTranslation = "Erken uyanırım.",
            role = VocabularyRole.NEW
        )
    ),

    phrases = listOf(
        PhraseItem(
            id = "phrase_what_time_is_it",
            phrase = "What time is it?",
            translation = "Saat kaç?",
            exampleSentence = "Excuse me, what time is it?",
            exampleTranslation = "Afedersiniz, saat kaç?",
            role = PhraseRole.NEW
        ),
        PhraseItem(
            id = "phrase_it_is_oclock",
            phrase = "It is ... o'clock.",
            translation = "Saat...",
            exampleSentence = "It is nine o'clock.",
            exampleTranslation = "Saat dokuz.",
            role = PhraseRole.NEW
        )
    ),

    grammarTargets = listOf(
        GrammarTarget(
            id = "grammar_telling_time",
            title = "Saat Sorma ve Söyleme (It is ... o'clock)",
            explanation = "Tam saatleri söylerken 'It is + [Sayı] + o'clock' yapısı kullanılır.",
            pattern = "It is + [Sayı] + o'clock",
            examples = listOf(
                GrammarExample(
                    sentence = "It is eight o'clock.",
                    translation = "Saat sekiz."
                ),
                GrammarExample(
                    sentence = "It is ten o'clock.",
                    translation = "Saat on."
                )
            ),
            role = GrammarRole.NEW
        )
    ),

    exercises = listOf(
        Exercise(
            id = "a1_u2_l7_ex1",
            type = ExerciseType.MULTIPLE_CHOICE,
            instruction = "Doğru kelimeyi seç.",
            prompt = "What ____ is it?",
            options = listOf(
                "time",
                "price",
                "table"
            ),
            correctAnswer = "time",
            explanation = "Saat sorusunda \"What time is it?\" kullanılır.",
            targetIds = listOf("vocab_time", "phrase_what_time_is_it"),
            order = 1
        ),
        Exercise(
            id = "a1_u2_l7_ex2",
            type = ExerciseType.SENTENCE_BUILDER,
            instruction = "Kelimeleri doğru sıraya koy.",
            prompt = "Saat sekiz.",
            options = listOf(
                "is",
                "eight",
                "It",
                "o'clock"
            ),
            correctAnswer = "It is eight o'clock",
            explanation = "Tam saat söylenirken \"It is... o'clock\" kullanılır.",
            targetIds = listOf("phrase_it_is_oclock", "grammar_telling_time"),
            order = 2
        ),
        Exercise(
            id = "a1_u2_l7_ex3",
            type = ExerciseType.FILL_IN_THE_BLANK,
            instruction = "Eksik kelimeyi tamamla.",
            prompt = "I wake up ____ in the morning.",
            options = listOf(
                "early",
                "ten",
                "clock"
            ),
            correctAnswer = "early",
            explanation = "Erken uyanmak anlamında \"early\" kullanılır.",
            targetIds = listOf("vocab_early"),
            order = 3
        )
    ),

    listeningContent = ListeningContent(
        id = "a1_u2_l7_listening",
        instruction = "Konuşmayı dinle ve soruyu cevapla.",
        transcript = listOf(
            DialogueLine(
                speaker = "Alex",
                text = "Excuse me Vani, what time is it?"
            ),
            DialogueLine(
                speaker = "Vani",
                text = "It is ten o'clock in the morning!"
            ),
            DialogueLine(
                speaker = "Alex",
                text = "Oh, it is early! Thank you."
            ),
            DialogueLine(
                speaker = "Vani",
                text = "You are welcome!"
            )
        ),
        questions = listOf(
            ListeningQuestion(
                id = "a1_u2_l7_listening_q1",
                question = "What time is it?",
                options = listOf(
                    "Ten o'clock",
                    "Five o'clock",
                    "Midnight"
                ),
                correctAnswer = "Ten o'clock",
                targetIds = listOf("vocab_ten", "phrase_it_is_oclock")
            )
        ),
        targetIds = listOf("phrase_what_time_is_it", "vocab_time")
    ),

    speakingScenario = SpeakingScenario(
        id = "a1_u2_l7_speaking",
        title = "Vani ile Saat ve Zaman Sohbeti",
        description = "Vani'ye saatin kaç olduğunu sor ve günün saatlerinden bahset.",
        aiRole = "A friendly AI cat coach carrying a pocket clock.",
        goal = "Help learner ask 'What time is it?' and express time accurately.",
        targetIds = listOf(
            "vocab_time",
            "vocab_ten",
            "phrase_what_time_is_it",
            "grammar_telling_time"
        ),
        maxTurns = 8,
        maxAiSentenceWords = 15,
        correctionStyle = CorrectionStyle.GENTLE
    )
)
