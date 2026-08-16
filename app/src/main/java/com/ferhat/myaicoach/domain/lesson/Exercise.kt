package com.ferhat.myaicoach.domain.lesson

data class Exercise(
    val id: String,

    // Egzersiz türü
    val type: ExerciseType,

    // Kullanıcıya gösterilecek soru / talimat
    val instruction: String,

    // Asıl soru veya cümle
    val prompt: String,

    // Seçenek gerektiren sorularda kullanılır
    val options: List<String> = emptyList(),

    // Beklenen doğru cevap
    val correctAnswer: String,

    // Cevaptan sonra gösterilecek kısa açıklama
    val explanation: String? = null,

    // Bu egzersizin ölçtüğü eğitim hedefleri
    val targetIds: List<String>,

    // Ders içerisindeki sıra
    val order: Int
)

enum class ExerciseType {
    MULTIPLE_CHOICE,
    FILL_IN_THE_BLANK,
    SENTENCE_BUILDER
}