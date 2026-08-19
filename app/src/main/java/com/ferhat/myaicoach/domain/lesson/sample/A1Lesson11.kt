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
 * A1Lesson11: "Weather & Seasons" (Hava Durumu ve Mevsimler)
 * Oxford 3000 / CEFR A1 Ünite 3 - 11. Ders Paketimiz.
 */
val A1Lesson11 = Lesson(
    id = "a1_u3_l11",
    level = EnglishLevel.A1,
    unitId = "a1_u3",
    order = 11,
    title = "Weather & Seasons",
    description = "Hava durumunu ve mevsimleri tanımlamayı öğren.",
    estimatedMinutes = 10,

    objectives = listOf(
        LessonObjective(
            id = "describe_weather",
            description = "Describe the weather using 'It is sunny/cold'."
        ),
        LessonObjective(
            id = "talk_seasons",
            description = "Mention seasons like summer and winter."
        )
    ),

    vocabulary = listOf(
        VocabularyItem(
            id = "vocab_weather",
            word = "weather",
            translation = "hava durumu",
            pronunciation = "/ˈweðə/",
            exampleSentence = "How is the weather today?",
            exampleTranslation = "Bugün hava nasıl?",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_sunny",
            word = "sunny",
            translation = "güneşli",
            pronunciation = "/ˈsʌni/",
            exampleSentence = "It is sunny today.",
            exampleTranslation = "Bugün hava güneşli.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_cold",
            word = "cold",
            translation = "soğuk",
            pronunciation = "/kəʊld/",
            exampleSentence = "It is cold in winter.",
            exampleTranslation = "Kışın hava soğuktur.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_hot",
            word = "hot",
            translation = "sıcak",
            pronunciation = "/hɒt/",
            exampleSentence = "Summer is very hot.",
            exampleTranslation = "Yaz mevsimi çok sıcaktır.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_summer",
            word = "summer",
            translation = "yaz mevsimi",
            pronunciation = "/ˈsʌmə/",
            exampleSentence = "I love summer.",
            exampleTranslation = "Yaz mevsimini çok seviyorum.",
            role = VocabularyRole.NEW
        )
    ),

    phrases = listOf(
        PhraseItem(
            id = "phrase_how_is_the_weather",
            phrase = "How is the weather?",
            translation = "Hava nasıl?",
            exampleSentence = "How is the weather in Istanbul?",
            exampleTranslation = "İstanbul'da hava nasıl?",
            role = PhraseRole.NEW
        ),
        PhraseItem(
            id = "phrase_it_is_sunny",
            phrase = "It is sunny.",
            translation = "Hava güneşli.",
            exampleSentence = "It is sunny today.",
            exampleTranslation = "Bugün hava güneşli.",
            role = PhraseRole.NEW
        )
    ),

    grammarTargets = listOf(
        GrammarTarget(
            id = "grammar_it_is_weather",
            title = "Hava Durumu Bildirme (It is + Sıfat)",
            explanation = "Hava durumunu tarif ederken 'It is + [Sıfat]' (It is sunny / cold) yapısı kullanılır.",
            pattern = "It is + [Sıfat]",
            examples = listOf(
                GrammarExample(
                    sentence = "It is sunny.",
                    translation = "Hava güneşli."
                ),
                GrammarExample(
                    sentence = "It is cold today.",
                    translation = "Bugün hava soğuk."
                )
            ),
            role = GrammarRole.NEW
        )
    ),

    exercises = listOf(
        Exercise(
            id = "a1_u3_l11_ex1",
            type = ExerciseType.MULTIPLE_CHOICE,
            instruction = "Doğru kelimeyi seç.",
            prompt = "How is the ____ today?",
            options = listOf(
                "weather",
                "street",
                "bag"
            ),
            correctAnswer = "weather",
            explanation = "Hava durumu sorusunda \"weather\" kelimesi kullanılır.",
            targetIds = listOf("vocab_weather", "phrase_how_is_the_weather"),
            order = 1
        ),
        Exercise(
            id = "a1_u3_l11_ex2",
            type = ExerciseType.SENTENCE_BUILDER,
            instruction = "Kelimeleri doğru sıraya koy.",
            prompt = "Bugün hava güneşli.",
            options = listOf(
                "sunny",
                "today",
                "It",
                "is"
            ),
            correctAnswer = "It is sunny today",
            explanation = "Hava durumu tanımı \"It is... today\" şeklinde yapılır.",
            targetIds = listOf("phrase_it_is_sunny", "grammar_it_is_weather"),
            order = 2
        ),
        Exercise(
            id = "a1_u3_l11_ex3",
            type = ExerciseType.FILL_IN_THE_BLANK,
            instruction = "Eksik kelimeyi tamamla.",
            prompt = "I love ____ because it is warm.",
            options = listOf(
                "summer",
                "cold",
                "hour"
            ),
            correctAnswer = "summer",
            explanation = "Mevsim ismi olarak \"summer\" (yaz) seçilir.",
            targetIds = listOf("vocab_summer"),
            order = 3
        )
    ),

    listeningContent = ListeningContent(
        id = "a1_u3_l11_listening",
        instruction = "Konuşmayı dinle ve soruyu cevapla.",
        transcript = listOf(
            DialogueLine(
                speaker = "Alex",
                text = "Hi Vani! How is the weather today?"
            ),
            DialogueLine(
                speaker = "Vani",
                text = "It is very sunny and hot!"
            ),
            DialogueLine(
                speaker = "Alex",
                text = "Great! I love sunny weather."
            ),
            DialogueLine(
                speaker = "Vani",
                text = "Let's go to the park!"
            )
        ),
        questions = listOf(
            ListeningQuestion(
                id = "a1_u3_l11_listening_q1",
                question = "How is the weather today?",
                options = listOf(
                    "Sunny and hot",
                    "Cold and rainy",
                    "Snowy"
                ),
                correctAnswer = "Sunny and hot",
                targetIds = listOf("vocab_sunny", "vocab_hot")
            )
        ),
        targetIds = listOf("phrase_how_is_the_weather", "vocab_weather")
    ),

    speakingScenario = SpeakingScenario(
        id = "a1_u3_l11_speaking",
        title = "Vani ile Hava Durumu Sohbeti",
        description = "Vani'ye yaşadığın yerdeki hava durumundan ve en sevdiğin mevsimden bahset.",
        aiRole = "A friendly AI cat weather reporter.",
        goal = "Help learner describe weather using 'It is sunny/cold' and talk about seasons.",
        targetIds = listOf(
            "vocab_weather",
            "vocab_sunny",
            "phrase_how_is_the_weather",
            "grammar_it_is_weather"
        ),
        maxTurns = 8,
        maxAiSentenceWords = 15,
        correctionStyle = CorrectionStyle.GENTLE
    )
)
