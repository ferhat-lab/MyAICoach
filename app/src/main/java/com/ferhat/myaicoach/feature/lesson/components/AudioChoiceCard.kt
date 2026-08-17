package com.ferhat.myaicoach.feature.lesson.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ferhat.myaicoach.domain.lesson.AudioChoiceActivity
import com.ferhat.myaicoach.feature.lesson.AnswerState

@Composable
fun AudioChoiceCard(
    activity: AudioChoiceActivity,
    selectedAnswer: String?,
    answerState: AnswerState,
    onAnswerClick: (String) -> Unit,
    onPlayAudio: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulseTransition")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.12f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tag Header
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.6f),
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(
                text = "DİNLEME EGZERSİZİ",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }

        Text(
            text = "Dinlediğin kelime hangisi?",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Big Animated Audio Player Circle
        Box(
            modifier = Modifier
                .scale(pulseScale)
                .size(96.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            com.ferhat.myaicoach.ui.theme.PrimaryDark
                        )
                    )
                )
                .clickable {
                    onPlayAudio(activity.audioText)
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                contentDescription = "Tekrar Dinle",
                tint = Color.White,
                modifier = Modifier.size(44.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.clickable {
                onPlayAudio(activity.audioText)
            }
        ) {
            Text(
                text = "🔊 Tekrar Dinle",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

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
                    label = "audioOptionBgColor"
                )

                val borderColor by animateColorAsState(
                    targetValue = when {
                        isSelected && answerState == AnswerState.CORRECT -> Color(0xFF22C55E)
                        isSelected && answerState == AnswerState.INCORRECT -> Color(0xFFEF4444)
                        isSelected -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                    },
                    label = "audioOptionBorderColor"
                )

                val elevation by animateDpAsState(
                    targetValue = if (isSelected) 6.dp else 1.dp,
                    label = "audioOptionElevation"
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
