package com.ferhat.myaicoach.domain.lesson.sample

import com.ferhat.myaicoach.data.model.EnglishLevel
import com.ferhat.myaicoach.domain.lesson.*

val A2Lesson4 = Lesson(
    id = "a2_u1_l4",
    level = EnglishLevel.A2,
    unitId = "a2_u1",
    order = 4,
    title = "Routine vs. Past",
    description = "Şimdiki/geniş zaman alışkanlıkların ile geçmişte yaptıkların arasındaki farkı kavra.",
    estimatedMinutes = 10,

    objectives = listOf(
        LessonObjective(id = "compare_present_past", description = "Compare daily routines with past activities.")
    ),

    vocabulary = listOf(
        VocabularyItem(
            id = "vocab_usually",
            word = "usually",
            translation = "genellikle",
            pronunciation = "/ˈjuːʒuəli/",
            exampleSentence = "I usually drink coffee, but yesterday I drank tea.",
            exampleTranslation = "Genellikle kahve içerim, ama dün çay içtim.",
            role = VocabularyRole.NEW
        )
    ),

    phrases = listOf(
        PhraseItem(
            id = "phrase_used_to",
            phrase = "I usually..., but yesterday...",
            translation = "Genellikle... yaparım ama dün...",
            exampleSentence = "I usually walk, but yesterday I took a taxi.",
            exampleTranslation = "Genellikle yürürüm ama dün taksiye bindim.",
            role = PhraseRole.NEW
        )
    ),

    grammarTargets = listOf(
        GrammarTarget(
            id = "present_vs_past",
            title = "Geniş Zaman vs. Geçmiş Zaman",
            explanation = "Alışkanlıklar için Present Simple (drink/walk), dün olanlar için Past Simple (drank/walked) kullanılır.",
            pattern = "Usually + Verb (Present) vs. Yesterday + Verb-ed (Past)",
            examples = listOf(GrammarExample(sentence = "I usually eat fruit, but yesterday I ate cake.", translation = "Genellikle meyve yerim ama dün pasta yedim.")),
            role = GrammarRole.NEW
        )
    ),

    exercises = listOf(
        Exercise(
            id = "a2_u1_l4_ex1",
            type = ExerciseType.MULTIPLE_CHOICE,
            instruction = "Zaman yapısını doğru seç.",
            prompt = "I usually walk, but yesterday I _____ the bus.",
            options = listOf("took", "take", "taking"),
            correctAnswer = "took",
            explanation = "Dün gerçekleşen eylem olduğu için geçmiş zaman hali 'took' seçilir.",
            targetIds = listOf("present_vs_past"),
            order = 1
        )
    ),

    listeningContent = ListeningContent(
        id = "a2_u1_l4_listening",
        instruction = "Dinle.",
        transcript = listOf(
            DialogueLine(speaker = "Vani", text = "Do you always wake up early?"),
            DialogueLine(speaker = "Alex", text = "Usually yes, but yesterday I slept until 10 AM.")
        ),
        questions = listOf(
            ListeningQuestion(
                id = "a2_u1_l4_q1",
                question = "What time did Alex wake up yesterday?",
                options = listOf("10 AM", "6 AM", "8 AM"),
                correctAnswer = "10 AM",
                targetIds = listOf("present_vs_past")
            )
        ),
        targetIds = listOf("present_vs_past")
    ),

    speakingScenario = SpeakingScenario(
        id = "a2_u1_l4_speaking",
        title = "🎙️ Alışkanlık vs. Geçmiş Gün Sohbeti",
        description = "Genellikle ne yaptığını ve dün fark olarak ne yaptığını Vani'ye anlat.",
        aiRole = "A cat coach asking about your routine shifts.",
        goal = "Compare present routine with past action.",
        targetIds = listOf("vocab_usually", "phrase_used_to")
    )
)
