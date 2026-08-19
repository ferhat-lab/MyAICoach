package com.ferhat.myaicoach.domain.lesson.sample

import com.ferhat.myaicoach.data.model.EnglishLevel
import com.ferhat.myaicoach.domain.lesson.*

val B1Lesson1 = Lesson(
    id = "b1_u1_l1",
    level = EnglishLevel.B1,
    unitId = "b1_u1",
    order = 1,
    title = "Expressing Business Opinions",
    description = "İş hayatında fikirlerini ifade etmeyi ve profesyonel tartışmayı öğren.",
    estimatedMinutes = 15,

    objectives = listOf(
        LessonObjective(
            id = "express_opinion_b1",
            description = "Express professional opinions clearly in business meetings."
        ),
        LessonObjective(
            id = "agree_disagree_politely",
            description = "Agree or disagree politely with colleagues."
        )
    ),

    vocabulary = listOf(
        VocabularyItem(
            id = "vocab_opinion",
            word = "opinion",
            translation = "görüş, fikir",
            pronunciation = "/əˈpɪnjən/",
            exampleSentence = "In my opinion, this project is very promising.",
            exampleTranslation = "Benim görüşüme göre bu proje çok vaat edici.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_strategy",
            word = "strategy",
            translation = "strateji",
            pronunciation = "/ˈstrætədʒi/",
            exampleSentence = "We need a new marketing strategy.",
            exampleTranslation = "Yeni bir pazarlama stratejisine ihtiyacımız var.",
            role = VocabularyRole.NEW
        )
    ),

    phrases = listOf(
        PhraseItem(
            id = "phrase_in_my_opinion",
            phrase = "In my opinion...",
            translation = "Benim fikrime göre...",
            exampleSentence = "In my opinion, we should focus on customer feedback.",
            exampleTranslation = "Benim fikrime göre müşteri geri bildirimlerine odaklanmalıyız.",
            role = PhraseRole.NEW
        ),
        PhraseItem(
            id = "phrase_strongly_agree",
            phrase = "I strongly agree with you.",
            translation = "Sana tamamen katılıyorum.",
            exampleSentence = "I strongly agree with you on this strategy.",
            exampleTranslation = "Bu strateji konusunda sana tamamen katılıyorum.",
            role = PhraseRole.NEW
        )
    ),

    grammarTargets = listOf(
        GrammarTarget(
            id = "linking_words_b1",
            title = "Linking Words (However, In my view)",
            explanation = "Fikirleri mantıksal olarak bağlamak ve profesyonel ton oluşturmak için kullanılır.",
            pattern = "Statement + however / in my view + Opinion",
            examples = listOf(
                GrammarExample(
                    sentence = "I agree with the plan; however, we need more time.",
                    translation = "Plana katılıyorum; ancak daha fazla zamana ihtiyacımız var."
                )
            ),
            role = GrammarRole.NEW
        )
    ),

    exercises = listOf(
        Exercise(
            id = "b1_u1_l1_ex1",
            type = ExerciseType.MULTIPLE_CHOICE,
            instruction = "Doğru edatı seç.",
            prompt = "_____ my opinion, we should focus on customer feedback.",
            options = listOf("In", "On", "At"),
            correctAnswer = "In",
            explanation = "Fikir ifade ederken 'In my opinion' kalıbı kullanılır.",
            targetIds = listOf("phrase_in_my_opinion"),
            order = 1
        )
    ),

    listeningContent = ListeningContent(
        id = "b1_u1_l1_listening",
        instruction = "Toplantı konuşmasını dinle.",
        transcript = listOf(
            DialogueLine(speaker = "Vani", text = "What do you think about launching the app next month?"),
            DialogueLine(speaker = "Alex", text = "In my opinion, it is a great idea!")
        ),
        questions = listOf(
            ListeningQuestion(
                id = "b1_u1_l1_q1",
                question = "What is Alex's opinion?",
                options = listOf("It is a great idea", "It is too early", "Cancel the launch"),
                correctAnswer = "It is a great idea",
                targetIds = listOf("phrase_in_my_opinion")
            )
        ),
        targetIds = listOf("phrase_in_my_opinion")
    ),

    speakingScenario = SpeakingScenario(
        id = "b1_u1_l1_speaking",
        title = "🎙️ Vani ile İş Toplantısı ve Proje Tartışması",
        description = "Vani proje yöneticin. Yeni ürün stratejisini tartışıyorsunuz. Fikirlerini belirt.",
        aiRole = "A senior project manager leading a business meeting.",
        goal = "Help the learner express professional opinions and agree/disagree politely.",
        targetIds = listOf("vocab_opinion", "phrase_in_my_opinion", "phrase_strongly_agree")
    )
)
