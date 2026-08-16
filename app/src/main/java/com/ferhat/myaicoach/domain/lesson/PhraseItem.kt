package com.ferhat.myaicoach.domain.lesson

data class PhraseItem(
    val id: String,

    // Öğretilecek kalıp
    val phrase: String,

    // Kullanıcının dilindeki anlamı
    val translation: String,

    // Örnek kullanım
    val exampleSentence: String,

    // Örnek kullanımın çevirisi
    val exampleTranslation: String,

    // Bu derste yeni mi, tekrar mı?
    val role: PhraseRole = PhraseRole.NEW
)

enum class PhraseRole {
    NEW,
    REVIEW
}