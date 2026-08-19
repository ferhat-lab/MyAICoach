package com.ferhat.myaicoach.domain.lesson.sample

import com.ferhat.myaicoach.data.model.EnglishLevel
import com.ferhat.myaicoach.domain.lesson.*

val A2Lesson8 = Lesson(
    id = "a2_u2_l8",
    level = EnglishLevel.A2,
    unitId = "a2_u2",
    order = 8,
    title = "Planning a Tour",
    description = "Geleceğe yönelik planlar yapmayı (be going to) ve tur rehberi kalıplarını öğren.",
    estimatedMinutes = 10,

    objectives = listOf(
        LessonObjective(id = "future_intentions", description = "Express future travel plans using 'be going to'.")
    ),

    vocabulary = listOf(
        VocabularyItem(
            id = "vocab_sightseeing",
            word = "sightseeing",
            translation = "şehir/turistik yerleri gezme",
            pronunciation = "/ˈsaɪtsiːɪŋ/",
            exampleSentence = "We are going sightseeing tomorrow.",
            exampleTranslation = "Yarın turistik yerleri gezmeye gidiyoruz.",
            role = VocabularyRole.NEW
        )
    ),

    phrases = listOf(
        PhraseItem(
            id = "phrase_im_going_to_visit",
            phrase = "I'm going to visit...",
            translation = "...'yi ziyaret edeceğim",
            exampleSentence = "I'm going to visit the museum tomorrow.",
            exampleTranslation = "Yarın müzeyi ziyaret edeceğim.",
            role = PhraseRole.NEW
        )
    ),

    grammarTargets = listOf(
        GrammarTarget(
            id = "be_going_to_future",
            title = "Be Going To (Planlanan Gelecek Zaman)",
            explanation = "Önceden kararlaştırılmış gelecek zaman planlarını anlatırken 'am/is/are going to + verb' kullanılır.",
            pattern = "Subject + am/is/are + going to + Verb",
            examples = listOf(GrammarExample(sentence = "She is going to buy a souvenir.", translation = "Bir hediyelik eşya satın alacak.")),
            role = GrammarRole.NEW
        )
    ),

    exercises = listOf(
        Exercise(
            id = "a2_u2_l8_ex1",
            type = ExerciseType.MULTIPLE_CHOICE,
            instruction = "Doğru plan kelimesini seç.",
            prompt = "We are going _____ visit Rome next month.",
            options = listOf("to", "for", "at"),
            correctAnswer = "to",
            explanation = "'Be going to' kalıbından sonra fiilden önce 'to' gelir.",
            targetIds = listOf("be_going_to_future"),
            order = 1
        )
    ),

    listeningContent = ListeningContent(
        id = "a2_u2_l8_listening",
        instruction = "Plan sohbetini dinle.",
        transcript = listOf(
            DialogueLine(speaker = "Vani", text = "What are your plans for tomorrow?"),
            DialogueLine(speaker = "Alex", text = "I am going to visit the old castle.")
        ),
        questions = listOf(
            ListeningQuestion(
                id = "a2_u2_l8_q1",
                question = "What is Alex going to visit?",
                options = listOf("The old castle", "The beach", "The airport"),
                correctAnswer = "The old castle",
                targetIds = listOf("be_going_to_future")
            )
        ),
        targetIds = listOf("be_going_to_future")
    ),

    speakingScenario = SpeakingScenario(
        id = "a2_u2_l8_speaking",
        title = "🎙️ Şehir Turu Planlama Sohbeti",
        description = "Gelecek tatilinde nereleri gezeceğini Vani'ye anlat.",
        aiRole = "A travel guide coach discussing tour plans.",
        goal = "Use 'I am going to visit...' for travel intentions.",
        targetIds = listOf("vocab_sightseeing", "phrase_im_going_to_visit")
    )
)
