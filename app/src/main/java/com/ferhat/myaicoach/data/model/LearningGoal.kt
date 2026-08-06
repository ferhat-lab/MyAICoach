package com.ferhat.myaicoach.data.model

enum class LearningGoal(
    val title: String,
    val description: String
) {

    DAILY_CONVERSATION(
        title = "Günlük Konuşma",
        description = "Günlük hayatta rahat iletişim kurmak istiyorum."
    ),

    BUSINESS(
        title = "İş İngilizcesi",
        description = "Toplantılar, sunumlar ve iş hayatında İngilizce kullanmak istiyorum."
    ),

    EDUCATION(
        title = "Eğitim",
        description = "Okul, üniversite ve akademik çalışmalar için İngilizcemi geliştirmek istiyorum."
    ),

    TRAVEL(
        title = "Seyahat",
        description = "Yurt dışında rahat iletişim kurmak istiyorum."
    ),

    EXAM(
        title = "Sınav Hazırlığı",
        description = "IELTS, TOEFL, YDS gibi sınavlara hazırlanıyorum."
    )
}