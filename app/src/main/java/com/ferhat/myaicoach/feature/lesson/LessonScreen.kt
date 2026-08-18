package com.ferhat.myaicoach.feature.lesson

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ferhat.myaicoach.domain.lesson.AudioChoiceActivity
import com.ferhat.myaicoach.domain.lesson.FillInTheBlankActivity
import com.ferhat.myaicoach.domain.lesson.MatchingActivity
import com.ferhat.myaicoach.domain.lesson.MultipleChoiceActivity
import com.ferhat.myaicoach.domain.lesson.ReverseChoiceActivity
import com.ferhat.myaicoach.domain.lesson.SentenceBuilderActivity
import com.ferhat.myaicoach.domain.lesson.WordIntroduction
import com.ferhat.myaicoach.feature.lesson.components.AudioChoiceCard
import com.ferhat.myaicoach.feature.lesson.components.FillInTheBlankCard
import com.ferhat.myaicoach.feature.lesson.components.LessonBottomBar
import com.ferhat.myaicoach.feature.lesson.components.LessonCompletionCard
import com.ferhat.myaicoach.feature.lesson.components.LessonTopBar
import com.ferhat.myaicoach.feature.lesson.components.MatchingCard
import com.ferhat.myaicoach.feature.lesson.components.MultipleChoiceCard
import com.ferhat.myaicoach.feature.lesson.components.ReverseChoiceCard
import com.ferhat.myaicoach.feature.lesson.components.SentenceBuilderCard
import com.ferhat.myaicoach.feature.lesson.components.WordIntroductionCard
import com.ferhat.myaicoach.ui.animation.ConfettiEffect

@Composable
fun LessonScreen(
    viewModel: LessonViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onPlayAudio: (String) -> Unit = {}
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var showExitDialog by remember { mutableStateOf(false) }
    var isLessonCompleted by remember { mutableStateOf(false) }

    val lesson = state.lesson ?: return
    val totalActivities = state.activities.size
    val currentIndex = state.currentActivityIndex
    val activity = state.activities.getOrNull(currentIndex)

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = {
                Text(
                    text = "Dersden çıkmak istiyor musun?",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "Şu ana kadarki ilerlemen kaybolabilir.",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showExitDialog = false
                        onNavigateBack()
                    }
                ) {
                    Text(
                        text = "Çık",
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) {
                    Text("Devam Et")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            if (!isLessonCompleted && state.errorMessage == null) {
                LessonTopBar(
                    currentIndex = currentIndex,
                    totalCount = totalActivities,
                    onCloseClick = { showExitDialog = true }
                )
            }
        },
        bottomBar = {
            if (!isLessonCompleted && activity != null && state.errorMessage == null) {
                val isIntro = activity is WordIntroduction
                val correctAnswer = when (activity) {
                    is MultipleChoiceActivity -> activity.correctAnswer
                    is ReverseChoiceActivity -> activity.correctAnswer
                    is AudioChoiceActivity -> activity.correctAnswer
                    is FillInTheBlankActivity -> activity.correctAnswer
                    is SentenceBuilderActivity -> activity.correctSentence
                    else -> null
                }

                LessonBottomBar(
                    answerState = state.answerState,
                    selectedAnswer = state.selectedAnswer,
                    correctAnswer = correctAnswer,
                    attemptCount = state.attemptCount,
                    isIntroduction = isIntro,
                    onCheckClick = {
                        viewModel.checkAnswer()
                    },
                    onNextClick = {
                        if (currentIndex < state.activities.lastIndex) {
                            viewModel.nextActivity()
                        } else {
                            isLessonCompleted = true
                        }
                    },
                    onRetryClick = {
                        viewModel.retryActivity()
                    }
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (state.errorMessage != null) {
                // Data Integrity Validation Error UI
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Veri Hatası",
                                tint = Color(0xFFEF4444),
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Müfredat Veri Hatası",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = state.errorMessage ?: "",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Button(
                                onClick = onNavigateBack,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Geri Dön")
                            }
                        }
                    }
                }
            } else if (isLessonCompleted) {
                LessonCompletionCard(
                    lessonTitle = lesson.title,
                    onCompleteClick = onNavigateBack,
                    modifier = Modifier.fillMaxSize()
                )
            } else if (activity != null) {
                AnimatedContent(
                    targetState = currentIndex,
                    transitionSpec = {
                        if (targetState > initialState) {
                            (slideInHorizontally { width -> width } + fadeIn()).togetherWith(
                                slideOutHorizontally { width -> -width } + fadeOut()
                            )
                        } else {
                            (slideInHorizontally { width -> -width } + fadeIn()).togetherWith(
                                slideOutHorizontally { width -> width } + fadeOut()
                            )
                        }
                    },
                    label = "activityTransition"
                ) { targetIndex ->
                    val targetActivity = state.activities.getOrNull(targetIndex)

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 24.dp, vertical = 12.dp)
                    ) {
                        when (targetActivity) {
                            is WordIntroduction -> {
                                val word = lesson.vocabulary.firstOrNull {
                                    it.id == targetActivity.wordId
                                }
                                if (word != null) {
                                    WordIntroductionCard(
                                        wordItem = word,
                                        onPlayAudio = onPlayAudio,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }

                            is MultipleChoiceActivity -> {
                                MultipleChoiceCard(
                                    activity = targetActivity,
                                    selectedAnswer = state.selectedAnswer,
                                    answerState = state.answerState,
                                    onAnswerClick = viewModel::selectAnswer,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                            is ReverseChoiceActivity -> {
                                ReverseChoiceCard(
                                    activity = targetActivity,
                                    selectedAnswer = state.selectedAnswer,
                                    answerState = state.answerState,
                                    onAnswerClick = viewModel::selectAnswer,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                            is AudioChoiceActivity -> {
                                AudioChoiceCard(
                                    activity = targetActivity,
                                    selectedAnswer = state.selectedAnswer,
                                    answerState = state.answerState,
                                    onAnswerClick = viewModel::selectAnswer,
                                    onPlayAudio = onPlayAudio,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                            is FillInTheBlankActivity -> {
                                FillInTheBlankCard(
                                    activity = targetActivity,
                                    selectedAnswer = state.selectedAnswer,
                                    answerState = state.answerState,
                                    onAnswerClick = viewModel::selectAnswer,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                            is SentenceBuilderActivity -> {
                                SentenceBuilderCard(
                                    activity = targetActivity,
                                    selectedAnswer = state.selectedAnswer,
                                    answerState = state.answerState,
                                    onAnswerChange = viewModel::selectAnswer,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                            is MatchingActivity -> {
                                MatchingCard(
                                    activity = targetActivity,
                                    selectedAnswer = state.selectedAnswer,
                                    answerState = state.answerState,
                                    onAnswerChange = viewModel::selectAnswer,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                            else -> {}
                        }
                    }
                }

                // Trigger confetti explosion whenever answerState becomes CORRECT
                if (state.answerState == AnswerState.CORRECT) {
                    ConfettiEffect(
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}