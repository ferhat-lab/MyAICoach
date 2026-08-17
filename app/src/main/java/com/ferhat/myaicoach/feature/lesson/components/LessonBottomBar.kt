package com.ferhat.myaicoach.feature.lesson.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ferhat.myaicoach.feature.lesson.AnswerState

@Composable
fun LessonBottomBar(
    answerState: AnswerState,
    selectedAnswer: String?,
    correctAnswer: String?,
    isIntroduction: Boolean,
    onCheckClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = when (answerState) {
            AnswerState.CORRECT -> Color(0xFF14532D) // Dark Emerald
            AnswerState.INCORRECT -> Color(0xFF7F1D1D) // Dark Red
            AnswerState.IDLE -> MaterialTheme.colorScheme.surface
        },
        label = "bottomBarBgColor"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .navigationBarsPadding()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        // Feedback message banner for Correct / Incorrect
        AnimatedVisibility(
            visible = answerState != AnswerState.IDLE,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (answerState == AnswerState.CORRECT) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Doğru",
                        tint = Color(0xFF4ADE80),
                        modifier = Modifier.size(32.dp)
                    )
                    Column {
                        Text(
                            text = "Harika! 🎉",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4ADE80)
                        )
                        Text(
                            text = "Doğru cevap verdin.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFFDCFCE7)
                        )
                    }
                } else if (answerState == AnswerState.INCORRECT) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Hatalı",
                        tint = Color(0xFFFCA5A5),
                        modifier = Modifier.size(32.dp)
                    )
                    Column {
                        Text(
                            text = "Tekrar Dene 💡",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFCA5A5)
                        )
                        if (correctAnswer != null) {
                            Text(
                                text = "Doğru cevap: $correctAnswer",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFFFEE2E2)
                            )
                        }
                    }
                }
            }
        }

        // Action Button
        val buttonText = when {
            isIntroduction -> "DEVAM ET"
            answerState == AnswerState.CORRECT -> "DEVAM ET"
            answerState == AnswerState.INCORRECT -> "ANLADIM"
            else -> "KONTROL ET"
        }

        val buttonContainerColor = when {
            answerState == AnswerState.CORRECT -> Color(0xFF22C55E)
            answerState == AnswerState.INCORRECT -> Color(0xFFEF4444)
            else -> MaterialTheme.colorScheme.primary
        }

        val isEnabled = isIntroduction || (selectedAnswer != null) || answerState != AnswerState.IDLE

        Button(
            onClick = {
                if (isIntroduction || answerState == AnswerState.CORRECT) {
                    onNextClick()
                } else if (answerState == AnswerState.INCORRECT) {
                    // Tapping "ANLADIM" moves forward or resets
                    onNextClick()
                } else {
                    onCheckClick()
                }
            },
            enabled = isEnabled,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonContainerColor,
                contentColor = Color.White,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = buttonText,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
