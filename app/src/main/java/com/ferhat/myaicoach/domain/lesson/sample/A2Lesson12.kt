package com.ferhat.myaicoach.domain.lesson.sample

import com.ferhat.myaicoach.data.model.EnglishLevel
import com.ferhat.myaicoach.domain.lesson.*

val A2Lesson12 = Lesson(
    id = "a2_u3_l12",
    level = EnglishLevel.A2,
    unitId = "a2_u3",
    order = 12,
    title = "A2 Graduation Challenge",
    description = "🎓 CEFR A2 seviyesinin tüm geçmiş zaman, seyahat, iş ve alışveriş kalıplarını kapsayan mezuniyet dersi.",
    estimatedMinutes = 15,

    objectives = listOf(
        LessonObjective(id = "a2_graduation_mastery", description = "Demonstrate complete fluency in CEFR A2 English grammar and vocabulary.")
    ),

    vocabulary = listOf(
        VocabularyItem(
            id = "vocab_confident",
            word = "confident",
            translation = "özgüvenli",
            pronunciation = "/ˈkɒnfɪdənt/",
            exampleSentence = "I feel confident speaking English at A2 level.",
            exampleTranslation = "A2 seviyesinde İngilizce konuşurken özgüvenli hissediyorum.",
            role = VocabularyRole.NEW
        )
    ),

    phrases = listOf(
        PhraseItem(
            id = "phrase_i_have_achieved_a2",
            phrase = "I have achieved A2 level!",
            translation = "A2 seviyesini başardım!",
            exampleSentence = "I have achieved A2 level in English!",
            exampleTranslation = "İngilizcede A2 seviyesini başardım!",
            role = PhraseRole.NEW
        )
    ),

    grammarTargets = listOf(
        GrammarTarget(
            id = "a2_full_review",
            title = "A2 Genel Dil Bilgisi Tekrarı",
            explanation = "Simple Past Tense, Be going to, Modal verbs ve yer edatlarının harmanlanmış kullanımı.",
            pattern = "Past + Future + Polite Requests",
            examples = listOf(GrammarExample(sentence = "I traveled yesterday and I am going to study tomorrow.", translation = "Dün seyahat ettim ve yarın ders çalışacağım.")),
            role = GrammarRole.NEW
        )
    ),

    exercises = listOf(
        Exercise(
            id = "a2_u3_l12_ex1",
            type = ExerciseType.MULTIPLE_CHOICE,
            instruction = "Mezuniyet sorusunu yanıtla.",
            prompt = "Yesterday I _____ to the airport, and tomorrow I am going to _____ my friend.",
            options = listOf("went / visit", "go / visited", "gone / visiting"),
            correctAnswer = "went / visit",
            explanation = "Geçmiş zaman için 'went', 'going to' kalıbından sonra yın fiil 'visit' kullanılır.",
            targetIds = listOf("a2_full_review"),
            order = 1
        )
    ),

    listeningContent = ListeningContent(
        id = "a2_u3_l12_listening",
        instruction = "Vani'nin mezuniyet tebrik konuşmasını dinle.",
        transcript = listOf(
            DialogueLine(speaker = "Vani", text = "Congratulations! You have completed all CEFR A2 lessons!"),
            DialogueLine(speaker = "Alex", text = "Thank you Vani! I am ready for B1 level now!")
        ),
        questions = listOf(
            ListeningQuestion(
                id = "a2_u3_l12_q1",
                question = "Which level did Alex complete?",
                options = listOf("A2 Level", "A1 Level", "C2 Level"),
                correctAnswer = "A2 Level",
                targetIds = listOf("phrase_i_have_achieved_a2")
            )
        ),
        targetIds = listOf("phrase_i_have_achieved_a2")
    ),

    speakingScenario = SpeakingScenario(
        id = "a2_u3_l12_speaking",
        title = "🎓 Vani ile A2 Mezuniyet ve Mülakat Konuşması",
        description = "Vani ile A2 mezuniyet sohbetini yap. Geçmiş anılarını, seyahat planlarını ve iş tecrübelerini anlat.",
        aiRole = "Vani, your proud cat coach conducting the A2 Graduation Interview.",
        goal = "Demonstrate complete A2 level fluency across past events, travel, and career topics.",
        targetIds = listOf("vocab_confident", "phrase_i_have_achieved_a2")
    )
)
