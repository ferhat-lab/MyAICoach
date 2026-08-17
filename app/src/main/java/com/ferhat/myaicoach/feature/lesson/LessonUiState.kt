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

    val isLoading: Boolean = false,

    val errorMessage: String? = null
)

enum class AnswerState {
    IDLE,
    CORRECT,
    INCORRECT
}