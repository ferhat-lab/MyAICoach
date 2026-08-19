package com.ferhat.myaicoach.feature.lesson.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ferhat.myaicoach.domain.lesson.MatchingActivity
import com.ferhat.myaicoach.feature.lesson.AnswerState

/**
 * Kullanıcı Eşleşme Çifti Modeli
 */
private data class UserPair(
    val englishWord: String,
    val turkishWord: String,
    val colorIndex: Int
)

// Her aktif çift için 5 adet tamamen benzersiz renk paleti
private val PairColors = listOf(
    Color(0xFFA855F7), // 0: Canlı Mor
    Color(0xFF0284C7), // 1: Canlı Mavi
    Color(0xFFEA580C), // 2: Canlı Turuncu
    Color(0xFFDB2777), // 3: Canlı Pembe
    Color(0xFF16A34A)  // 4: Canlı Yeşil
)

/**
 * MatchingCard: İptal ve yeniden seçim durumlarında renk çakışmasını engelleyen benzersiz renk atamalı eşleştirme bileşeni.
 */
@Composable
fun MatchingCard(
    activity: MatchingActivity,
    selectedAnswer: String?,
    answerState: AnswerState,
    onAnswerChange: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    // İngilizce ve Türkçe kelimelerin sabit karıştırılması
    val englishWords = remember(activity.id) { activity.pairs.keys.toList().shuffled() }
    val turkishWords = remember(activity.id) { activity.pairs.values.toList().shuffled() }

    // Seçili İngilizce kelime
    var selectedEnglish by remember(activity.id) { mutableStateOf<String?>(null) }

    // Kullanıcının eşleştirdiği çiftler
    val userPairs = remember(activity.id) { mutableStateListOf<UserPair>() }

    // Ana Box koordinatı ve kartların soket noktası koordinatları
    var parentCoordinates by remember(activity.id) { mutableStateOf<LayoutCoordinates?>(null) }
    val enPositions = remember(activity.id) { mutableStateMapOf<String, Offset>() }
    val trPositions = remember(activity.id) { mutableStateMapOf<String, Offset>() }

    // Tüm kelimeler eşleştiğinde KONTROL ET butonunu aktif yapma
    LaunchedEffect(userPairs.size) {
        if (userPairs.size == activity.pairs.size && activity.pairs.isNotEmpty()) {
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
        // Üst Açıklama Başlıkları
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

        Spacer(modifier = Modifier.height(20.dp))

        // Ana Izgara & Çizgi Kapsayıcı Kutusu
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { parentCoordinates = it }
        ) {
            // ARKA PLAN CANVAS: Kartların kenar soketleri arasındaki yatay bağlantı çizgileri
            Canvas(
                modifier = Modifier.matchParentSize()
            ) {
                userPairs.forEach { pair ->
                    val start = enPositions[pair.englishWord]
                    val end = trPositions[pair.turkishWord]

                    if (start != null && end != null) {
                        // Değerlendirme durumuna göre çizgi ve düğüm rengi
                        val lineColor = when (answerState) {
                            AnswerState.CORRECT -> Color(0xFF22C55E) // Yeşil
                            AnswerState.INCORRECT -> {
                                val isCorrect = activity.pairs[pair.englishWord] == pair.turkishWord
                                if (isCorrect) Color(0xFF22C55E) else Color(0xFFEF4444)
                            }
                            else -> PairColors[pair.colorIndex % PairColors.size]
                        }

                        // Hafif esnek kıvrımlı Bezier yolu
                        val controlX1 = start.x + (end.x - start.x) * 0.4f
                        val controlX2 = start.x + (end.x - start.x) * 0.6f

                        val path = Path().apply {
                            moveTo(start.x, start.y)
                            cubicTo(controlX1, start.y, controlX2, end.y, end.x, end.y)
                        }

                        // Yatay Bağlantı Çizgisi Çizimi
                        drawPath(
                            path = path,
                            color = lineColor,
                            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                        )

                        // Sol Kartın Sağ Kenar Soket Düğümü (Halka + İç Dolgu)
                        drawCircle(color = lineColor, radius = 6.5.dp.toPx(), center = start)
                        drawCircle(color = Color(0xFF0F172A), radius = 3.5.dp.toPx(), center = start)

                        // Sağ Kartın Sol Kenar Soket Düğümü (Halka + İç Dolgu)
                        drawCircle(color = lineColor, radius = 6.5.dp.toPx(), center = end)
                        drawCircle(color = Color(0xFF0F172A), radius = 3.5.dp.toPx(), center = end)
                    }
                }
            }

            // ÖN PLAN: İki Sütunlu Kartlar (Aralarında 44.dp net boşluk)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(44.dp)
            ) {
                // İngilizce Sütunu (Sol)
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "İngilizce",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    englishWords.forEach { enWord ->
                        val existingPair = userPairs.firstOrNull { it.englishWord == enWord }
                        val isSelected = selectedEnglish == enWord

                        val isCorrectPair = existingPair != null && activity.pairs[enWord] == existingPair.turkishWord
                        val isEvaluated = answerState != AnswerState.IDLE

                        MatchingOptionChip(
                            text = enWord,
                            isSelected = isSelected,
                            pairColor = existingPair?.let { PairColors[it.colorIndex % PairColors.size] },
                            isEvaluated = isEvaluated,
                            isCorrectPair = isCorrectPair,
                            enabled = answerState == AnswerState.IDLE,
                            onPositioned = { coords ->
                                val parent = parentCoordinates
                                if (parent != null && parent.isAttached) {
                                    val localPos = parent.localPositionOf(coords, Offset.Zero)
                                    enPositions[enWord] = Offset(
                                        x = localPos.x + coords.size.width,
                                        y = localPos.y + (coords.size.height / 2f)
                                    )
                                }
                            },
                            onClick = {
                                if (answerState != AnswerState.IDLE) return@MatchingOptionChip

                                if (existingPair != null) {
                                    userPairs.remove(existingPair)
                                    selectedEnglish = null
                                } else {
                                    selectedEnglish = if (isSelected) null else enWord
                                }
                            }
                        )
                    }
                }

                // Türkçe Sütunu (Sağ)
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Türkçe",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    turkishWords.forEach { trWord ->
                        val existingPair = userPairs.firstOrNull { it.turkishWord == trWord }

                        val isCorrectPair = existingPair != null && activity.pairs[existingPair.englishWord] == trWord
                        val isEvaluated = answerState != AnswerState.IDLE

                        MatchingOptionChip(
                            text = trWord,
                            isSelected = false,
                            pairColor = existingPair?.let { PairColors[it.colorIndex % PairColors.size] },
                            isEvaluated = isEvaluated,
                            isCorrectPair = isCorrectPair,
                            enabled = answerState == AnswerState.IDLE,
                            onPositioned = { coords ->
                                val parent = parentCoordinates
                                if (parent != null && parent.isAttached) {
                                    val localPos = parent.localPositionOf(coords, Offset.Zero)
                                    trPositions[trWord] = Offset(
                                        x = localPos.x,
                                        y = localPos.y + (coords.size.height / 2f)
                                    )
                                }
                            },
                            onClick = {
                                if (answerState != AnswerState.IDLE) return@MatchingOptionChip

                                val currentEn = selectedEnglish
                                if (existingPair != null) {
                                    userPairs.remove(existingPair)
                                } else if (currentEn != null) {
                                    // RENK ÇAKIŞMASINI ÖNLEYEN MANTIK: Halihazırda kullanılmayan İLK BENZERSİZ renk indeksini bul
                                    val usedIndices = userPairs.map { it.colorIndex }.toSet()
                                    val firstAvailableColorIndex = (0 until PairColors.size).firstOrNull { it !in usedIndices } ?: userPairs.size

                                    userPairs.add(
                                        UserPair(
                                            englishWord = currentEn,
                                            turkishWord = trWord,
                                            colorIndex = firstAvailableColorIndex
                                        )
                                    )
                                    selectedEnglish = null
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

/**
 * MatchingOptionChip: Eşleştirme Kartı.
 */
@Composable
private fun MatchingOptionChip(
    text: String,
    isSelected: Boolean,
    pairColor: Color?,
    isEvaluated: Boolean,
    isCorrectPair: Boolean,
    enabled: Boolean,
    onPositioned: (LayoutCoordinates) -> Unit,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.97f else 1.0f,
        animationSpec = spring(stiffness = 500f),
        label = "matchingPressScale"
    )

    // Arka plan rengi (Görsel 2 stili)
    val containerColor by animateColorAsState(
        targetValue = when {
            isEvaluated && isCorrectPair -> Color(0xFF14532D) // Koyu Yeşil (Doğru)
            isEvaluated && !isCorrectPair && pairColor != null -> Color(0xFF7F1D1D) // Koyu Kırmızı (Yanlış)
            pairColor != null -> pairColor.copy(alpha = 0.15f) // Atanmış Çift Rengi Arka Plan Tint
            isSelected -> MaterialTheme.colorScheme.primaryContainer // Mor Vurgu (Seçili)
            else -> MaterialTheme.colorScheme.surface
        },
        animationSpec = spring(),
        label = "matchBg"
    )

    // Kenarlık rengi
    val borderColor by animateColorAsState(
        targetValue = when {
            isEvaluated && isCorrectPair -> Color(0xFF22C55E) // Canlı Yeşil
            isEvaluated && !isCorrectPair && pairColor != null -> Color(0xFFEF4444) // Canlı Kırmızı
            pairColor != null -> pairColor // Atanmış Benzersiz Çift Kenarlık Rengi
            isSelected -> MaterialTheme.colorScheme.primary // Mor Kenarlık
            else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
        },
        label = "matchBorder"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .onGloballyPositioned { coordinates ->
                onPositioned(coordinates)
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = BorderStroke(if (isSelected || pairColor != null || isEvaluated) 2.dp else 1.dp, borderColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = if (isSelected || pairColor != null || isEvaluated) FontWeight.Bold else FontWeight.Medium,
                color = when {
                    isEvaluated && isCorrectPair -> Color(0xFFDCFCE7)
                    isEvaluated && !isCorrectPair && pairColor != null -> Color(0xFFFCA5A5)
                    pairColor != null -> Color.White
                    isSelected -> MaterialTheme.colorScheme.onPrimaryContainer
                    else -> MaterialTheme.colorScheme.onSurface
                }
            )

            // Değerlendirme Sonu Simge Gösterimi (✓ veya ✕)
            if (isEvaluated && pairColor != null) {
                if (isCorrectPair) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Doğru",
                        tint = Color(0xFF4ADE80)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Yanlış",
                        tint = Color(0xFFEF4444)
                    )
                }
            }
        }
    }
}
