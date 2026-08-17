package com.ferhat.myaicoach.feature.lesson

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ferhat.myaicoach.domain.lesson.MultipleChoiceActivity
import com.ferhat.myaicoach.domain.lesson.WordIntroduction
import com.ferhat.myaicoach.domain.lesson.AudioChoiceActivity

@Composable
fun LessonScreen(
    viewModel: LessonViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val lesson = state.lesson ?: return

    val activity = state.activities
        .getOrNull(state.currentActivityIndex)
        ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 24.dp,
                vertical = 32.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = lesson.title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(32.dp))

        when (activity) {

            is WordIntroduction -> {
                val word = lesson.vocabulary
                    .firstOrNull {
                        it.id == activity.wordId
                    }
                    ?: return

                WordIntroductionContent(
                    word = word.word,
                    translation = word.translation,
                    pronunciation = word.pronunciation,
                    exampleSentence = word.exampleSentence,
                    exampleTranslation = word.exampleTranslation,
                    onNextClick = viewModel::nextActivity
                )
            }

            is MultipleChoiceActivity -> {
                MultipleChoiceContent(
                    activity = activity,
                    selectedAnswer = state.selectedAnswer,
                    answerState = state.answerState,
                    onAnswerClick = viewModel::selectAnswer,
                    onNextClick = viewModel::nextActivity
                )
            }

            is AudioChoiceActivity -> {
                AudioChoiceContent(
                    activity = activity,
                    selectedAnswer = state.selectedAnswer,
                    answerState = state.answerState,
                    onAnswerClick = viewModel::selectAnswer,
                    onNextClick = viewModel::nextActivity
                )
            }
        }
    }
}

@Composable
private fun WordIntroductionContent(
    word: String,
    translation: String,
    pronunciation: String?,
    exampleSentence: String,
    exampleTranslation: String,
    onNextClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = word,
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold
        )

        pronunciation?.let {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = it,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = translation,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = exampleSentence,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = exampleTranslation,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = onNextClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Devam Et")
        }
    }
}

@Composable
private fun MultipleChoiceContent(
    activity: MultipleChoiceActivity,
    selectedAnswer: String?,
    answerState: AnswerState,
    onAnswerClick: (String) -> Unit,
    onNextClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = activity.instruction,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = activity.prompt,
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        activity.options.forEach { option ->

            Button(
                onClick = {
                    onAnswerClick(option)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {
                Text(option)
            }
        }

        if (selectedAnswer != null) {

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = when (answerState) {
                    AnswerState.CORRECT -> "Doğru ✓"
                    AnswerState.INCORRECT -> "Tekrar dene"
                    AnswerState.IDLE -> ""
                },
                color = when (answerState) {
                    AnswerState.CORRECT ->
                        MaterialTheme.colorScheme.secondary

                    AnswerState.INCORRECT ->
                        MaterialTheme.colorScheme.error

                    AnswerState.IDLE ->
                        MaterialTheme.colorScheme.onSurface
                }
            )

            if (answerState == AnswerState.CORRECT) {

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onNextClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Devam Et")
                }
            }
        }
    }
}
@Composable
private fun AudioChoiceContent(
    activity: AudioChoiceActivity,
    selectedAnswer: String?,
    answerState: AnswerState,
    onAnswerClick: (String) -> Unit,
    onNextClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "🎧",
            style = MaterialTheme.typography.displayLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Dinlediğin kelime hangisi?",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // Daha sonra gerçek TTS bağlanacak.
            }
        ) {
            Text("🔊 Tekrar Dinle")
        }

        Spacer(modifier = Modifier.height(32.dp))

        activity.options.forEach { option ->

            Button(
                onClick = {
                    onAnswerClick(option)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {
                Text(option)
            }
        }

        if (selectedAnswer != null) {

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = when (answerState) {
                    AnswerState.CORRECT -> "Doğru ✓"
                    AnswerState.INCORRECT -> "Tekrar dene"
                    AnswerState.IDLE -> ""
                },
                color = when (answerState) {
                    AnswerState.CORRECT ->
                        MaterialTheme.colorScheme.secondary

                    AnswerState.INCORRECT ->
                        MaterialTheme.colorScheme.error

                    AnswerState.IDLE ->
                        MaterialTheme.colorScheme.onSurface
                }
            )

            if (answerState == AnswerState.CORRECT) {

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onNextClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Devam Et")
                }
            }
        }
    }
}