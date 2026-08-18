package com.ferhat.myaicoach.feature.lesson

import androidx.lifecycle.ViewModel
import com.ferhat.myaicoach.domain.lesson.AudioChoiceActivity
import com.ferhat.myaicoach.domain.lesson.FillInTheBlankActivity
import com.ferhat.myaicoach.domain.lesson.MatchingActivity
import com.ferhat.myaicoach.domain.lesson.MultipleChoiceActivity
import com.ferhat.myaicoach.domain.lesson.ReverseChoiceActivity
import com.ferhat.myaicoach.domain.lesson.SentenceBuilderActivity
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
                // 1. Target Encounter: name
                WordIntroduction(
                    id = "intro_name",
                    targetIds = listOf("vocab_name"),
                    wordId = "vocab_name"
                ),

                // 2. Target Encounter: from
                WordIntroduction(
                    id = "intro_from",
                    targetIds = listOf("vocab_from"),
                    wordId = "vocab_from"
                ),

                // 3. Multiple Choice: from (EN -> TR)
                MultipleChoiceActivity(
                    id = "mc_from",
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

                // 4. Reverse Choice: name (TR -> EN)
                ReverseChoiceActivity(
                    id = "rc_name",
                    targetIds = listOf("vocab_name"),
                    instruction = "İngilizce karşılığını seç.",
                    prompt = "isim",
                    options = listOf(
                        "from",
                        "name",
                        "live"
                    ),
                    correctAnswer = "name"
                ),

                // 5. Target Encounter: live
                WordIntroduction(
                    id = "intro_live",
                    targetIds = listOf("vocab_live"),
                    wordId = "vocab_live"
                ),

                // 6. Audio Choice: name
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
                ),

                // 7. Fill in the Blank: name
                FillInTheBlankActivity(
                    id = "fill_name",
                    targetIds = listOf("vocab_name"),
                    instruction = "Cümleyi tamamla.",
                    sentenceWithBlank = "My ___ is Alex.",
                    options = listOf(
                        "name",
                        "from",
                        "live"
                    ),
                    correctAnswer = "name"
                ),

                // 8. Reverse Choice: from
                ReverseChoiceActivity(
                    id = "rc_from",
                    targetIds = listOf("vocab_from"),
                    instruction = "İngilizce karşılığını seç.",
                    prompt = "-den / -dan",
                    options = listOf(
                        "live",
                        "city",
                        "from"
                    ),
                    correctAnswer = "from"
                ),

                // 9. Sentence Builder: My name is Alex.
                SentenceBuilderActivity(
                    id = "sb_my_name",
                    targetIds = listOf("vocab_name"),
                    instruction = "Cümleyi oluştur.",
                    promptTranslation = "Benim adım Alex.",
                    wordChips = listOf("Alex", "name", "My", "is"),
                    correctSentence = "My name is Alex"
                ),

                // 10. Target Encounter: city
                WordIntroduction(
                    id = "intro_city",
                    targetIds = listOf("vocab_city"),
                    wordId = "vocab_city"
                ),

                // 11. Fill in the Blank: from
                FillInTheBlankActivity(
                    id = "fill_from",
                    targetIds = listOf("vocab_from"),
                    instruction = "Cümleyi tamamla.",
                    sentenceWithBlank = "I am ___ Turkey.",
                    options = listOf(
                        "from",
                        "city",
                        "name"
                    ),
                    correctAnswer = "from"
                ),

                // 12. Audio Choice: live
                AudioChoiceActivity(
                    id = "audio_live",
                    targetIds = listOf("vocab_live"),
                    audioText = "live",
                    options = listOf(
                        "live",
                        "city",
                        "from"
                    ),
                    correctAnswer = "live"
                ),

                // 13. Matching: hello, name, from, live
                MatchingActivity(
                    id = "match_mix_1",
                    targetIds = listOf("vocab_hello", "vocab_name", "vocab_from", "vocab_live"),
                    instruction = "Kelime çiftlerini eşleştir.",
                    pairs = mapOf(
                        "hello" to "merhaba",
                        "name" to "isim",
                        "from" to "-den / -dan",
                        "live" to "yaşamak"
                    )
                ),

                // 14. Sentence Builder: I'm from Turkey.
                SentenceBuilderActivity(
                    id = "sb_im_from",
                    targetIds = listOf("vocab_from"),
                    instruction = "Cümleyi oluştur.",
                    promptTranslation = "Ben Türkiye'denim.",
                    wordChips = listOf("Turkey", "from", "I'm"),
                    correctSentence = "I'm from Turkey"
                )
            )
        )
    )

    val uiState: StateFlow<LessonUiState> = _uiState.asStateFlow()

    fun selectAnswer(answer: String?) {
        _uiState.update {
            it.copy(
                selectedAnswer = answer,
                // Re-selecting an answer resets INCORRECT back to IDLE so CHECK activates again
                answerState = if (it.answerState == AnswerState.INCORRECT) AnswerState.IDLE else it.answerState
            )
        }
    }

    fun retryActivity() {
        _uiState.update {
            it.copy(
                answerState = AnswerState.IDLE
            )
        }
    }

    fun checkAnswer() {
        val state = _uiState.value
        val activity = state.activities.getOrNull(state.currentActivityIndex) ?: return
        val answer = state.selectedAnswer ?: return

        val isCorrect = when (activity) {
            is MultipleChoiceActivity -> answer.trim() == activity.correctAnswer.trim()
            is ReverseChoiceActivity -> answer.trim() == activity.correctAnswer.trim()
            is AudioChoiceActivity -> answer.trim() == activity.correctAnswer.trim()
            is FillInTheBlankActivity -> answer.trim() == activity.correctAnswer.trim()
            is SentenceBuilderActivity -> answer.trim() == activity.correctSentence.trim()
            is MatchingActivity -> answer == "COMPLETED"
            else -> true
        }

        if (isCorrect) {
            _uiState.update {
                it.copy(answerState = AnswerState.CORRECT)
            }
        } else {
            _uiState.update {
                it.copy(
                    answerState = AnswerState.INCORRECT,
                    attemptCount = it.attemptCount + 1
                )
            }
        }
    }

    fun nextActivity() {
        val state = _uiState.value

        if (state.currentActivityIndex < state.activities.lastIndex) {
            _uiState.update {
                it.copy(
                    currentActivityIndex = it.currentActivityIndex + 1,
                    selectedAnswer = null,
                    answerState = AnswerState.IDLE,
                    attemptCount = 0
                )
            }
        }
    }
}