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
 * A1Lesson3: "My Family & Friends" (Ailem ve Arkadaşlarım)
 * Oxford 3000 / CEFR A1 Standartlarına Uyumlu 3. Ders Paketimiz.
 */
val A1Lesson3 = Lesson(
    id = "a1_u1_l3",
    level = EnglishLevel.A1,
    unitId = "a1_u1",
    order = 3,
    title = "My Family & Friends",
    description = "Aile üyelerini ve arkadaşlarını İngilizce tanıtmama yardımcı olalım.",
    estimatedMinutes = 10,

    objectives = listOf(
        LessonObjective(
            id = "introduce_family",
            description = "Introduce family members and friends."
        ),
        LessonObjective(
            id = "use_possessives",
            description = "Use possessive adjectives 'my' and 'your'."
        )
    ),

    vocabulary = listOf(
        VocabularyItem(
            id = "vocab_family",
            word = "family",
            translation = "aile",
            pronunciation = "/ˈfæməli/",
            exampleSentence = "I love my family.",
            exampleTranslation = "Ailemi seviyorum.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_mother",
            word = "mother",
            translation = "anne",
            pronunciation = "/ˈmʌðə/",
            exampleSentence = "My mother is kind.",
            exampleTranslation = "Annem naziktir.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_father",
            word = "father",
            translation = "baba",
            pronunciation = "/ˈfɑːðə/",
            exampleSentence = "My father works hard.",
            exampleTranslation = "Babam çok çalışır.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_friend",
            word = "friend",
            translation = "arkadaş",
            pronunciation = "/frend/",
            exampleSentence = "Alex is my best friend.",
            exampleTranslation = "Alex benim en iyi arkadaşımdır.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_happy",
            word = "happy",
            translation = "mutlu",
            pronunciation = "/ˈhæpi/",
            exampleSentence = "We are a happy family.",
            exampleTranslation = "Biz mutlu bir aileyiz.",
            role = VocabularyRole.NEW
        )
    ),

    phrases = listOf(
        PhraseItem(
            id = "phrase_this_is",
            phrase = "This is...",
            translation = "Bu...",
            exampleSentence = "This is my friend, Sarah.",
            exampleTranslation = "Bu benim arkadaşım Sarah.",
            role = PhraseRole.NEW
        ),
        PhraseItem(
            id = "phrase_my_friend",
            phrase = "my friend",
            translation = "benim arkadaşım",
            exampleSentence = "This is my friend.",
            exampleTranslation = "Bu benim arkadaşım.",
            role = PhraseRole.NEW
        )
    ),

    grammarTargets = listOf(
        GrammarTarget(
            id = "grammar_possessive_my_your",
            title = "Aitlik Sıfatları (My / Your)",
            explanation = "Bir şeyin kime ait olduğunu söylerken 'my' (benim) veya 'your' (senin) kullanılır.",
            pattern = "my / your + [İsim]",
            examples = listOf(
                GrammarExample(
                    sentence = "This is my mother.",
                    translation = "Bu benim annem."
                ),
                GrammarExample(
                    sentence = "What is your name?",
                    translation = "Senin adın ne?"
                )
            ),
            role = GrammarRole.NEW
        )
    ),

    exercises = listOf(
        Exercise(
            id = "a1_u1_l3_ex1",
            type = ExerciseType.MULTIPLE_CHOICE,
            instruction = "Doğru kelimeyi seç.",
            prompt = "This is my ____.",
            options = listOf(
                "mother",
                "happy",
                "night"
            ),
            correctAnswer = "mother",
            explanation = "\"This is my...\" kalıbından sonra isim (mother) gelir.",
            targetIds = listOf("vocab_mother", "phrase_this_is"),
            order = 1
        ),
        Exercise(
            id = "a1_u1_l3_ex2",
            type = ExerciseType.SENTENCE_BUILDER,
            instruction = "Kelimeleri doğru sıraya koy.",
            prompt = "Bu benim arkadaşım.",
            options = listOf(
                "friend",
                "my",
                "This",
                "is"
            ),
            correctAnswer = "This is my friend.",
            explanation = "İngilizcede birini tanıtırken \"This is my friend.\" kalıbı kullanılır.",
            targetIds = listOf("phrase_this_is", "grammar_possessive_my_your"),
            order = 2
        ),
        Exercise(
            id = "a1_u1_l3_ex3",
            type = ExerciseType.FILL_IN_THE_BLANK,
            instruction = "Eksik kelimeyi tamamla.",
            prompt = "We are a ____ family.",
            correctAnswer = "happy",
            explanation = "Mutlu aile anlamında sıfat olan \"happy\" kelimesi kullanılır.",
            targetIds = listOf("vocab_happy", "vocab_family"),
            order = 3
        )
    ),

    listeningContent = ListeningContent(
        id = "a1_u1_l3_listening",
        instruction = "Konuşmayı dinle ve soruyu cevapla.",
        transcript = listOf(
            DialogueLine(
                speaker = "Vani",
                text = "Who is this, Alex?"
            ),
            DialogueLine(
                speaker = "Alex",
                text = "This is my friend, Sarah. She is very happy today."
            ),
            DialogueLine(
                speaker = "Vani",
                text = "Nice to meet you, Sarah! Where is your family?"
            ),
            DialogueLine(
                speaker = "Alex",
                text = "My family is in Turkey."
            )
        ),
        questions = listOf(
            ListeningQuestion(
                id = "a1_u1_l3_listening_q1",
                question = "Who is Sarah?",
                options = listOf(
                    "Alex's friend",
                    "Alex's mother",
                    "Vani's sister"
                ),
                correctAnswer = "Alex's friend",
                targetIds = listOf("phrase_my_friend")
            )
        ),
        targetIds = listOf("phrase_this_is", "vocab_friend")
    ),

    speakingScenario = SpeakingScenario(
        id = "a1_u1_l3_speaking",
        title = "Vani ile Aileni Tanıştır",
        description = "Vani'ye ailenden ve arkadaşlarından bahset.",
        aiRole = "A friendly AI cat coach asking about learner's family and friends.",
        goal = "Help learner introduce a family member or friend using 'This is my...'.",
        targetIds = listOf(
            "vocab_family",
            "vocab_friend",
            "phrase_this_is",
            "grammar_possessive_my_your"
        ),
        maxTurns = 8,
        maxAiSentenceWords = 15,
        correctionStyle = CorrectionStyle.GENTLE
    )
)
