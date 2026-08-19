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
 * A1Lesson8: "My Favorite Things" (Sevdiğim Şeyler & Hobiler)
 * CEFR A1 Ünite 2 Kapanış Dersi.
 */
val A1Lesson8 = Lesson(
    id = "a1_u2_l8",
    level = EnglishLevel.A1,
    unitId = "a1_u2",
    order = 8,
    title = "My Favorite Things",
    description = "En sevdiğin şeyleri ve hobilerini anlatmayı öğren.",
    estimatedMinutes = 10,

    objectives = listOf(
        LessonObjective(
            id = "express_favorites",
            description = "Express your favorite food, drinks, and hobbies."
        ),
        LessonObjective(
            id = "use_like_love",
            description = "Use verbs 'like' and 'love' to express preferences."
        )
    ),

    vocabulary = listOf(
        VocabularyItem(
            id = "vocab_favorite",
            word = "favorite",
            translation = "favori / en sevilen",
            pronunciation = "/ˈfeɪvərɪt/",
            exampleSentence = "Coffee is my favorite drink.",
            exampleTranslation = "Kahve en sevdiğim içecektir.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_music",
            word = "music",
            translation = "müzik",
            pronunciation = "/ˈmjuːzɪk/",
            exampleSentence = "I listen to music every day.",
            exampleTranslation = "Her gün müzik dinlerim.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_game",
            word = "game",
            translation = "oyun",
            pronunciation = "/ɡeɪm/",
            exampleSentence = "I play a fun game.",
            exampleTranslation = "Eğlenceli bir oyun oynarım.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_sport",
            word = "sport",
            translation = "spor",
            pronunciation = "/spɔːt/",
            exampleSentence = "Football is a popular sport.",
            exampleTranslation = "Futbol popüler bir spordur.",
            role = VocabularyRole.NEW
        ),
        VocabularyItem(
            id = "vocab_love",
            word = "love",
            translation = "çok sevmek",
            pronunciation = "/lʌv/",
            exampleSentence = "I love learning English with Vani.",
            exampleTranslation = "Vani ile İngilizce öğrenmeyi çok seviyorum.",
            role = VocabularyRole.NEW
        )
    ),

    phrases = listOf(
        PhraseItem(
            id = "phrase_my_favorite_is",
            phrase = "My favorite ... is...",
            translation = "En sevdiğim... ...'dır",
            exampleSentence = "My favorite food is bread.",
            exampleTranslation = "En sevdiğim yiyecek ekmektir.",
            role = PhraseRole.NEW
        ),
        PhraseItem(
            id = "phrase_i_love",
            phrase = "I love...",
            translation = "...'ı çok seviyorum",
            exampleSentence = "I love music.",
            exampleTranslation = "Müziği çok seviyorum.",
            role = PhraseRole.NEW
        )
    ),

    grammarTargets = listOf(
        GrammarTarget(
            id = "grammar_like_love",
            title = "Beğeni İfadesi (I like / I love)",
            explanation = "Bir nesneyi veya eylemi sevdiğini ifade ederken 'I like...' veya daha güçlüsü 'I love...' kullanılır.",
            pattern = "I love / like + [İsim]",
            examples = listOf(
                GrammarExample(
                    sentence = "I love music.",
                    translation = "Müziği çok seviyorum."
                ),
                GrammarExample(
                    sentence = "My favorite drink is tea.",
                    translation = "En sevdiğim içecek çaydır."
                )
            ),
            role = GrammarRole.NEW
        )
    ),

    exercises = listOf(
        Exercise(
            id = "a1_u2_l8_ex1",
            type = ExerciseType.MULTIPLE_CHOICE,
            instruction = "Doğru kelimeyi seç.",
            prompt = "My ____ food is apple.",
            options = listOf(
                "favorite",
                "time",
                "hour"
            ),
            correctAnswer = "favorite",
            explanation = "En sevilen anlamında \"favorite\" kullanılır.",
            targetIds = listOf("vocab_favorite", "phrase_my_favorite_is"),
            order = 1
        ),
        Exercise(
            id = "a1_u2_l8_ex2",
            type = ExerciseType.SENTENCE_BUILDER,
            instruction = "Kelimeleri doğru sıraya koy.",
            prompt = "Müziği çok seviyorum.",
            options = listOf(
                "love",
                "music",
                "I"
            ),
            correctAnswer = "I love music",
            explanation = "Sevilen şeyler söylenirken \"I love...\" kalıbı kurulur.",
            targetIds = listOf("phrase_i_love", "grammar_like_love"),
            order = 2
        ),
        Exercise(
            id = "a1_u2_l8_ex3",
            type = ExerciseType.FILL_IN_THE_BLANK,
            instruction = "Eksik kelimeyi tamamla.",
            prompt = "I play a fun ____.",
            options = listOf(
                "game",
                "sport",
                "clock"
            ),
            correctAnswer = "game",
            explanation = "Oynanan aktivite için \"game\" (oyun) seçilir.",
            targetIds = listOf("vocab_game"),
            order = 3
        )
    ),

    listeningContent = ListeningContent(
        id = "a1_u2_l8_listening",
        instruction = "Konuşmayı dinle ve soruyu cevapla.",
        transcript = listOf(
            DialogueLine(
                speaker = "Vani",
                text = "What is your favorite sport, Alex?"
            ),
            DialogueLine(
                speaker = "Alex",
                text = "I love football! What about you, Vani?"
            ),
            DialogueLine(
                speaker = "Vani",
                text = "My favorite thing is playing games and eating apples!"
            ),
            DialogueLine(
                speaker = "Alex",
                text = "Haha, that sounds fun!"
            )
        ),
        questions = listOf(
            ListeningQuestion(
                id = "a1_u2_l8_listening_q1",
                question = "What is Alex's favorite sport?",
                options = listOf(
                    "Football",
                    "Basketball",
                    "Tennis"
                ),
                correctAnswer = "Football",
                targetIds = listOf("vocab_sport")
            )
        ),
        targetIds = listOf("phrase_my_favorite_is", "vocab_favorite")
    ),

    speakingScenario = SpeakingScenario(
        id = "a1_u2_l8_speaking",
        title = "Vani ile En Sevdiğin Şeyleri Konuş",
        description = "Vani'ye en sevdiğin yiyecek, müzik ve oyunlardan bahset.",
        aiRole = "A friendly AI cat coach sharing hobbies.",
        goal = "Help learner express their favorite things using 'My favorite ... is ...' and 'I love ...'.",
        targetIds = listOf(
            "vocab_favorite",
            "vocab_music",
            "phrase_i_love",
            "grammar_like_love"
        ),
        maxTurns = 8,
        maxAiSentenceWords = 15,
        correctionStyle = CorrectionStyle.GENTLE
    )
)
