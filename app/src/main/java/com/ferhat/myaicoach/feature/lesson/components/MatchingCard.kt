package com.ferhat.myaicoach.feature.lesson.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.BorderStroke
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

// Sırasıyla eşleştirme çiftlerine atanacak canlı renk paleti
private val PairColors = listOf(
    Color(0xFF8B5CF6), // Canlı Mor
    Color(0xFF06B6D4), // Turkuaz Mavi
    Color(0xFFF59E0B), // Kehribar Turuncu
    Color(0xFFEC4899), // Pembe
    Color(0xFF10B981)  // Zümrüt Yeşili
)

/**
 * MatchingCard: Yeniden tasarlanmış interaktif kelime eşleştirme bileşeni.
 * - Soldan ve sağdan kelimelere dokunularak eşleştirme yapılır.
 * - Eşleşen her çift kendine özel canlı bir renk alır ve arkalarında kıvrımlı ip bağlantısı çizilir.
 * - Tıklanarak bağlantı kaldırılabilir veya değiştirilebilir.
 * - Tüm kelimeler eşleşince KONTROL ET butonu aktifleşir.
 * - Kontrol sonrasında doğru çiftler Yeşile (✓), yanlış çiftler Kırmızıya (✕) döner.
 */
@Composable
fun MatchingCard(
    activity: MatchingActivity,
    selectedAnswer: String?,
    answerState: AnswerState,
    onAnswerChange: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    // İngilizce ve Türkçe kelimelerin bir kez karıştırılması
    val englishWords = remember(activity.id) { activity.pairs.keys.toList().shuffled() }
    val turkishWords = remember(activity.id) { activity.pairs.values.toList().shuffled() }

    // Seçili İngilizce kelime
    var selectedEnglish by remember(activity.id) { mutableStateOf<String?>(null) }

    // Kullanıcının oluşturduğu eşleşme çiftleri listesi
    val userPairs = remember(activity.id) { mutableStateListOf<UserPair>() }

    // Kartların Canvas üzerindeki X-Y orta noktalarının koordinat haritası
    val enPositions = remember(activity.id) { mutableStateMapOf<String, Offset>() }
    val trPositions = remember(activity.id) { mutableStateMapOf<String, Offset>() }

    // Tüm kelimeler eşleştiğinde KONTROL ET butonunu aktifleştirme
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
        // Üst Başlık
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

        // Bağlantı Çizgileri + Izgara Kartları Kapsayıcısı
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            // ARKA PLAN: Eşleşme Çiftlerinin İp Bağlantı Çizgileri (Canvas)
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                userPairs.forEach { pair ->
                    val start = enPositions[pair.englishWord]
                    val end = trPositions[pair.turkishWord]

                    if (start != null && end != null) {
                        // Değerlendirme durumuna göre renk belirleme
                        val lineColor = when (answerState) {
                            AnswerState.CORRECT -> Color(0xFF22C55E) // Yeşil
                            AnswerState.INCORRECT -> {
                                val isCorrectPair = activity.pairs[pair.englishWord] == pair.turkishWord
                                if (isCorrectPair) Color(0xFF22C55E) else Color(0xFFEF4444)
                            }
                            else -> PairColors[pair.colorIndex % PairColors.size]
                        }

                        // Hafif kıvrımlı Bezier ip yolu
                        val controlX1 = start.x + (end.x - start.x) * 0.4f
                        val controlX2 = start.x + (end.x - start.x) * 0.6f

                        val path = Path().apply {
                            moveTo(start.x, start.y)
                            cubicTo(controlX1, start.y, controlX2, end.y, end.x, end.y)
                        }

                        // Kıvrımlı ip çizimi
                        drawPath(
                            path = path,
                            color = lineColor,
                            style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
                        )

                        // Uçlardaki küçük bağlantı noktaları (Bağlantı bilyeleri)
                        drawCircle(color = lineColor, radius = 5.dp.toPx(), center = start)
                        drawCircle(color = lineColor, radius = 5.dp.toPx(), center = end)
                    }
                }
            }

            // ÖN PLAN: İki Sütunlu Izgara (Sol: İngilizce, Sağ: Türkçe)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // İngilizce Sütunu
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
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

                        // Değerlendirme Durumu Hesaplama
                        val isCorrectPair = existingPair != null && activity.pairs[enWord] == existingPair.turkishWord
                        val isEvaluated = answerState != AnswerState.IDLE

                        MatchingOptionChip(
                            text = enWord,
                            isSelected = isSelected,
                            pairColor = existingPair?.let { PairColors[it.colorIndex % PairColors.size] },
                            isEvaluated = isEvaluated,
                            isCorrectPair = isCorrectPair,
                            enabled = answerState == AnswerState.IDLE,
                            onPositioned = { offset ->
                                enPositions[enWord] = offset
                            },
                            onClick = {
                                if (answerState != AnswerState.IDLE) return@MatchingOptionChip

                                if (existingPair != null) {
                                    // Zaten eşleşmişse bağlantıyı kaldır
                                    userPairs.remove(existingPair)
                                    selectedEnglish = null
                                } else {
                                    // Seçimi güncelle
                                    selectedEnglish = if (isSelected) null else enWord
                                }
                            }
                        )
                    }
                }

                // Türkçe Sütunu
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
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

                        // Değerlendirme Durumu Hesaplama
                        val isCorrectPair = existingPair != null && activity.pairs[existingPair.englishWord] == trWord
                        val isEvaluated = answerState != AnswerState.IDLE

                        MatchingOptionChip(
                            text = trWord,
                            isSelected = false,
                            pairColor = existingPair?.let { PairColors[it.colorIndex % PairColors.size] },
                            isEvaluated = isEvaluated,
                            isCorrectPair = isCorrectPair,
                            enabled = answerState == AnswerState.IDLE,
                            onPositioned = { offset ->
                                trPositions[trWord] = offset
                            },
                            onClick = {
                                if (answerState != AnswerState.IDLE) return@MatchingOptionChip

                                val currentEn = selectedEnglish
                                if (existingPair != null) {
                                    // Zaten eşleşmişse bağlantıyı kaldır
                                    userPairs.remove(existingPair)
                                } else if (currentEn != null) {
                                    // Yeni Çift Oluştur ve Ekle
                                    val newColorIndex = userPairs.size
                                    userPairs.add(
                                        UserPair(
                                            englishWord = currentEn,
                                            turkishWord = trWord,
                                            colorIndex = newColorIndex
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
 * MatchingOptionChip: Özelleştirilmiş Eşleştirme Kartı.
 * - Sağ/Sol kenarlarından bağlantı ipi koordinatını hesaplar.
 * - Atanan özel çifte göre renk alır.
 * - Değerlendirme sonrasında Doğru (✓) veya Yanlış (✕) rozeti gösterir.
 */
@Composable
private fun MatchingOptionChip(
    text: String,
    isSelected: Boolean,
    pairColor: Color?,
    isEvaluated: Boolean,
    isCorrectPair: Boolean,
    enabled: Boolean,
    onPositioned: (Offset) -> Unit,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Basılma mikro ölçekleme animasyonu
    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.97f else 1.0f,
        animationSpec = spring(stiffness = 500f),
        label = "matchingPressScale"
    )

    // Arka plan renginin duruma göre geçişi
    val containerColor by animateColorAsState(
        targetValue = when {
            isEvaluated && isCorrectPair -> Color(0xFF14532D) // Koyu Yeşil (Doğru)
            isEvaluated && !isCorrectPair && pairColor != null -> Color(0xFF7F1D1D) // Koyu Kırmızı (Yanlış)
            pairColor != null -> pairColor.copy(alpha = 0.25f) // Atanmış Çift Rengi (Şeffaf)
            isSelected -> MaterialTheme.colorScheme.primaryContainer // Mor Vurgu (Seçili)
            else -> MaterialTheme.colorScheme.surface
        },
        animationSpec = spring(),
        label = "matchBg"
    )

    // Kenarlık rengi geçişi
    val borderColor by animateColorAsState(
        targetValue = when {
            isEvaluated && isCorrectPair -> Color(0xFF22C55E) // Canlı Yeşil
            isEvaluated && !isCorrectPair && pairColor != null -> Color(0xFFEF4444) // Canlı Kırmızı
            pairColor != null -> pairColor // Atanmış Çift Rengi
            isSelected -> MaterialTheme.colorScheme.primary // Mor Kenarlık
            else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
        },
        label = "matchBorder"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .onGloballyPositioned { coordinates ->
                // Kartın orta noktasını Canvas koordinatı için iletme
                val positionInParent = coordinates.positionInParent()
                val size = coordinates.size
                val centerOffset = Offset(
                    x = positionInParent.x + (size.width / 2f),
                    y = positionInParent.y + (size.height / 2f)
                )
                onPositioned(centerOffset)
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick
            ),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = BorderStroke(if (isSelected || pairColor != null || isEvaluated) 2.dp else 1.dp, borderColor)
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
                fontWeight = if (isSelected || pairColor != null || isEvaluated) FontWeight.Bold else FontWeight.Medium,
                color = when {
                    isEvaluated && isCorrectPair -> Color(0xFFDCFCE7)
                    isEvaluated && !isCorrectPair && pairColor != null -> Color(0xFFFCA5A5)
                    pairColor != null -> pairColor
                    isSelected -> MaterialTheme.colorScheme.onPrimaryContainer
                    else -> MaterialTheme.colorScheme.onSurface
                }
            )

            // Değerlendirme Simge Gösterimi
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
