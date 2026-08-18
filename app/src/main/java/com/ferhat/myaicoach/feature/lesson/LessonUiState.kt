package com.ferhat.myaicoach.feature.lesson

import com.ferhat.myaicoach.domain.lesson.Lesson
import com.ferhat.myaicoach.domain.lesson.LessonActivity

data class LessonUiState(
    val lesson: Lesson? = null,

    // Ders motorunun oluşturduğu aktivite sırası
    val activities: List<LessonActivity> = emptyList(),

    // Kullanıcının şu anda bulunduğu aktivite
    val currentActivityIndex: Int = 0,

    // Seçenekli sorularda kullanıcının seçimi
    val selectedAnswer: String? = null,

    // Cevabın mevcut durumu
    val answerState: AnswerState = AnswerState.IDLE,

    // Kaçıncı hatalı denemede olunduğunu takip eder (1. yanlış: Tekrar Dene, 2. yanlış: İpucu, 3. yanlış: Doğru Cevap Gösterimi)
    val attemptCount: Int = 0,

    val isLoading: Boolean = false,

    val errorMessage: String? = null
)

enum class AnswerState {
    IDLE,
    CORRECT,
    INCORRECT
}