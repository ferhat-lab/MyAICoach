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
 * A1Lesson12: "Travel & Hotel" (Seyahat ve Otelde Konaklama)
 * Oxford 3000 / CEFR A1 Ünite 3 - 12. Ders Paketimiz (A1 Seviyesi Son Ana Ders).
 */
val A1Lesson12 = Lesson(
    id = "a1_u3_l12",
    level = EnglishLevel.A1,
    unitId = "a1_u3",
    order = 12,
    title = "Travel & Hotel",
    description = "Seyahat ederken otelde konaklama ve bilet alma ifadelerini öğren.",
    estimatedMinutes = 10,

    objectives = listOf(
        LessonObjective(
            id = "hotel_checkin",
            description = "Express hotel reservation details."
        ),
        LessonObjective(
            id = "travel_essentials",
            description = "Identify travel items like ticket, passport, hotel."
        )
    ),

    vocabulary = listOf(
        VocabularyItem(
            id = "vocab_ticket",
            word = "ticket",
            translation = "bilet",
            pronunciation = "/ˈtɪkɪt/",
            exampleSentence = "I have a bus ticket.",
            exampleTranslation = "Otobüs biletim var.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_passport",
            word = "passport",
            translation = "pasaport",
            pronunciation = "/ˈpɑːspɔːt/",
            exampleSentence = "Here is my passport.",
            exampleTranslation = "İşte pasaportum.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_hotel",
            word = "hotel",
            translation = "otel",
            pronunciation = "/həʊˈtel/",
            exampleSentence = "The hotel is clean.",
            exampleTranslation = "Otel temizdir.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_stay",
            word = "stay",
            translation = "kalmak / konaklamak",
            pronunciation = "/steɪ/",
            exampleSentence = "I stay at a hotel.",
            exampleTranslation = "Bir otelde kalıyorum.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_visit",
            word = "visit",
            translation = "ziyaret etmek",
            pronunciation = "/ˈvɪzɪt/",
            exampleSentence = "I want to visit London.",
            exampleTranslation = "Londra'yı ziyaret etmek istiyorum.",
            role = VocabularyRole.NEW
        )
    ),

    phrases = listOf(
        PhraseItem(
            id = "phrase_i_have_a_reservation",
            phrase = "I have a reservation.",
            translation = "Rezervasyonum var.",
            exampleSentence = "Hello, I have a reservation at this hotel.",
            exampleTranslation = "Merhaba, bu otelde rezervasyonum var.",
            role = PhraseRole.NEW
        ),
        PhraseItem(
            id = "phrase_here_is_my_passport",
            phrase = "Here is my passport.",
            translation = "İşte pasaportum.",
            exampleSentence = "Here is my passport and ticket.",
            exampleTranslation = "İşte pasaportum ve biletim.",
            role = PhraseRole.NEW
        )
    ),

    grammarTargets = listOf(
        GrammarTarget(
            id = "grammar_have_has",
            title = "Sahiplik Bildirme (I have...)",
            explanation = "Bir şeye sahip olduğunu söylemek için 'I have + [Nesne]' yapısı kullanılır.",
            pattern = "I have + [Nesne]",
            examples = listOf(
                GrammarExample(
                    sentence = "I have a ticket.",
                    translation = "Biletim var."
                ),
                GrammarExample(
                    sentence = "I have a reservation.",
                    translation = "Rezervasyonum var."
                )
            ),
            role = GrammarRole.NEW
        )
    ),

    exercises = listOf(
        Exercise(
            id = "a1_u3_l12_ex1",
            type = ExerciseType.MULTIPLE_CHOICE,
            instruction = "Doğru kelimeyi seç.",
            prompt = "I have a bus ____.",
            options = listOf(
                "ticket",
                "weather",
                "sunny"
            ),
            correctAnswer = "ticket",
            explanation = "Ulaşım belgesi olarak \"ticket\" (bilet) seçilir.",
            targetIds = listOf("vocab_ticket", "grammar_have_has"),
            order = 1
        ),
        Exercise(
            id = "a1_u3_l12_ex2",
            type = ExerciseType.SENTENCE_BUILDER,
            instruction = "Kelimeleri doğru sıraya koy.",
            prompt = "Otelde rezervasyonum var.",
            options = listOf(
                "reservation",
                "have",
                "I",
                "a"
            ),
            correctAnswer = "I have a reservation",
            explanation = "Rezervasyon beyanı \"I have a reservation\" şeklinde yapılır.",
            targetIds = listOf("phrase_i_have_a_reservation", "grammar_have_has"),
            order = 2
        ),
        Exercise(
            id = "a1_u3_l12_ex3",
            type = ExerciseType.FILL_IN_THE_BLANK,
            instruction = "Eksik kelimeyi tamamla.",
            prompt = "Here is my ____.",
            options = listOf(
                "passport",
                "hour",
                "right"
            ),
            correctAnswer = "passport",
            explanation = "Pasaport beyanı için \"passport\" kelimesi kullanılır.",
            targetIds = listOf("vocab_passport", "phrase_here_is_my_passport"),
            order = 3
        )
    ),

    listeningContent = ListeningContent(
        id = "a1_u3_l12_listening",
        instruction = "Konuşmayı dinle ve soruyu cevapla.",
        transcript = listOf(
            DialogueLine(
                speaker = "Alex",
                text = "Hello Vani! I have a reservation."
            ),
            DialogueLine(
                speaker = "Vani",
                text = "Welcome to our hotel! May I see your passport?"
            ),
            DialogueLine(
                speaker = "Alex",
                text = "Here is my passport and ticket."
            ),
            DialogueLine(
                speaker = "Vani",
                text = "Thank you! Enjoy your stay."
            )
        ),
        questions = listOf(
            ListeningQuestion(
                id = "a1_u3_l12_listening_q1",
                question = "What does Alex show to Vani?",
                options = listOf(
                    "Passport and ticket",
                    "A picture",
                    "A cat toy"
                ),
                correctAnswer = "Passport and ticket",
                targetIds = listOf("vocab_passport", "vocab_ticket")
            )
        ),
        targetIds = listOf("phrase_i_have_a_reservation", "vocab_stay")
    ),

    speakingScenario = SpeakingScenario(
        id = "a1_u3_l12_speaking",
        title = "Vani'nin Otelinde Check-in Yap",
        description = "Vani'nin resepsiyonist olduğu otelde giriş yap, pasaportunu göster ve rezervasyonunu doğrula.",
        aiRole = "A polite AI cat hotel receptionist.",
        goal = "Help learner complete hotel check-in using 'I have a reservation' and 'Here is my passport'.",
        targetIds = listOf(
            "vocab_passport",
            "vocab_ticket",
            "phrase_i_have_a_reservation",
            "grammar_have_has"
        ),
        maxTurns = 8,
        maxAiSentenceWords = 15,
        correctionStyle = CorrectionStyle.GENTLE
    )
)
