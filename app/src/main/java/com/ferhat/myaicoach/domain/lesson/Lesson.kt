package com.ferhat.myaicoach.domain.lesson

import com.ferhat.myaicoach.data.model.EnglishLevel

data class Lesson(
    val id: String,

    val level: EnglishLevel,

    val unitId: String,

    // Unit içindeki ders sırası
    val order: Int,

    val title: String,

    val description: String,

    val estimatedMinutes: Int,

    // Ders sonunda kullanıcının ne yapabiliyor olması gerekiyor?
    val objectives: List<LessonObjective>,

    // Bu derste öğretilen / tekrar edilen kelimeler
    val vocabulary: List<VocabularyItem>,

    // İletişim kalıpları
    val phrases: List<PhraseItem>,

    // Küçük gramer hedefleri
    val grammarTargets: List<GrammarTarget>,

    // Practice aşamasındaki egzersizler
    val exercises: List<Exercise>,

    // Listening aşaması
    val listeningContent: ListeningContent? = null,

    // Vani ile final konuşması
    val speakingScenario: SpeakingScenario? = null
)