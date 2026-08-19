package com.ferhat.myaicoach.domain.lesson.sample

import com.ferhat.myaicoach.data.model.EnglishLevel
import com.ferhat.myaicoach.domain.lesson.*

val A2Lesson5 = Lesson(
    id = "a2_u2_l5",
    level = EnglishLevel.A2,
    unitId = "a2_u2",
    order = 5,
    title = "At the Airport",
    description = "Havaalanında biniş kartı, bagaj ve danışma kalıplarını öğren.",
    estimatedMinutes = 12,

    objectives = listOf(
        LessonObjective(id = "airport_vocab", description = "Navigate airport check-in and boarding gates in English.")
    ),

    vocabulary = listOf(
        VocabularyItem(
            id = "vocab_boarding_pass",
            word = "boarding pass",
            translation = "biniş kartı",
            pronunciation = "/ˈbɔːdɪŋ pɑːs/",
            exampleSentence = "Here is my boarding pass.",
            exampleTranslation = "İşte biniş kartım.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_luggage",
            word = "luggage",
            translation = "bagaj",
            pronunciation = "/ˈlʌɡɪdʒ/",
            exampleSentence = "Do you have any luggage to check in?",
            exampleTranslation = "Teslim edilecek bagajınız var mı?",
            role = VocabularyRole.NEW
        )
    ),

    phrases = listOf(
        PhraseItem(
            id = "phrase_which_gate",
            phrase = "Which gate is my flight?",
            translation = "Uçağım hangi kapıda?",
            exampleSentence = "Excuse me, which gate is my flight to London?",
            exampleTranslation = "Affedersiniz, Londra uçuşum hangi kapıda?",
            role = PhraseRole.NEW
        )
    ),

    grammarTargets = listOf(
        GrammarTarget(
            id = "modal_can_may_a2",
            title = "Can / May I see...",
            explanation = "Havaalanında veya resmi alanlarda bir şey talep ederken 'Can I see...' veya 'May I see...' kullanılır.",
            pattern = "May I see + Your passport / boarding pass",
            examples = listOf(GrammarExample(sentence = "May I see your passport?", translation = "Pasaportunuzu görebilir miyim?")),
            role = GrammarRole.NEW
        )
    ),

    exercises = listOf(
        Exercise(
            id = "a2_u2_l5_ex1",
            type = ExerciseType.MULTIPLE_CHOICE,
            instruction = "Doğru kelimeyi seç.",
            prompt = "Please show your _____ pass at gate 5.",
            options = listOf("boarding", "hotel", "bus"),
            correctAnswer = "boarding",
            explanation = "Uçağa biniş kartı 'boarding pass' olarak adlandırılır.",
            targetIds = listOf("vocab_boarding_pass"),
            order = 1
        )
    ),

    listeningContent = ListeningContent(
        id = "a2_u2_l5_listening",
        instruction = "Havaalanı anonsunu dinle.",
        transcript = listOf(
            DialogueLine(speaker = "Anons", text = "Flight TK102 to London is now boarding at Gate 12."),
            DialogueLine(speaker = "Passenger", text = "Great, let's head to Gate 12!")
        ),
        questions = listOf(
            ListeningQuestion(
                id = "a2_u2_l5_q1",
                question = "Which gate is boarding?",
                options = listOf("Gate 12", "Gate 5", "Gate 10"),
                correctAnswer = "Gate 12",
                targetIds = listOf("phrase_which_gate")
            )
        ),
        targetIds = listOf("phrase_which_gate")
    ),

    speakingScenario = SpeakingScenario(
        id = "a2_u2_l5_speaking",
        title = "🎙️ Havaalanı Danışma Sohbeti",
        description = "Havaalanı görevlisine kapı numaranı ve biniş kartını sor.",
        aiRole = "An airport officer at the check-in counter.",
        goal = "Ask about boarding gate and show luggage/passport.",
        targetIds = listOf("vocab_boarding_pass", "phrase_which_gate")
    )
)
