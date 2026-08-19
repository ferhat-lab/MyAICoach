package com.ferhat.myaicoach.domain.lesson.sample

import com.ferhat.myaicoach.data.model.EnglishLevel
import com.ferhat.myaicoach.domain.lesson.*

val A2Lesson11 = Lesson(
    id = "a2_u3_l11",
    level = EnglishLevel.A2,
    unitId = "a2_u3",
    order = 11,
    title = "Shopping for Clothes",
    description = "Kıyafet alışverişinde beden sormayı, fiyat sormayı ve deneme kabini kalıplarını öğren.",
    estimatedMinutes = 10,

    objectives = listOf(
        LessonObjective(id = "shopping_clothes", description = "Ask for sizes, prices, and try on clothes in English.")
    ),

    vocabulary = listOf(
        VocabularyItem(
            id = "vocab_size",
            word = "size",
            translation = "beden, numara",
            pronunciation = "/saɪz/",
            exampleSentence = "Do you have this jacket in medium size?",
            exampleTranslation = "Bu ceketin orta bedeni var mı?",
            role = VocabularyRole.NEW
        )
    ),

    phrases = listOf(
        PhraseItem(
            id = "phrase_can_i_try_this_on",
            phrase = "Can I try this on?",
            translation = "Bunu deneyebilir miyim?",
            exampleSentence = "Can I try this shirt on?",
            exampleTranslation = "Bu gömleği üzerimde deneyebilir miyim?",
            role = PhraseRole.NEW
        )
    ),

    grammarTargets = listOf(
        GrammarTarget(
            id = "demonstratives_a2",
            title = "This / That / These / Those",
            explanation = "Yakındaki tekil için 'this', çoğul için 'these'; uzaktaki tekil için 'that', çoğul için 'those' kullanılır.",
            pattern = "This jacket (near) vs. Those shoes (far)",
            examples = listOf(GrammarExample(sentence = "How much are these shoes?", translation = "Bu ayakkabılar ne kadar?")),
            role = GrammarRole.NEW
        )
    ),

    exercises = listOf(
        Exercise(
            id = "a2_u3_l11_ex1",
            type = ExerciseType.MULTIPLE_CHOICE,
            instruction = "Doğru gösterim sıfatını seç.",
            prompt = "How much is _____ coat near me?",
            options = listOf("this", "those", "these"),
            correctAnswer = "this",
            explanation = "Yakındaki tekil eşya için 'this' kullanılır.",
            targetIds = listOf("demonstratives_a2"),
            order = 1
        )
    ),

    listeningContent = ListeningContent(
        id = "a2_u3_l11_listening",
        instruction = "Alışveriş konuşmasını dinle.",
        transcript = listOf(
            DialogueLine(speaker = "Shop Assistant", text = "Can I help you?"),
            DialogueLine(speaker = "Customer", text = "Yes, can I try this sweater on in medium size?")
        ),
        questions = listOf(
            ListeningQuestion(
                id = "a2_u3_l11_q1",
                question = "What size does the customer want?",
                options = listOf("Medium", "Small", "Large"),
                correctAnswer = "Medium",
                targetIds = listOf("phrase_can_i_try_this_on")
            )
        ),
        targetIds = listOf("phrase_can_i_try_this_on")
    ),

    speakingScenario = SpeakingScenario(
        id = "a2_u3_l11_speaking",
        title = "🎙️ Mağaza Alışveriş Sohbeti",
        description = "Mağaza görevlisine kıyafetin bedenini sor ve deneme kabinini öğren.",
        aiRole = "A helpful clothing store assistant.",
        goal = "Ask 'Can I try this on?' and request a different size.",
        targetIds = listOf("vocab_size", "phrase_can_i_try_this_on")
    )
)
