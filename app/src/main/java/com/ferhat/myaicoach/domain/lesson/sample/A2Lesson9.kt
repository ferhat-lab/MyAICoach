package com.ferhat.myaicoach.domain.lesson.sample

import com.ferhat.myaicoach.data.model.EnglishLevel
import com.ferhat.myaicoach.domain.lesson.*

val A2Lesson9 = Lesson(
    id = "a2_u3_l9",
    level = EnglishLevel.A2,
    unitId = "a2_u3",
    order = 9,
    title = "Job & Professions",
    description = "Meslekleri ve günlük iş sorumluluklarını anlatmayı öğren.",
    estimatedMinutes = 10,

    objectives = listOf(
        LessonObjective(id = "jobs_vocabulary", description = "Talk about professions and work duties.")
    ),

    vocabulary = listOf(
        VocabularyItem(
            id = "vocab_engineer",
            word = "engineer",
            translation = "mühendis",
            pronunciation = "/ˌendʒɪˈnɪə/",
            exampleSentence = "My brother is a software engineer.",
            exampleTranslation = "Erkek kardeşim yazılım mühendisidir.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_responsible_for",
            word = "responsible for",
            translation = "-den sorumlu",
            pronunciation = "/rɪˈspɒnsəbl fɔː/",
            exampleSentence = "I am responsible for customer support.",
            exampleTranslation = "Müşteri desteğinden sorumluyum.",
            role = VocabularyRole.NEW
        )
    ),

    phrases = listOf(
        PhraseItem(
            id = "phrase_what_do_you_do",
            phrase = "What do you do for a living?",
            translation = "Geçiminizi sağlamak için ne iş yapıyorsunuz?",
            exampleSentence = "What do you do for a living?",
            exampleTranslation = "Mesleğiniz nedir?",
            role = PhraseRole.NEW
        )
    ),

    grammarTargets = listOf(
        GrammarTarget(
            id = "work_responsibility_a2",
            title = "I work as a... / I am responsible for...",
            explanation = "Mesleğini belirtirken 'I work as a/an', sorumluluk söylerken 'I am responsible for + noun/-ing' kullanılır.",
            pattern = "I work as a + Job / I am responsible for + Verb-ing",
            examples = listOf(GrammarExample(sentence = "I am responsible for managing projects.", translation = "Projeleri yönetmekten sorumluyum.")),
            role = GrammarRole.NEW
        )
    ),

    exercises = listOf(
        Exercise(
            id = "a2_u3_l9_ex1",
            type = ExerciseType.MULTIPLE_CHOICE,
            instruction = "Doğru edatı seç.",
            prompt = "I am responsible _____ sales.",
            options = listOf("for", "to", "at"),
            correctAnswer = "for",
            explanation = "'Responsible' sıfatından sonra 'for' gelir.",
            targetIds = listOf("work_responsibility_a2"),
            order = 1
        )
    ),

    listeningContent = ListeningContent(
        id = "a2_u3_l9_listening",
        instruction = "Meslek konuşmasını dinle.",
        transcript = listOf(
            DialogueLine(speaker = "Vani", text = "What do you do for a living?"),
            DialogueLine(speaker = "Alex", text = "I am an engineer. I design products.")
        ),
        questions = listOf(
            ListeningQuestion(
                id = "a2_u3_l9_q1",
                question = "What is Alex's profession?",
                options = listOf("Engineer", "Doctor", "Teacher"),
                correctAnswer = "Engineer",
                targetIds = listOf("vocab_engineer")
            )
        ),
        targetIds = listOf("vocab_engineer")
    ),

    speakingScenario = SpeakingScenario(
        id = "a2_u3_l9_speaking",
        title = "🎙️ Meslek Tanıtım Sohbeti",
        description = "Ne iş yaptığını ve gün içinde nelerden sorumlu olduğunu Vani'ye anlat.",
        aiRole = "A colleague asking about your job.",
        goal = "Use 'I work as a...' and 'I am responsible for...'.",
        targetIds = listOf("vocab_engineer", "phrase_what_do_you_do")
    )
)
