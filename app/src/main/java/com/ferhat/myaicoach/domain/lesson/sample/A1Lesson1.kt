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

val A1Lesson1 = Lesson(
    id = "a1_u1_l1",
    level = EnglishLevel.A1,
    unitId = "a1_u1",
    order = 1,
    title = "Hello!",
    description = "Kendini basit cümlelerle tanıtmayı öğren.",
    estimatedMinutes = 10,

    objectives = listOf(
        LessonObjective(
            id = "introduce_self",
            description = "Introduce yourself using simple English sentences."
        ),
        LessonObjective(
            id = "say_origin",
            description = "Say where you are from."
        ),
        LessonObjective(
            id = "say_location",
            description = "Say where you live."
        )
    ),

    vocabulary = listOf(
        VocabularyItem(
            id = "vocab_hello",
            word = "hello",
            translation = "merhaba",
            pronunciation = "/həˈləʊ/",
            exampleSentence = "Hello! My name is Alex.",
            exampleTranslation = "Merhaba! Benim adım Alex.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_name",
            word = "name",
            translation = "isim",
            pronunciation = "/neɪm/",
            exampleSentence = "My name is Alex.",
            exampleTranslation = "Benim adım Alex.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_from",
            word = "from",
            translation = "-den / -dan",
            pronunciation = "/frəm/",
            exampleSentence = "I'm from Turkey.",
            exampleTranslation = "Türkiye'den geliyorum.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_live",
            word = "live",
            translation = "yaşamak",
            pronunciation = "/lɪv/",
            exampleSentence = "I live in Istanbul.",
            exampleTranslation = "İstanbul'da yaşıyorum.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_city",
            word = "city",
            translation = "şehir",
            pronunciation = "/ˈsɪti/",
            exampleSentence = "Istanbul is a big city.",
            exampleTranslation = "İstanbul büyük bir şehirdir.",
            role = VocabularyRole.NEW
        )
    ),

    phrases = listOf(
        PhraseItem(
            id = "phrase_my_name_is",
            phrase = "My name is...",
            translation = "Benim adım...",
            exampleSentence = "My name is Alex.",
            exampleTranslation = "Benim adım Alex.",
            role = PhraseRole.NEW
        ),
        PhraseItem(
            id = "phrase_im_from",
            phrase = "I'm from...",
            translation = "...'dan geliyorum",
            exampleSentence = "I'm from Turkey.",
            exampleTranslation = "Türkiye'den geliyorum.",
            role = PhraseRole.NEW
        ),
        PhraseItem(
            id = "phrase_i_live_in",
            phrase = "I live in...",
            translation = "...'da yaşıyorum",
            exampleSentence = "I live in Istanbul.",
            exampleTranslation = "İstanbul'da yaşıyorum.",
            role = PhraseRole.NEW
        )
    ),

    grammarTargets = listOf(
        GrammarTarget(
            id = "grammar_be_first_person",
            title = "I am → I'm",
            explanation = "Kendinden bahsederken 'I am' veya kısa hali olan 'I'm' kullanabilirsin.",
            pattern = "I am + ...",
            examples = listOf(
                GrammarExample(
                    sentence = "I'm Alex.",
                    translation = "Ben Alex."
                ),
                GrammarExample(
                    sentence = "I'm from Turkey.",
                    translation = "Türkiye'den geliyorum."
                )
            ),
            role = GrammarRole.NEW
        )
    ),

    exercises = listOf(
        Exercise(
            id = "a1_u1_l1_ex1",
            type = ExerciseType.MULTIPLE_CHOICE,
            instruction = "Doğru kelimeyi seç.",
            prompt = "I'm ___ Turkey.",
            options = listOf(
                "from",
                "name",
                "live"
            ),
            correctAnswer = "from",
            explanation = "\"I'm from...\" nereli olduğunu söylerken kullanılır.",
            targetIds = listOf(
                "phrase_im_from"
            ),
            order = 1
        ),
        Exercise(
            id = "a1_u1_l1_ex2",
            type = ExerciseType.SENTENCE_BUILDER,
            instruction = "Kelimeleri doğru sıraya koy.",
            prompt = "Türkiye'den geliyorum.",
            options = listOf(
                "Turkey",
                "from",
                "I'm"
            ),
            correctAnswer = "I'm from Turkey",
            explanation = "\"I'm from...\" kalıbı nereli olduğunu söylemek için kullanılır.",
            targetIds = listOf(
                "phrase_im_from",
                "grammar_be_first_person"
            ),
            order = 2
        ),
        Exercise(
            id = "a1_u1_l1_ex3",
            type = ExerciseType.FILL_IN_THE_BLANK,
            instruction = "Eksik kelimeyi tamamla.",
            prompt = "My ____ is Alex.",
            correctAnswer = "name",
            explanation = "\"My name is...\" adını söylerken kullanılır.",
            targetIds = listOf(
                "vocab_name",
                "phrase_my_name_is"
            ),
            order = 3
        )
    ),

    listeningContent = ListeningContent(
        id = "a1_u1_l1_listening",
        instruction = "Konuşmayı dinle ve soruyu cevapla.",

        transcript = listOf(
            DialogueLine(
                speaker = "Vani",
                text = "Hi! My name is Vani. What's your name?"
            ),
            DialogueLine(
                speaker = "Alex",
                text = "Hi! I'm Alex."
            ),
            DialogueLine(
                speaker = "Vani",
                text = "Nice to meet you, Alex. Where are you from?"
            ),
            DialogueLine(
                speaker = "Alex",
                text = "I'm from Spain."
            )
        ),

        questions = listOf(
            ListeningQuestion(
                id = "a1_u1_l1_listening_q1",
                question = "Where is Alex from?",
                options = listOf(
                    "Spain",
                    "Italy",
                    "Turkey"
                ),
                correctAnswer = "Spain",
                targetIds = listOf(
                    "phrase_im_from"
                )
            )
        ),

        targetIds = listOf(
            "phrase_my_name_is",
            "phrase_im_from"
        )
    ),

    speakingScenario = SpeakingScenario(
        id = "a1_u1_l1_speaking",
        title = "Vani ile Tanış",
        description = "Yeni tanıştığın biriyle kısa bir İngilizce konuşma yap.",
        aiRole = "A friendly person meeting the learner for the first time.",
        goal = "Help the learner introduce themselves, say where they are from and where they live.",
        targetIds = listOf(
            "phrase_my_name_is",
            "phrase_im_from",
            "phrase_i_live_in",
            "grammar_be_first_person"
        ),
        maxTurns = 8,
        maxAiSentenceWords = 15,
        correctionStyle = CorrectionStyle.GENTLE
    )
)