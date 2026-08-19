package com.ferhat.myaicoach.domain.lesson.sample

import com.ferhat.myaicoach.data.model.EnglishLevel
import com.ferhat.myaicoach.domain.lesson.*

val A2Lesson1 = Lesson(
    id = "a2_u1_l1",
    level = EnglishLevel.A2,
    unitId = "a2_u1",
    order = 1,
    title = "Yesterday's Trip",
    description = "Geçmiş zaman (Simple Past) ile dün yaptıklarını anlatmayı öğren.",
    estimatedMinutes = 12,

    objectives = listOf(
        LessonObjective(id = "talk_past_events", description = "Talk about past events using Simple Past Tense."),
        LessonObjective(id = "use_was_were", description = "Use 'was' and 'were' correctly.")
    ),

    vocabulary = listOf(
        VocabularyItem(
            id = "vocab_yesterday",
            word = "yesterday",
            translation = "dün",
            pronunciation = "/ˈjestədeɪ/",
            exampleSentence = "I visited the museum yesterday.",
            exampleTranslation = "Dün müzeyi ziyaret ettim.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_traveled",
            word = "traveled",
            translation = "seyahat etti",
            pronunciation = "/ˈtrævld/",
            exampleSentence = "We traveled to London last summer.",
            exampleTranslation = "Geçen yaz Londra'ya seyahat ettik.",
            role = VocabularyRole.NEW
        )
    ),

    phrases = listOf(
        PhraseItem(
            id = "phrase_how_was_trip",
            phrase = "How was your trip?",
            translation = "Yolculuğun nasıldı?",
            exampleSentence = "How was your trip to London?",
            exampleTranslation = "Londra yolculuğun nasıldı?",
            role = PhraseRole.NEW
        )
    ),

    grammarTargets = listOf(
        GrammarTarget(
            id = "simple_past_regular",
            title = "Simple Past Tense (Geçmiş Zaman)",
            explanation = "Geçmişte tamamlanmış eylemleri anlatmak için fiillerin sonuna '-ed' eklenir.",
            pattern = "Subject + Verb-ed",
            examples = listOf(GrammarExample(sentence = "The trip was amazing.", translation = "Yolculuk harikaydı.")),
            role = GrammarRole.NEW
        )
    ),

    exercises = listOf(
        Exercise(
            id = "a2_u1_l1_ex1",
            type = ExerciseType.MULTIPLE_CHOICE,
            instruction = "Doğru seçeneği belirle.",
            prompt = "I _____ at home yesterday.",
            options = listOf("was", "were", "am"),
            correctAnswer = "was",
            explanation = "'I' öznesi için geçmiş zamanda 'was' kullanılır.",
            targetIds = listOf("simple_past_regular"),
            order = 1
        )
    ),

    listeningContent = ListeningContent(
        id = "a2_u1_l1_listening",
        instruction = "Konuşmayı dinle.",
        transcript = listOf(
            DialogueLine(speaker = "Vani", text = "Yesterday was a rainy day!"),
            DialogueLine(speaker = "Alex", text = "So I stayed at home and listened to music.")
        ),
        questions = listOf(
            ListeningQuestion(
                id = "a2_u1_l1_q1",
                question = "What did Alex do yesterday?",
                options = listOf("Stayed at home", "Went to the beach", "Traveled abroad"),
                correctAnswer = "Stayed at home",
                targetIds = listOf("simple_past_regular")
            )
        ),
        targetIds = listOf("simple_past_regular")
    ),

    speakingScenario = SpeakingScenario(
        id = "a2_u1_l1_speaking",
        title = "🎙️ Geçmiş Gün Anlatımı",
        description = "Geçen gün ne yaptığını Vani'ye anlat.",
        aiRole = "A friendly cat coach asking about your day.",
        goal = "Practice using Simple Past Tense to describe past events.",
        targetIds = listOf("simple_past_regular", "vocab_yesterday")
    )
)
