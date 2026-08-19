package com.ferhat.myaicoach.domain.lesson.sample

import com.ferhat.myaicoach.data.model.EnglishLevel
import com.ferhat.myaicoach.domain.lesson.*

val A2Lesson2 = Lesson(
    id = "a2_u1_l2",
    level = EnglishLevel.A2,
    unitId = "a2_u1",
    order = 2,
    title = "Childhood Memories",
    description = "Düzensiz fiiller (Irregular Verbs: went, saw, ate) ile çocukluk anılarını anlatmayı öğren.",
    estimatedMinutes = 12,

    objectives = listOf(
        LessonObjective(id = "irregular_verbs_a2", description = "Use common irregular past verbs in sentences.")
    ),

    vocabulary = listOf(
        VocabularyItem(
            id = "vocab_went",
            word = "went",
            translation = "gitti (go fiilinin geçmiş hali)",
            pronunciation = "/went/",
            exampleSentence = "I went to Paris last year.",
            exampleTranslation = "Geçen yıl Paris'e gittim.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_saw",
            word = "saw",
            translation = "gördü (see fiilinin geçmiş hali)",
            pronunciation = "/sɔː/",
            exampleSentence = "We saw a great movie.",
            exampleTranslation = "Harika bir film izledik.",
            role = VocabularyRole.NEW
        )
    ),

    phrases = listOf(
        PhraseItem(
            id = "phrase_when_i_was_young",
            phrase = "When I was young...",
            translation = "Ben gençken...",
            exampleSentence = "When I was young, I lived in Spain.",
            exampleTranslation = "Gençken İspanya'da yaşıyordum.",
            role = PhraseRole.NEW
        )
    ),

    grammarTargets = listOf(
        GrammarTarget(
            id = "irregular_past_tense",
            title = "Düzensiz Fiiller (Irregular Verbs)",
            explanation = "Bazı fiiller geçmiş zamanda '-ed' almaz, tamamen değişir: go → went, see → saw, eat → ate.",
            pattern = "go → went, see → saw, eat → ate",
            examples = listOf(GrammarExample(sentence = "I went to school by bus.", translation = "Okula otobüsle gittim.")),
            role = GrammarRole.NEW
        )
    ),

    exercises = listOf(
        Exercise(
            id = "a2_u1_l2_ex1",
            type = ExerciseType.MULTIPLE_CHOICE,
            instruction = "Doğru geçmiş zaman fiilini seç.",
            prompt = "Yesterday I _____ my best friend.",
            options = listOf("saw", "see", "seed"),
            correctAnswer = "saw",
            explanation = "'See' fiilinin geçmiş zaman hali 'saw'dur.",
            targetIds = listOf("irregular_past_tense"),
            order = 1
        )
    ),

    listeningContent = ListeningContent(
        id = "a2_u1_l2_listening",
        instruction = "Dinle ve soruyu yanıtla.",
        transcript = listOf(
            DialogueLine(speaker = "Vani", text = "Where did you go last night?"),
            DialogueLine(speaker = "Alex", text = "I went to a restaurant and ate pizza.")
        ),
        questions = listOf(
            ListeningQuestion(
                id = "a2_u1_l2_q1",
                question = "What did Alex eat?",
                options = listOf("Pizza", "Burger", "Pasta"),
                correctAnswer = "Pizza",
                targetIds = listOf("irregular_past_tense")
            )
        ),
        targetIds = listOf("irregular_past_tense")
    ),

    speakingScenario = SpeakingScenario(
        id = "a2_u1_l2_speaking",
        title = "🎙️ Çocukluk Anıları Sohbeti",
        description = "Çocukluğunda nerede yaşadığını ve ne yaptığını anlat.",
        aiRole = "A curious cat coach asking about your past memories.",
        goal = "Use irregular verbs like went, saw, lived.",
        targetIds = listOf("vocab_went", "phrase_when_i_was_young")
    )
)
