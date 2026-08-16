package com.ferhat.myaicoach.domain.lesson

data class VocabularyItem(
    val id: String,

    // Öğretilecek İngilizce kelime
    val word: String,

    // Kullanıcının dilindeki anlam
    val translation: String,

    // IPA telaffuzu
    val pronunciation: String? = null,

    // Basit örnek cümle
    val exampleSentence: String,

    // Örnek cümlenin çevirisi
    val exampleTranslation: String,

    // Bu derste yeni mi, tekrar mı?
    val role: VocabularyRole = VocabularyRole.NEW
)

enum class VocabularyRole {
    NEW,
    REVIEW
}