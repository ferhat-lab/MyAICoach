package com.ferhat.myaicoach.feature.lesson

import androidx.lifecycle.ViewModel
import com.ferhat.myaicoach.domain.lesson.AudioChoiceActivity
import com.ferhat.myaicoach.domain.lesson.MultipleChoiceActivity
import com.ferhat.myaicoach.domain.lesson.WordIntroduction
import com.ferhat.myaicoach.domain.lesson.sample.A1Lesson1
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LessonViewModel : ViewModel() {

    private val lesson = A1Lesson1

    private val _uiState = MutableStateFlow(
        LessonUiState(
            lesson = lesson,
            activities = listOf(
                WordIntroduction(
                    id = "intro_name",
                    targetIds = listOf("vocab_name"),
                    wordId = "vocab_name"
                ),

                WordIntroduction(
                    id = "intro_from",
                    targetIds = listOf("vocab_from"),
                    wordId = "vocab_from"
                ),

                MultipleChoiceActivity(
                    id = "check_from",
                    targetIds = listOf("vocab_from"),
                    instruction = "Doğru anlamı seç.",
                    prompt = "from",
                    options = listOf(
                        "-den / -dan",
                        "isim",
                        "yaşamak"
                    ),
                    correctAnswer = "-den / -dan"
                ),

                AudioChoiceActivity(
                    id = "audio_name",
                    targetIds = listOf("vocab_name"),
                    audioText = "name",
                    options = listOf(
                        "name",
                        "from",
                        "live"
                    ),
                    correctAnswer = "name"
                )
            )
        )
    )

    val uiState: StateFlow<LessonUiState> = _uiState.asStateFlow()

    fun selectAnswer(answer: String) {
        val state = _uiState.value

        val activity = state.activities
            .getOrNull(state.currentActivityIndex)
            ?: return

        val correctAnswer = when (activity) {
            is MultipleChoiceActivity -> activity.correctAnswer
            is AudioChoiceActivity -> activity.correctAnswer
            else -> return
        }

        val result = if (answer == correctAnswer) {
            AnswerState.CORRECT
        } else {
            AnswerState.INCORRECT
        }

        _uiState.update {
            it.copy(
                selectedAnswer = answer,
                answerState = result
            )
        }
    }

    fun nextActivity() {
        val state = _uiState.value

        if (state.currentActivityIndex < state.activities.lastIndex) {
            _uiState.update {
                it.copy(
                    currentActivityIndex = it.currentActivityIndex + 1,
                    selectedAnswer = null,
                    answerState = AnswerState.IDLE
                )
            }
        }
    }
}