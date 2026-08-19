package com.ferhat.myaicoach.domain.lesson.sample

import com.ferhat.myaicoach.data.model.EnglishLevel
import com.ferhat.myaicoach.domain.lesson.*

val A2Lesson10 = Lesson(
    id = "a2_u3_l10",
    level = EnglishLevel.A2,
    unitId = "a2_u3",
    order = 10,
    title = "Job Interview Basics",
    description = "İş mülakatlarında kendini, yeteneklerini ve deneyimini anlatmayı öğren.",
    estimatedMinutes = 12,

    objectives = listOf(
        LessonObjective(id = "interview_skills", description = "Answer common job interview questions in English.")
    ),

    vocabulary = listOf(
        VocabularyItem(
            id = "vocab_experience",
            word = "experience",
            translation = "deneyim, tecrübe",
            pronunciation = "/ɪkˈspɪəriəns/",
            exampleSentence = "I have 3 years of experience in marketing.",
            exampleTranslation = "Pazarlama alanında 3 yıllık deneyimim var.",
            role = VocabularyRole.NEW
        )
    ),

    phrases = listOf(
        PhraseItem(
            id = "phrase_tell_me_about_yourself",
            phrase = "Tell me about yourself.",
            translation = "Bana biraz kendinizden bahsedin.",
            exampleSentence = "Tell me about yourself and your career background.",
            exampleTranslation = "Bana biraz kendinizden ve kariyer geçmişinizden bahsedin.",
            role = PhraseRole.NEW
        )
    ),

    grammarTargets = listOf(
        GrammarTarget(
            id = "present_perfect_experience_a2",
            title = "Present Perfect for Experience (I have worked...)",
            explanation = "Kariyer tecrübesinden bahsederken 'I have worked for 3 years' kalıbı kullanılır.",
            pattern = "I have + Past Participle (worked/lived)",
            examples = listOf(GrammarExample(sentence = "I have worked in sales since 2021.", translation = "2021'den beri satışta çalıştım.")),
            role = GrammarRole.NEW
        )
    ),

    exercises = listOf(
        Exercise(
            id = "a2_u3_l10_ex1",
            type = ExerciseType.MULTIPLE_CHOICE,
            instruction = "Doğru kelimeyi seç.",
            prompt = "I have 5 years of _____ in design.",
            options = listOf("experience", "experiencing", "experienced"),
            correctAnswer = "experience",
            explanation = "İsim hali olan 'experience' kullanılır.",
            targetIds = listOf("vocab_experience"),
            order = 1
        )
    ),

    listeningContent = ListeningContent(
        id = "a2_u3_l10_listening",
        instruction = "Mülakatı dinle.",
        transcript = listOf(
            DialogueLine(speaker = "Interviewer", text = "Tell me about your experience."),
            DialogueLine(speaker = "Alex", text = "I have worked as a manager for two years.")
        ),
        questions = listOf(
            ListeningQuestion(
                id = "a2_u3_l10_q1",
                question = "How long has Alex worked as a manager?",
                options = listOf("Two years", "Five years", "One month"),
                correctAnswer = "Two years",
                targetIds = listOf("present_perfect_experience_a2")
            )
        ),
        targetIds = listOf("present_perfect_experience_a2")
    ),

    speakingScenario = SpeakingScenario(
        id = "a2_u3_l10_speaking",
        title = "🎙️ İş Mülakatı Pratiği",
        description = "Mülakatçına deneyimlerinden ve güçlü yönlerinden bahset.",
        aiRole = "A professional hiring manager interviewing you.",
        goal = "Answer 'Tell me about yourself' and mention experience.",
        targetIds = listOf("vocab_experience", "phrase_tell_me_about_yourself")
    )
)
