package com.ferhat.myaicoach.feature.lesson.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ferhat.myaicoach.domain.lesson.SentenceBuilderActivity
import com.ferhat.myaicoach.feature.lesson.AnswerState

/**
 * SentenceBuilderCard: Cümle oluşturma aktivitesi bileşeni.
 * Kullanıcı alt kelime bankasından çiplere dokunarak üst cümleyi kurar.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SentenceBuilderCard(
    activity: SentenceBuilderActivity,
    selectedAnswer: String?,
    answerState: AnswerState,
    onAnswerChange: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    // Üst tray alanına yerleştirilen kelime çiplerinin listesi
    val placedChips = remember(activity.id) { mutableStateListOf<String>() }

    // Çip değişikliğini ebeveyn ViewModel'in selectedAnswer stringine senkronize etme
    LaunchedEffect(placedChips.toList()) {
        val currentString = if (placedChips.isEmpty()) null else placedChips.joinToString(" ")
        if (currentString != selectedAnswer) {
            onAnswerChange(currentString)
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Yönerge Başlığı
        Text(
            text = activity.instruction,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Hedef Türkçe Cümle Kartı
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 18.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = activity.promptTranslation,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Cümle Oluşturma Yerleştirme Alanı (Dinamik Yükseklik Animasyonlu)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 72.dp)
                .animateContentSize(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = when (answerState) {
                    AnswerState.CORRECT -> Color(0xFF14532D) // Koyu Yeşil (Doğru)
                    AnswerState.INCORRECT -> Color(0xFF7F1D1D) // Koyu Kırmızı (Yanlış)
                    AnswerState.IDLE -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                }
            ),
            border = BorderStroke(
                1.dp,
                when (answerState) {
                    AnswerState.CORRECT -> Color(0xFF22C55E)
                    AnswerState.INCORRECT -> Color(0xFFEF4444)
                    AnswerState.IDLE -> MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                }
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                if (placedChips.isEmpty()) {
                    Text(
                        text = "Kelime çiplerine dokunarak cümleyi oluştur...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                } else {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        placedChips.forEachIndexed { index, chip ->
                            SentenceChip(
                                text = chip,
                                isPlaced = true,
                                enabled = answerState != AnswerState.CORRECT,
                                onClick = { placedChips.removeAt(index) }
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Kelime Bankası Başlığı
        Text(
            text = "Kelime Bankası",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        // Kelime Bankası Çipleri
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            activity.wordChips.forEachIndexed { chipIndex, chip ->
                val alreadyPlacedCount = placedChips.count { it == chip }
                val occurrencesBeforeThis = activity.wordChips.take(chipIndex).count { it == chip }
                val isAvailable = occurrencesBeforeThis >= alreadyPlacedCount

                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    SentenceChip(
                        text = chip,
                        isPlaced = false,
                        isAvailable = isAvailable,
                        enabled = isAvailable && answerState != AnswerState.CORRECT,
                        onClick = { placedChips.add(chip) }
                    )
                }
            }
        }
    }
}

/**
 * SentenceChip: Cümle çipi bileşeni. Dokunma mikro ölçekleme animasyonu içerir.
 */
@Composable
private fun SentenceChip(
    text: String,
    isPlaced: Boolean,
    isAvailable: Boolean = true,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.95f else 1.0f,
        animationSpec = spring(stiffness = 500f),
        label = "chipPressScale"
    )

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = when {
            isPlaced -> MaterialTheme.colorScheme.primaryContainer
            isAvailable -> MaterialTheme.colorScheme.surface
            else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)
        },
        border = BorderStroke(
            1.dp,
            when {
                isPlaced -> MaterialTheme.colorScheme.primary
                isAvailable -> MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
                else -> Color.Transparent
            }
        ),
        modifier = Modifier
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick
            )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = when {
                isPlaced -> MaterialTheme.colorScheme.onPrimaryContainer
                isAvailable -> MaterialTheme.colorScheme.onSurface
                else -> Color.Transparent
            },
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
        )
    }
}
