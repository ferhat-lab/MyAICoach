package com.ferhat.myaicoach.domain.lesson

data class GrammarTarget(
    val id: String,

    // Kullanıcıya gösterilecek kısa başlık
    val title: String,

    // Kısa ve seviyeye uygun açıklama
    val explanation: String,

    // Hedef yapı
    val pattern: String,

    // Doğru kullanım örnekleri
    val examples: List<GrammarExample>,

    // Bu derste yeni mi, tekrar mı?
    val role: GrammarRole = GrammarRole.NEW
)

data class GrammarExample(
    val sentence: String,
    val translation: String
)

enum class GrammarRole {
    NEW,
    REVIEW
}