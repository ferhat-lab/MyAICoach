package com.ferhat.myaicoach.feature.lesson.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ferhat.myaicoach.domain.lesson.MultipleChoiceActivity
import com.ferhat.myaicoach.feature.lesson.AnswerState

@Composable
fun MultipleChoiceCard(
    activity: MultipleChoiceActivity,
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
        // Tag Header
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f),
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            Text(
                text = activity.instruction,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
            )
        }

        // Target Prompt Display Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 28.dp, horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = activity.prompt,
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Options List
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            activity.options.forEach { option ->
                val isSelected = option == selectedAnswer

                val containerColor by animateColorAsState(
                    targetValue = when {
                        isSelected && answerState == AnswerState.CORRECT -> Color(0xFF14532D)
                        isSelected && answerState == AnswerState.INCORRECT -> Color(0xFF7F1D1D)
                        isSelected -> MaterialTheme.colorScheme.primaryContainer
                        else -> MaterialTheme.colorScheme.surface
                    },
                    animationSpec = spring(),
                    label = "optionBgColor"
                )

                val borderColor by animateColorAsState(
                    targetValue = when {
                        isSelected && answerState == AnswerState.CORRECT -> Color(0xFF22C55E)
                        isSelected && answerState == AnswerState.INCORRECT -> Color(0xFFEF4444)
                        isSelected -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                    },
                    label = "optionBorderColor"
                )

                val elevation by animateDpAsState(
                    targetValue = if (isSelected) 6.dp else 1.dp,
                    label = "optionElevation"
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = answerState == AnswerState.IDLE) {
                            onAnswerClick(option)
                        },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = containerColor),
                    border = BorderStroke(if (isSelected) 2.dp else 1.dp, borderColor),
                    elevation = CardDefaults.cardElevation(defaultElevation = elevation)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 18.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = option,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = when {
                                isSelected && answerState == AnswerState.CORRECT -> Color(0xFFDCFCE7)
                                isSelected && answerState == AnswerState.INCORRECT -> Color(0xFFFEE2E2)
                                isSelected -> MaterialTheme.colorScheme.onPrimaryContainer
                                else -> MaterialTheme.colorScheme.onSurface
                            }
                        )

                        if (isSelected && answerState == AnswerState.CORRECT) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Doğru",
                                tint = Color(0xFF4ADE80)
                            )
                        } else if (isSelected && answerState == AnswerState.INCORRECT) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Hatalı",
                                tint = Color(0xFFFCA5A5)
                            )
                        }
                    }
                }
            }
        }
    }
}
