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
 * A1Lesson6: "At the Supermarket" (Süpermarkette Alışveriş)
 * Oxford 3000 / CEFR A1 Ünite 2 - 6. Ders Paketimiz.
 */
val A1Lesson6 = Lesson(
    id = "a1_u2_l6",
    level = EnglishLevel.A1,
    unitId = "a1_u2",
    order = 6,
    title = "At the Supermarket",
    description = "Süpermarkette alışveriş yapmayı ve fiyat sormayı öğren.",
    estimatedMinutes = 10,

    objectives = listOf(
        LessonObjective(
            id = "ask_price",
            description = "Ask for prices using 'How much is...?'."
        ),
        LessonObjective(
            id = "shopping_phrases",
            description = "Use common shopping phrases like 'Here you are'."
        )
    ),

    vocabulary = listOf(
        VocabularyItem(
            id = "vocab_buy",
            word = "buy",
            translation = "satın almak",
            pronunciation = "/baɪ/",
            exampleSentence = "I want to buy fresh apples.",
            exampleTranslation = "Taze elma satın almak istiyorum.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_price",
            word = "price",
            translation = "fiyat",
            pronunciation = "/praɪs/",
            exampleSentence = "The price is five dollars.",
            exampleTranslation = "Fiyatı beş dolardır.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_money",
            word = "money",
            translation = "para",
            pronunciation = "/ˈmʌni/",
            exampleSentence = "I have money for shopping.",
            exampleTranslation = "Alışveriş için param var.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_bag",
            word = "bag",
            translation = "çanta / poşet",
            pronunciation = "/bæɡ/",
            exampleSentence = "I need a shopping bag.",
            exampleTranslation = "Alışveriş poşetine ihtiyacım var.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_cheap",
            word = "cheap",
            translation = "ucuz",
            pronunciation = "/tʃiːp/",
            exampleSentence = "This apple is cheap.",
            exampleTranslation = "Bu elma ucuzdur.",
            role = VocabularyRole.NEW
        )
    ),

    phrases = listOf(
        PhraseItem(
            id = "phrase_how_much_is",
            phrase = "How much is...",
            translation = "...ne kadar?",
            exampleSentence = "How much is this bread?",
            exampleTranslation = "Bu ekmek ne kadar?",
            role = PhraseRole.NEW
        ),
        PhraseItem(
            id = "phrase_here_you_are",
            phrase = "Here you are.",
            translation = "Buyurun.",
            exampleSentence = "Here you are, your bag and change.",
            exampleTranslation = "Buyurun, poşetiniz ve para üstünüz.",
            role = PhraseRole.NEW
        )
    ),

    grammarTargets = listOf(
        GrammarTarget(
            id = "grammar_how_much",
            title = "Fiyat Sorma (How much is...?)",
            explanation = "Bir ürünün fiyatını sorarken 'How much is this / that...?' yapısı kullanılır.",
            pattern = "How much is + [Ürün] ?",
            examples = listOf(
                GrammarExample(
                    sentence = "How much is this?",
                    translation = "Bu ne kadar?"
                ),
                GrammarExample(
                    sentence = "How much is the water?",
                    translation = "Su ne kadar?"
                )
            ),
            role = GrammarRole.NEW
        )
    ),

    exercises = listOf(
        Exercise(
            id = "a1_u2_l6_ex1",
            type = ExerciseType.MULTIPLE_CHOICE,
            instruction = "Doğru kelimeyi seç.",
            prompt = "How much is the ____?",
            options = listOf(
                "price",
                "night",
                "room"
            ),
            correctAnswer = "price",
            explanation = "Fiyat sorusunda nesne veya \"price\" tanımı kullanılır.",
            targetIds = listOf("vocab_price", "phrase_how_much_is"),
            order = 1
        ),
        Exercise(
            id = "a1_u2_l6_ex2",
            type = ExerciseType.SENTENCE_BUILDER,
            instruction = "Kelimeleri doğru sıraya koy.",
            prompt = "Bu ekmek ne kadar?",
            options = listOf(
                "much",
                "How",
                "is",
                "this",
                "bread"
            ),
            correctAnswer = "How much is this bread",
            explanation = "Fiyat sorusu \"How much is...\" kalıbıyla başlar.",
            targetIds = listOf("phrase_how_much_is", "grammar_how_much"),
            order = 2
        ),
        Exercise(
            id = "a1_u2_l6_ex3",
            type = ExerciseType.FILL_IN_THE_BLANK,
            instruction = "Eksik kelimeyi tamamla.",
            prompt = "I need a shopping ____.",
            options = listOf(
                "bag",
                "money",
                "buy"
            ),
            correctAnswer = "bag",
            explanation = "Alışveriş poşeti için \"bag\" kelimesi seçilir.",
            targetIds = listOf("vocab_bag"),
            order = 3
        )
    ),

    listeningContent = ListeningContent(
        id = "a1_u2_l6_listening",
        instruction = "Konuşmayı dinle ve soruyu cevapla.",
        transcript = listOf(
            DialogueLine(
                speaker = "Alex",
                text = "Hello Vani! How much is this bread?"
            ),
            DialogueLine(
                speaker = "Vani",
                text = "It is two dollars. It is very cheap!"
            ),
            DialogueLine(
                speaker = "Alex",
                text = "Great! I want to buy a bag of bread."
            ),
            DialogueLine(
                speaker = "Vani",
                text = "Here you are!"
            )
        ),
        questions = listOf(
            ListeningQuestion(
                id = "a1_u2_l6_listening_q1",
                question = "How much is the bread?",
                options = listOf(
                    "Two dollars",
                    "Ten dollars",
                    "Free"
                ),
                correctAnswer = "Two dollars",
                targetIds = listOf("vocab_price", "vocab_cheap")
            )
        ),
        targetIds = listOf("phrase_how_much_is", "phrase_here_you_are")
    ),

    speakingScenario = SpeakingScenario(
        id = "a1_u2_l6_speaking",
        title = "Vani'nin Süpermarketinde Alışveriş",
        description = "Vani'nin süpermarketinde ürün fiyatı sor ve alışveriş yap.",
        aiRole = "A friendly AI cat cashier at a local supermarket.",
        goal = "Help learner ask 'How much is this?' and complete a simple purchase.",
        targetIds = listOf(
            "vocab_buy",
            "vocab_price",
            "phrase_how_much_is",
            "grammar_how_much"
        ),
        maxTurns = 8,
        maxAiSentenceWords = 15,
        correctionStyle = CorrectionStyle.GENTLE
    )
)
