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
 * A1Lesson4: "Around the House" (Evimiz ve Odalar)
 * Oxford 3000 / CEFR A1 Standartlarına Uyumlu 4. Ders Paketimiz (Ünite 1 Kapanış Dersi).
 */
val A1Lesson4 = Lesson(
    id = "a1_u1_l4",
    level = EnglishLevel.A1,
    unitId = "a1_u1",
    order = 4,
    title = "Around the House",
    description = "Evdeki nesneleri ve odaları tanımlamayı öğren.",
    estimatedMinutes = 10,

    objectives = listOf(
        LessonObjective(
            id = "describe_rooms",
            description = "Describe rooms and items inside a house."
        ),
        LessonObjective(
            id = "use_there_is",
            description = "Express existence using 'There is...'."
        )
    ),

    vocabulary = listOf(
        VocabularyItem(
            id = "vocab_house",
            word = "house",
            translation = "ev",
            pronunciation = "/haʊs/",
            exampleSentence = "I live in a beautiful house.",
            exampleTranslation = "Güzel bir evde yaşıyorum.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_room",
            word = "room",
            translation = "oda",
            pronunciation = "/ruːm/",
            exampleSentence = "My room is clean.",
            exampleTranslation = "Odam temizdir.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_bed",
            word = "bed",
            translation = "yatak",
            pronunciation = "/bed/",
            exampleSentence = "There is a big bed in my room.",
            exampleTranslation = "Odamda büyük bir yatak var.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_table",
            word = "table",
            translation = "masa",
            pronunciation = "/ˈteɪbl/",
            exampleSentence = "The coffee is on the table.",
            exampleTranslation = "Kahve masanın üzerinde.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_big",
            word = "big",
            translation = "büyük",
            pronunciation = "/bɪɡ/",
            exampleSentence = "Istanbul is a big city.",
            exampleTranslation = "İstanbul büyük bir şehirdir.",
            role = VocabularyRole.NEW
        )
    ),

    phrases = listOf(
        PhraseItem(
            id = "phrase_there_is",
            phrase = "There is...",
            translation = "...var",
            exampleSentence = "There is a table in my room.",
            exampleTranslation = "Odamda bir masa var.",
            role = PhraseRole.NEW
        ),
        PhraseItem(
            id = "phrase_in_my_room",
            phrase = "in my room",
            translation = "odamda",
            exampleSentence = "There is a bed in my room.",
            exampleTranslation = "Odamda bir yatak var.",
            role = PhraseRole.NEW
        )
    ),

    grammarTargets = listOf(
        GrammarTarget(
            id = "grammar_there_is",
            title = "Varlık Bildirme (There is...)",
            explanation = "Tekil bir nesnenin varlığını bildirmek için 'There is...' kalıbı kullanılır.",
            pattern = "There is + a/an + [Tekil İsim]",
            examples = listOf(
                GrammarExample(
                    sentence = "There is a bed.",
                    translation = "Bir yatak var."
                ),
                GrammarExample(
                    sentence = "There is a big table.",
                    translation = "Büyük bir masa var."
                )
            ),
            role = GrammarRole.NEW
        )
    ),

    exercises = listOf(
        Exercise(
            id = "a1_u1_l4_ex1",
            type = ExerciseType.MULTIPLE_CHOICE,
            instruction = "Doğru kelimeyi seç.",
            prompt = "There is a ____ in my room.",
            options = listOf(
                "table",
                "morning",
                "every"
            ),
            correctAnswer = "table",
            explanation = "Odada bulunan bir nesne olarak \"table\" (masa) seçilir.",
            targetIds = listOf("vocab_table", "phrase_there_is"),
            order = 1
        ),
        Exercise(
            id = "a1_u1_l4_ex2",
            type = ExerciseType.SENTENCE_BUILDER,
            instruction = "Kelimeleri doğru sıraya koy.",
            prompt = "Odamda bir yatak var.",
            options = listOf(
                "my",
                "in",
                "room",
                "There",
                "is",
                "a",
                "bed"
            ),
            correctAnswer = "There is a bed in my room.",
            explanation = "Varlık cümlesi \"There is...\" kalıbıyla başlar.",
            targetIds = listOf("phrase_there_is", "phrase_in_my_room", "grammar_there_is"),
            order = 2
        ),
        Exercise(
            id = "a1_u1_l4_ex3",
            type = ExerciseType.FILL_IN_THE_BLANK,
            instruction = "Eksik kelimeyi tamamla.",
            prompt = "I live in a ____ house.",
            correctAnswer = "big",
            explanation = "Büyük ev tanımı için \"big\" sıfatı kullanılır.",
            targetIds = listOf("vocab_big", "vocab_house"),
            order = 3
        )
    ),

    listeningContent = ListeningContent(
        id = "a1_u1_l4_listening",
        instruction = "Konuşmayı dinle ve soruyu cevapla.",
        transcript = listOf(
            DialogueLine(
                speaker = "Vani",
                text = "Do you live in a big house, Alex?"
            ),
            DialogueLine(
                speaker = "Alex",
                text = "Yes! There is a big room for me."
            ),
            DialogueLine(
                speaker = "Vani",
                text = "What is in your room?"
            ),
            DialogueLine(
                speaker = "Alex",
                text = "There is a bed and a table in my room."
            )
        ),
        questions = listOf(
            ListeningQuestion(
                id = "a1_u1_l4_listening_q1",
                question = "What is in Alex's room?",
                options = listOf(
                    "A bed and a table",
                    "Only a sofa",
                    "A car"
                ),
                correctAnswer = "A bed and a table",
                targetIds = listOf("vocab_bed", "vocab_table")
            )
        ),
        targetIds = listOf("phrase_there_is", "vocab_room")
    ),

    speakingScenario = SpeakingScenario(
        id = "a1_u1_l4_speaking",
        title = "Vani ile Evini Anlat",
        description = "Vani'ye odanı ve evindeki eşyaları anlat.",
        aiRole = "A friendly AI cat coach asking about the learner's room.",
        goal = "Help learner describe their room using 'There is a... in my room'.",
        targetIds = listOf(
            "vocab_house",
            "vocab_room",
            "phrase_there_is",
            "grammar_there_is"
        ),
        maxTurns = 8,
        maxAiSentenceWords = 15,
        correctionStyle = CorrectionStyle.GENTLE
    )
)
