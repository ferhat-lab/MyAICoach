package com.ferhat.myaicoach.feature.lesson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferhat.myaicoach.data.repository.LessonRepositoryImpl
import com.ferhat.myaicoach.data.repository.StudentStateRepositoryImpl
import com.ferhat.myaicoach.domain.lesson.AudioChoiceActivity
import com.ferhat.myaicoach.domain.lesson.FillInTheBlankActivity
import com.ferhat.myaicoach.domain.lesson.Lesson
import com.ferhat.myaicoach.domain.lesson.LessonValidator
import com.ferhat.myaicoach.domain.lesson.MatchingActivity
import com.ferhat.myaicoach.domain.lesson.MultipleChoiceActivity
import com.ferhat.myaicoach.domain.lesson.ReverseChoiceActivity
import com.ferhat.myaicoach.domain.lesson.SentenceBuilderActivity
import com.ferhat.myaicoach.domain.lesson.ValidationResult
import com.ferhat.myaicoach.domain.lesson.WordIntroduction
import com.ferhat.myaicoach.domain.repository.LessonRepository
import com.ferhat.myaicoach.domain.repository.StudentStateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * LessonViewModel: Ders akışı ve cevap kontrolü mantığı.
 * LessonRepository ve StudentStateRepository katmanlarına bağlıdır.
 */
class LessonViewModel(
    private val lessonRepository: LessonRepository = LessonRepositoryImpl(),
    private val studentStateRepository: StudentStateRepository = StudentStateRepositoryImpl()
) : ViewModel() {

    private val _uiState = MutableStateFlow(LessonUiState())
    val uiState: StateFlow<LessonUiState> = _uiState.asStateFlow()

    init {
        loadLesson("a1_u1_l1")
    }

    fun loadLesson(lessonId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            lessonRepository.getLessonById(lessonId).collect { lesson ->
                if (lesson != null) {
                    val activities = generateActivitiesForLesson(lesson)
                    val validation = LessonValidator.validate(lesson, activities)

                    if (validation is ValidationResult.Error) {
                        _uiState.update {
                            it.copy(
                                lesson = lesson,
                                activities = emptyList(),
                                errorMessage = validation.message,
                                isLoading = false
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                lesson = lesson,
                                activities = activities,
                                errorMessage = null,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun selectAnswer(answer: String?) {
        _uiState.update {
            it.copy(
                selectedAnswer = answer,
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
        } else {
            // Ders Tamamlandı -> Öğrenci Durumunu Güncelle
            viewModelScope.launch {
                state.lesson?.let { completedLesson ->
                    studentStateRepository.completeLesson(
                        lessonId = completedLesson.id,
                        xpEarned = 50,
                        learnedTargetIds = completedLesson.vocabulary.map { it.id }
                    )
                }
            }
        }
    }

    private fun generateActivitiesForLesson(lesson: Lesson): List<com.ferhat.myaicoach.domain.lesson.LessonActivity> {
        val activities = mutableListOf<com.ferhat.myaicoach.domain.lesson.LessonActivity>()

        // 1. Kelime Tanıtım Kartları
        lesson.vocabulary.forEach { item ->
            activities.add(
                WordIntroduction(
                    id = "intro_${item.id}",
                    targetIds = listOf(item.id),
                    wordId = item.id
                )
            )
        }

        // 2. Çoktan Seçmeli & Eşleştirme Aktiviteleri
        lesson.exercises.forEach { exercise ->
            when (exercise.type) {
                com.ferhat.myaicoach.domain.lesson.ExerciseType.MULTIPLE_CHOICE -> {
                    activities.add(
                        MultipleChoiceActivity(
                            id = exercise.id,
                            targetIds = exercise.targetIds,
                            instruction = exercise.instruction,
                            prompt = exercise.prompt,
                            options = exercise.options,
                            correctAnswer = exercise.correctAnswer
                        )
                    )
                }
                com.ferhat.myaicoach.domain.lesson.ExerciseType.SENTENCE_BUILDER -> {
                    activities.add(
                        SentenceBuilderActivity(
                            id = exercise.id,
                            targetIds = exercise.targetIds,
                            instruction = exercise.instruction,
                            promptTranslation = exercise.prompt,
                            wordChips = exercise.options,
                            correctSentence = exercise.correctAnswer
                        )
                    )
                }
                com.ferhat.myaicoach.domain.lesson.ExerciseType.FILL_IN_THE_BLANK -> {
                    activities.add(
                        FillInTheBlankActivity(
                            id = exercise.id,
                            targetIds = exercise.targetIds,
                            instruction = exercise.instruction,
                            sentenceWithBlank = exercise.prompt,
                            options = exercise.options,
                            correctAnswer = exercise.correctAnswer
                        )
                    )
                }
                com.ferhat.myaicoach.domain.lesson.ExerciseType.MATCHING -> {
                    activities.add(
                        MatchingActivity(
                            id = exercise.id,
                            targetIds = exercise.targetIds,
                            instruction = exercise.instruction,
                            pairs = exercise.options.associateWith { "çeviri" }
                        )
                    )
                }
            }
        }

        return activities
    }
}