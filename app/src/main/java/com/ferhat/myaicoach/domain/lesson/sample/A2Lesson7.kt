package com.ferhat.myaicoach.domain.lesson.sample

import com.ferhat.myaicoach.data.model.EnglishLevel
import com.ferhat.myaicoach.domain.lesson.*

val A2Lesson7 = Lesson(
    id = "a2_u2_l7",
    level = EnglishLevel.A2,
    unitId = "a2_u2",
    order = 7,
    title = "Asking for Directions",
    description = "Sokakta yol sormayı, sağa/sola dönmeyi ve mekan yerlerini tarif etmeyi öğren.",
    estimatedMinutes = 10,

    objectives = listOf(
        LessonObjective(id = "give_ask_directions", description = "Ask for and understand basic directions in a city.")
    ),

    vocabulary = listOf(
        VocabularyItem(
            id = "vocab_straight",
            word = "go straight",
            translation = "düz git",
            pronunciation = "/ɡəʊ streɪt/",
            exampleSentence = "Go straight for two blocks.",
            exampleTranslation = "İki blok boyunca düz gidin.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_opposite",
            word = "opposite",
            translation = "karşısında",
            pronunciation = "/ˈɒpəzɪt/",
            exampleSentence = "The bank is opposite the station.",
            exampleTranslation = "Banka istasyonun karşısındadır.",
            role = VocabularyRole.NEW
        )
    ),

    phrases = listOf(
        PhraseItem(
            id = "phrase_how_do_i_get_to",
            phrase = "How do I get to...?",
            translation = "...'ya nasıl gidebilirim?",
            exampleSentence = "Excuse me, how do I get to the museum?",
            exampleTranslation = "Affedersiniz, müzeye nasıl gidebilirim?",
            role = PhraseRole.NEW
        )
    ),

    grammarTargets = listOf(
        GrammarTarget(
            id = "imperatives_directions",
            title = "Emir Kipi ve Yer Edatları (Turn left, Next to)",
            explanation = "Yol tarif ederken 'Turn left', 'Turn right', 'Go straight' gibi fiille başlayan cümleler kullanılır.",
            pattern = "Turn left / Turn right + at the corner",
            examples = listOf(GrammarExample(sentence = "Turn left at the supermarket.", translation = "Süpermarketten sola dönün.")),
            role = GrammarRole.NEW
        )
    ),

    exercises = listOf(
        Exercise(
            id = "a2_u2_l7_ex1",
            type = ExerciseType.MULTIPLE_CHOICE,
            instruction = "Doğru yön kelimesini seç.",
            prompt = "_____ left at the next street.",
            options = listOf("Turn", "Walks", "Goes"),
            correctAnswer = "Turn",
            explanation = "Dönmek anlamında 'Turn left' kalıbı kullanılır.",
            targetIds = listOf("imperatives_directions"),
            order = 1
        )
    ),

    listeningContent = ListeningContent(
        id = "a2_u2_l7_listening",
        instruction = "Yol tarifini dinle.",
        transcript = listOf(
            DialogueLine(speaker = "Tourist", text = "Excuse me, where is the pharmacy?"),
            DialogueLine(speaker = "Local", text = "Go straight and turn right. It is next to the cafe.")
        ),
        questions = listOf(
            ListeningQuestion(
                id = "a2_u2_l7_q1",
                question = "Where is the pharmacy?",
                options = listOf("Next to the cafe", "Behind the station", "Opposite the park"),
                correctAnswer = "Next to the cafe",
                targetIds = listOf("phrase_how_do_i_get_to")
            )
        ),
        targetIds = listOf("phrase_how_do_i_get_to")
    ),

    speakingScenario = SpeakingScenario(
        id = "a2_u2_l7_speaking",
        title = "🎙️ Şehirde Yol Tarifi Sohbeti",
        description = "Vani'ye en yakın otobüs durağına nasıl gideceğini sor.",
        aiRole = "A helpful resident showing directions.",
        goal = "Ask for directions using 'How do I get to...'.",
        targetIds = listOf("vocab_straight", "phrase_how_do_i_get_to")
    )
)
