package com.ferhat.myaicoach.feature.lesson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferhat.myaicoach.data.repository.LessonRepositoryImpl
import com.ferhat.myaicoach.data.repository.StudentStateRepositoryImpl
import com.ferhat.myaicoach.domain.lesson.AudioChoiceActivity
import com.ferhat.myaicoach.domain.lesson.FillInTheBlankActivity
import com.ferhat.myaicoach.domain.lesson.Lesson
import com.ferhat.myaicoach.domain.lesson.LessonActivity
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
 * LessonViewModel: Pedagojik sıralı ders akışı ve cevap kontrolü mantığı.
 * Kelimeleri sırayla tanıtır, pekiştirir ve en sonda eşleştirme egzersizi (MatchingCard) ile tamamlar.
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
                    val activities = generatePedagogicalActivities(lesson)
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

    /**
     * generatePedagogicalActivities: Pedagojik Ders Akışı Algoritması.
     * 1. Kelime Tanıtımı (WordIntroduction)
     * 2. Çoktan Seçmeli Test (MultipleChoice - EN -> TR)
     * 3. Boşluk Doldurma (FillInTheBlank)
     * 4. Ters Seçenek Testi (ReverseChoice - TR -> EN)
     * 5. Cümle Kurma (SentenceBuilder)
     * 6. Kapanışta Kelime Eşleştirme Egzersizi (MatchingActivity - 2 Sütunlu Soketli Bezier İp Çizgili)
     */
    private fun generatePedagogicalActivities(lesson: Lesson): List<LessonActivity> {
        val activities = mutableListOf<LessonActivity>()
        val allWords = lesson.vocabulary.map { it.word }
        val allTranslations = lesson.vocabulary.map { it.translation }

        // 1. Her kelime için döngüsel pedagojik öğretim
        lesson.vocabulary.forEachIndexed { index, item ->
            // A) Kelime Tanıtım Kartı
            activities.add(
                WordIntroduction(
                    id = "intro_${item.id}",
                    targetIds = listOf(item.id),
                    wordId = item.id
                )
            )

            // B) Çoktan Seçmeli Test (EN -> TR)
            val trDistractors = (allTranslations - item.translation).shuffled().take(2)
            val mcOptions = (listOf(item.translation) + trDistractors).shuffled()
            activities.add(
                MultipleChoiceActivity(
                    id = "mc_${item.id}",
                    targetIds = listOf(item.id),
                    instruction = "Doğru Türkçe karşılığını seç.",
                    prompt = item.word,
                    options = mcOptions,
                    correctAnswer = item.translation
                )
            )

            // C) Ters Seçenek Testi (TR -> EN)
            val enDistractors = (allWords - item.word).shuffled().take(2)
            val rcOptions = (listOf(item.word) + enDistractors).shuffled()
            activities.add(
                ReverseChoiceActivity(
                    id = "rc_${item.id}",
                    targetIds = listOf(item.id),
                    instruction = "İngilizce karşılığını seç.",
                    prompt = item.translation,
                    options = rcOptions,
                    correctAnswer = item.word
                )
            )

            // D) Boşluk Doldurma (Cümle İçi Pratik)
            val exampleSentence = item.exampleSentence
            if (exampleSentence.contains(item.word, ignoreCase = true)) {
                val sentenceWithBlank = exampleSentence.replace(Regex(item.word, RegexOption.IGNORE_CASE), "___")
                    .replace(".", "")
                activities.add(
                    FillInTheBlankActivity(
                        id = "fill_${item.id}",
                        targetIds = listOf(item.id),
                        instruction = "Cümledeki boşluğu tamamla.",
                        sentenceWithBlank = sentenceWithBlank,
                        options = rcOptions,
                        correctAnswer = item.word
                    )
                )
            }
        }

        // 2. Cümle Oluşturma Egzersizleri (Lesson Exercises)
        lesson.exercises.filter { it.type == com.ferhat.myaicoach.domain.lesson.ExerciseType.SENTENCE_BUILDER }
            .forEach { exercise ->
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

        // 3. KAPANIŞ: Kelime Eşleştirme Egzersizi (MatchingActivity)
        val matchingPairs = lesson.vocabulary.associate { it.word to it.translation }
        activities.add(
            MatchingActivity(
                id = "matching_final_${lesson.id}",
                targetIds = lesson.vocabulary.map { it.id },
                instruction = "Öğrendiğin kelime çiftlerini eşleştir.",
                pairs = matchingPairs
            )
        )

        return activities
    }
}