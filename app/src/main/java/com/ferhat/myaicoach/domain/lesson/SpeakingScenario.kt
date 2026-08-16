package com.ferhat.myaicoach.domain.lesson

data class SpeakingScenario(
    val id: String,

    // Kullanıcıya gösterilecek başlık
    val title: String,

    // Senaryonun kısa açıklaması
    val description: String,

    // Vani'nin bu konuşmadaki rolü
    val aiRole: String,

    // Öğrencinin konuşma sonunda başarması gereken şey
    val goal: String,

    // Kullanılması hedeflenen kalıp / kelime / gramer ID'leri
    val targetIds: List<String>,

    // Önceki derslerden konuşmada tekrar ettirilebilecek hedefler
    val reviewTargetIds: List<String> = emptyList(),

    // Konuşmanın maksimum tur sayısı
    val maxTurns: Int = 8,

    // AI cevabının kısa tutulması için pedagojik sınır
    val maxAiSentenceWords: Int = 15,

    // Hata düzeltme davranışı
    val correctionStyle: CorrectionStyle = CorrectionStyle.GENTLE
)

enum class CorrectionStyle {
    GENTLE,
    DIRECT,
    ONLY_WHEN_NEEDED
}