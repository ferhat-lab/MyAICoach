package com.ferhat.myaicoach.feature.lesson.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.ferhat.myaicoach.domain.lesson.FillInTheBlankActivity
import com.ferhat.myaicoach.feature.lesson.AnswerState

@Composable
fun FillInTheBlankCard(
    activity: FillInTheBlankActivity,
    selectedAnswer: String?,
    answerState: AnswerState,
    onAnswerClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Clean Instruction Typography
        Text(
            text = activity.instruction,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Sentence Hero Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 28.dp, horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val filledSentence = buildAnnotatedString {
                    val sentence = activity.sentenceWithBlank
                    val blankMarker = "___"
                    val parts = sentence.split(blankMarker)

                    if (parts.size >= 2) {
                        append(parts[0])
                        withStyle(
                            style = SpanStyle(
                                color = when {
                                    selectedAnswer != null && answerState == AnswerState.CORRECT -> Color(0xFF4ADE80)
                                    selectedAnswer != null && answerState == AnswerState.INCORRECT -> Color(0xFFFCA5A5)
                                    selectedAnswer != null -> MaterialTheme.colorScheme.primary
                                    else -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                                },
                                fontWeight = FontWeight.Bold,
                                textDecoration = if (selectedAnswer != null && answerState == AnswerState.IDLE) {
                                    androidx.compose.ui.text.style.TextDecoration.Underline
                                } else {
                                    androidx.compose.ui.text.style.TextDecoration.None
                                }
                            )
                        ) {
                            append(selectedAnswer ?: " _____ ")
                        }
                        append(parts[1])
                    } else {
                        append(sentence)
                    }
                }

                Text(
                    text = filledSentence,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Option Cards
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            activity.options.forEach { option ->
                val isSelected = option == selectedAnswer

                LessonChoiceOption(
                    text = option,
                    isSelected = isSelected,
                    answerState = answerState,
                    onClick = { onAnswerClick(option) }
                )
            }
        }
    }
}
