package com.ferhat.myaicoach.domain.lesson.sample

import com.ferhat.myaicoach.data.model.EnglishLevel
import com.ferhat.myaicoach.domain.lesson.*

val A2Lesson6 = Lesson(
    id = "a2_u2_l6",
    level = EnglishLevel.A2,
    unitId = "a2_u2",
    order = 6,
    title = "Hotel Reservation",
    description = "Otel rezervasyonu yapmayı ve oda imkanlarını sormayı öğren.",
    estimatedMinutes = 12,

    objectives = listOf(
        LessonObjective(id = "book_hotel", description = "Make a hotel reservation and ask about wifi/breakfast.")
    ),

    vocabulary = listOf(
        VocabularyItem(
            id = "vocab_reservation_a2",
            word = "reservation",
            translation = "rezervasyon",
            pronunciation = "/ˌrezəˈveɪʃn/",
            exampleSentence = "I have a reservation for two nights.",
            exampleTranslation = "İki gecelik rezervasyonum var.",
            role = VocabularyRole.NEW
        )
    ),

    phrases = listOf(
        PhraseItem(
            id = "phrase_breakfast_included",
            phrase = "Is breakfast included?",
            translation = "Kahvaltı dahil mi?",
            exampleSentence = "Is breakfast included in the room price?",
            exampleTranslation = "Oda fiyatına kahvaltı dahil mi?",
            role = PhraseRole.NEW
        )
    ),

    grammarTargets = listOf(
        GrammarTarget(
            id = "would_like_a2",
            title = "I would like + noun / to verb",
            explanation = "Resepsiyonda talepleri iletirken 'I would like' kullanılır.",
            pattern = "I would like + a double room",
            examples = listOf(GrammarExample(sentence = "I would like a room with a view.", translation = "Manzaralı bir oda almak istiyorum.")),
            role = GrammarRole.NEW
        )
    ),

    exercises = listOf(
        Exercise(
            id = "a2_u2_l6_ex1",
            type = ExerciseType.MULTIPLE_CHOICE,
            instruction = "Doğru seçeneği işaretle.",
            prompt = "Is breakfast _____ in the price?",
            options = listOf("included", "closed", "opened"),
            correctAnswer = "included",
            explanation = "'Included' dahil edilmek anlamına gelir.",
            targetIds = listOf("phrase_breakfast_included"),
            order = 1
        )
    ),

    listeningContent = ListeningContent(
        id = "a2_u2_l6_listening",
        instruction = "Dinle.",
        transcript = listOf(
            DialogueLine(speaker = "Receptionist", text = "Welcome! Do you have a reservation?"),
            DialogueLine(speaker = "Alex", text = "Yes, under the name Alex.")
        ),
        questions = listOf(
            ListeningQuestion(
                id = "a2_u2_l6_q1",
                question = "Does Alex have a reservation?",
                options = listOf("Yes", "No", "Maybe"),
                correctAnswer = "Yes",
                targetIds = listOf("vocab_reservation_a2")
            )
        ),
        targetIds = listOf("vocab_reservation_a2")
    ),

    speakingScenario = SpeakingScenario(
        id = "a2_u2_l6_speaking",
        title = "🎙️ Otel Resepsiyon Sohbeti",
        description = "Resepsiyona giriş yap ve kahvaltının dahil olup olmadığını sor.",
        aiRole = "A hotel receptionist.",
        goal = "Check in and ask about hotel facilities.",
        targetIds = listOf("vocab_reservation_a2", "phrase_breakfast_included")
    )
)
