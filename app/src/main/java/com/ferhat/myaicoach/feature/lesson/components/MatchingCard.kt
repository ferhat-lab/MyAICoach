package com.ferhat.myaicoach.feature.lesson.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ferhat.myaicoach.domain.lesson.MatchingActivity
import com.ferhat.myaicoach.feature.lesson.AnswerState

@Composable
fun MatchingCard(
    activity: MatchingActivity,
    selectedAnswer: String?,
    answerState: AnswerState,
    onAnswerChange: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    // English words list (shuffled once)
    val englishWords = remember(activity.id) { activity.pairs.keys.toList().shuffled() }
    // Turkish translations list (shuffled once)
    val turkishWords = remember(activity.id) { activity.pairs.values.toList().shuffled() }

    var selectedEnglish by remember(activity.id) { mutableStateOf<String?>(null) }
    var selectedTurkish by remember(activity.id) { mutableStateOf<String?>(null) }

    // Map of matched English word -> matched Turkish word
    val matchedPairs = remember(activity.id) { mutableStateMapOf<String, String>() }

    // Auto-pair when both selected
    LaunchedEffect(selectedEnglish, selectedTurkish) {
        val en = selectedEnglish
        val tr = selectedTurkish
        if (en != null && tr != null) {
            val correctTr = activity.pairs[en]
            if (correctTr == tr) {
                // Correct Match
                matchedPairs[en] = tr
            }
            // Reset selection after attempt
            selectedEnglish = null
            selectedTurkish = null
        }
    }

    // Notify parent state when all pairs matched
    LaunchedEffect(matchedPairs.size) {
        if (matchedPairs.size == activity.pairs.size && activity.pairs.isNotEmpty()) {
            onAnswerChange("COMPLETED")
        } else {
            onAnswerChange(null)
        }
    }

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

        Text(
            text = "Kelime Çiftlerini Eşleştir",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Two Columns: Left English, Right Turkish
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // English Column
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "İngilizce",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                englishWords.forEach { enWord ->
                    val isMatched = matchedPairs.containsKey(enWord)
                    val isSelected = selectedEnglish == enWord

                    MatchingOptionChip(
                        text = enWord,
                        isSelected = isSelected,
                        isMatched = isMatched,
                        enabled = !isMatched && answerState != AnswerState.CORRECT,
                        onClick = {
                            selectedEnglish = if (isSelected) null else enWord
                        }
                    )
                }
            }

            // Turkish Column
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Türkçe",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                turkishWords.forEach { trWord ->
                    val isMatched = matchedPairs.containsValue(trWord)
                    val isSelected = selectedTurkish == trWord

                    MatchingOptionChip(
                        text = trWord,
                        isSelected = isSelected,
                        isMatched = isMatched,
                        enabled = !isMatched && answerState != AnswerState.CORRECT,
                        onClick = {
                            selectedTurkish = if (isSelected) null else trWord
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun MatchingOptionChip(
    text: String,
    isSelected: Boolean,
    isMatched: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.97f else 1.0f,
        animationSpec = spring(stiffness = 500f),
        label = "matchingPressScale"
    )

    val containerColor by animateColorAsState(
        targetValue = when {
            isMatched -> Color(0xFF14532D)
            isSelected -> MaterialTheme.colorScheme.primaryContainer
            else -> MaterialTheme.colorScheme.surface
        },
        animationSpec = spring(),
        label = "matchBg"
    )

    val borderColor by animateColorAsState(
        targetValue = when {
            isMatched -> Color(0xFF22C55E)
            isSelected -> MaterialTheme.colorScheme.primary
            else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
        },
        label = "matchBorder"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick
            ),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = BorderStroke(if (isSelected || isMatched) 2.dp else 1.dp, borderColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 52.dp)
                .padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = if (isSelected || isMatched) FontWeight.Bold else FontWeight.Medium,
                color = when {
                    isMatched -> Color(0xFFDCFCE7)
                    isSelected -> MaterialTheme.colorScheme.onPrimaryContainer
                    else -> MaterialTheme.colorScheme.onSurface
                }
            )

            if (isMatched) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Eşleşti",
                    tint = Color(0xFF4ADE80)
                )
            }
        }
    }
}
