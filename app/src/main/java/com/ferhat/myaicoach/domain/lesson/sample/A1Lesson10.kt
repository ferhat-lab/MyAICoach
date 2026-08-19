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
 * A1Lesson10: "Asking Directions" (Yön Sorma ve Adres Tarifi)
 * Oxford 3000 / CEFR A1 Ünite 3 - 10. Ders Paketimiz.
 */
val A1Lesson10 = Lesson(
    id = "a1_u3_l10",
    level = EnglishLevel.A1,
    unitId = "a1_u3",
    order = 10,
    title = "Asking Directions",
    description = "Yön sormayı ve temel yol tariflerini anlamayı öğren.",
    estimatedMinutes = 10,

    objectives = listOf(
        LessonObjective(
            id = "ask_directions",
            description = "Ask for directions using 'Where is...?'."
        ),
        LessonObjective(
            id = "understand_basic_directions",
            description = "Understand basic direction phrases like 'turn left' and 'near'."
        )
    ),

    vocabulary = listOf(
        VocabularyItem(
            id = "vocab_left",
            word = "left",
            translation = "sol",
            pronunciation = "/left/",
            exampleSentence = "Turn left at the park.",
            exampleTranslation = "Parktan sola dön.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_right",
            word = "right",
            translation = "sağ",
            pronunciation = "/raɪt/",
            exampleSentence = "Turn right at the museum.",
            exampleTranslation = "Müzeden sağa dön.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_near",
            word = "near",
            translation = "yakınında",
            pronunciation = "/nɪə/",
            exampleSentence = "The hotel is near the station.",
            exampleTranslation = "Otel istasyonun yakınındadır.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_straight",
            word = "straight",
            translation = "düz",
            pronunciation = "/streɪt/",
            exampleSentence = "Go straight ahead.",
            exampleTranslation = "Düz ilerle.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_street",
            word = "street",
            translation = "sokak / caddesi",
            pronunciation = "/striːt/",
            exampleSentence = "Cross the street.",
            exampleTranslation = "Sokağı geç.",
            role = VocabularyRole.NEW
        )
    ),

    phrases = listOf(
        PhraseItem(
            id = "phrase_excuse_me",
            phrase = "Excuse me...",
            translation = "Afedersiniz...",
            exampleSentence = "Excuse me, where is the hospital?",
            exampleTranslation = "Afedersiniz, hastane nerede?",
            role = PhraseRole.NEW
        ),
        PhraseItem(
            id = "phrase_where_is",
            phrase = "Where is...",
            translation = "...nerede?",
            exampleSentence = "Where is the bus station?",
            exampleTranslation = "Otobüs durağı nerede?",
            role = PhraseRole.NEW
        )
    ),

    grammarTargets = listOf(
        GrammarTarget(
            id = "grammar_where_is",
            title = "Yer Sorma (Where is...?)",
            explanation = "Bir mekanın nerede olduğunu sormak için 'Where is + [Mekan]?' kalıbı kullanılır.",
            pattern = "Where is + the + [Mekan] ?",
            examples = listOf(
                GrammarExample(
                    sentence = "Where is the park?",
                    translation = "Park nerede?"
                ),
                GrammarExample(
                    sentence = "Where is the museum?",
                    translation = "Müze nerede?"
                )
            ),
            role = GrammarRole.NEW
        )
    ),

    exercises = listOf(
        Exercise(
            id = "a1_u3_l10_ex1",
            type = ExerciseType.MULTIPLE_CHOICE,
            instruction = "Doğru kelimeyi seç.",
            prompt = "____ me, where is the museum?",
            options = listOf(
                "Excuse",
                "Near",
                "Left"
            ),
            correctAnswer = "Excuse",
            explanation = "Nezaket ifadesi olarak \"Excuse me\" kullanılır.",
            targetIds = listOf("phrase_excuse_me", "phrase_where_is"),
            order = 1
        ),
        Exercise(
            id = "a1_u3_l10_ex2",
            type = ExerciseType.SENTENCE_BUILDER,
            instruction = "Kelimeleri doğru sıraya koy.",
            prompt = "Afedersiniz, hastane nerede?",
            options = listOf(
                "me",
                "Excuse",
                "where",
                "is",
                "the",
                "hospital"
            ),
            correctAnswer = "Excuse me where is the hospital",
            explanation = "Yer sorma cümlesi \"Excuse me where is...\" şeklinde kurulur.",
            targetIds = listOf("phrase_excuse_me", "phrase_where_is", "grammar_where_is"),
            order = 2
        ),
        Exercise(
            id = "a1_u3_l10_ex3",
            type = ExerciseType.FILL_IN_THE_BLANK,
            instruction = "Eksik kelimeyi tamamla.",
            prompt = "Turn ____ at the park.",
            options = listOf(
                "left",
                "straight",
                "street"
            ),
            correctAnswer = "left",
            explanation = "Dönüş yönü olarak \"left\" (sol) seçilir.",
            targetIds = listOf("vocab_left"),
            order = 3
        )
    ),

    listeningContent = ListeningContent(
        id = "a1_u3_l10_listening",
        instruction = "Konuşmayı dinle ve soruyu cevapla.",
        transcript = listOf(
            DialogueLine(
                speaker = "Alex",
                text = "Excuse me Vani, where is the hotel?"
            ),
            DialogueLine(
                speaker = "Vani",
                text = "Go straight and turn right. It is near the park!"
            ),
            DialogueLine(
                speaker = "Alex",
                text = "Thank you very much!"
            ),
            DialogueLine(
                speaker = "Vani",
                text = "Have a great day!"
            )
        ),
        questions = listOf(
            ListeningQuestion(
                id = "a1_u3_l10_listening_q1",
                question = "Where is the hotel?",
                options = listOf(
                    "Near the park",
                    "Inside the hospital",
                    "Far away"
                ),
                correctAnswer = "Near the park",
                targetIds = listOf("vocab_near")
            )
        ),
        targetIds = listOf("phrase_where_is", "vocab_right")
    ),

    speakingScenario = SpeakingScenario(
        id = "a1_u3_l10_speaking",
        title = "Vani'ye Yol Sor",
        description = "Vani'ye gitmek istediğin yerin nerede olduğunu sor ve yol tarifini dinle.",
        aiRole = "A helpful AI cat local guiding tourists.",
        goal = "Help learner ask 'Excuse me, where is the...?' and understand simple directions.",
        targetIds = listOf(
            "vocab_left",
            "vocab_right",
            "phrase_excuse_me",
            "grammar_where_is"
        ),
        maxTurns = 8,
        maxAiSentenceWords = 15,
        correctionStyle = CorrectionStyle.GENTLE
    )
)
