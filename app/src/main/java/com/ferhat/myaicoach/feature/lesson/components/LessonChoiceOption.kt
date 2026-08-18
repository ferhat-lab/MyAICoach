package com.ferhat.myaicoach.feature.lesson.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ferhat.myaicoach.feature.lesson.AnswerState

@Composable
fun LessonChoiceOption(
    text: String,
    isSelected: Boolean,
    answerState: AnswerState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.98f else 1.0f,
        animationSpec = spring(stiffness = 500f),
        label = "optionPressScale"
    )

    val containerColor by animateColorAsState(
        targetValue = when {
            isSelected && answerState == AnswerState.CORRECT -> Color(0xFF14532D) // Koyu Zümrüt Yeşili
            isSelected && answerState == AnswerState.INCORRECT -> Color(0xFF7F1D1D) // Koyu Mercan Kırmızı
            isSelected -> MaterialTheme.colorScheme.primaryContainer
            else -> MaterialTheme.colorScheme.surface
        },
        animationSpec = spring(),
        label = "choiceOptionBg"
    )

    val borderColor by animateColorAsState(
        targetValue = when {
            isSelected && answerState == AnswerState.CORRECT -> Color(0xFF22C55E)
            isSelected && answerState == AnswerState.INCORRECT -> Color(0xFFEF4444)
            isSelected -> MaterialTheme.colorScheme.primary
            else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
        },
        label = "choiceOptionBorder"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = BorderStroke(if (isSelected) 2.dp else 1.dp, borderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .padding(horizontal = 20.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
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
