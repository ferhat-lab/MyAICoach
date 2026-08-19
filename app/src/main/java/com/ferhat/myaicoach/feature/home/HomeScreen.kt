package com.ferhat.myaicoach.feature.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * HomeScreen: Yeniden tasarlanmış modern Ana Sayfa Dashboard.
 * - Vani AI Kedi Maskotu karşılama kartı ve canlı konuşma balonu
 * - Günlük Hedef dairesel ilerleme simgesi (%80)
 * - 7 Günlük Seri, XP ve Coin rozetleri
 * - Bağlantılı dikey zaman çizelgeli öğrenme yolu
 */
@Composable
fun HomeScreen(
    onLessonClick: (LessonStage) -> Unit
) {
    val nickname = "Ferhat"
    val streakDays = 7
    val xp = 1240
    val coins = 320
    val dailyProgress = 0.80f // %80 Günlük Hedef

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        // Üst Kullanıcı Karşılama Başlığı
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Merhaba, $nickname 👋",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Bugünkü İngilizce yolculuğuna devam edelim.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 🐱 VANI AI KEDİ MASKOTU HERO KARŞILAMA KARTI
        VaniHeroBanner(
            onSpeakClick = { onLessonClick(LessonStage.SPEAK) }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Rozetler & Günlük Hedef Göstergesi
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Günlük Hedef Dairesel İlerleme Kartı
            DailyGoalCard(
                progress = dailyProgress,
                modifier = Modifier.weight(1.2f)
            )

            // İstatistik Rozetleri (Seri & XP)
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatBadgeCard(
                    icon = "🔥",
                    value = "$streakDays Gün",
                    label = "Seri",
                    accentColor = Color(0xFFEC4899)
                )
                StatBadgeCard(
                    icon = "⭐",
                    value = "$xp",
                    label = "XP Puanı",
                    accentColor = Color(0xFFF59E0B)
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Öğrenme Yolu Başlığı
        Text(
            text = "Öğrenme Yolu",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Bağlantılı Zaman Çizelgesi Öğrenme Yolu
        LearningPathTimeline(
            onLessonClick = onLessonClick
        )
    }
}

/**
 * VaniHeroBanner: AI Kedi Maskotumuz Vani'yi öne çıkaran etkileşimli Hero kartı.
 */
@Composable
private fun VaniHeroBanner(
    onSpeakClick: () -> Unit
) {
    // Maskot etrafındaki hafif canlı ışık animasyonu
    val infiniteTransition = rememberInfiniteTransition(label = "vaniGlow")
    val glowScale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(1400),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowScale"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E293B)
        ),
        border = BorderStroke(
            width = 1.5.dp,
            brush = Brush.horizontalGradient(
                colors = listOf(Color(0xFF7C3AED), Color(0xFF38BDF8))
            )
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Vani Kedi Avatar Rozeti
                Box(
                    modifier = Modifier.size(68.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Dış parlayan halka
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(glowScale)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(
                                        Color(0xFF7C3AED).copy(alpha = 0.6f),
                                        Color.Transparent
                                    )
                                )
                            )
                    )

                    // Avatar İkon Dairesi (Heterokromi Kedi Maskotu Temsili)
                    Surface(
                        modifier = Modifier.size(58.dp),
                        shape = CircleShape,
                        color = Color(0xFF0F172A),
                        border = BorderStroke(2.dp, Color(0xFF38BDF8))
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "🐱",
                                fontSize = 32.sp
                            )
                        }
                    }
                }

                // Vani Konuşma Balonu
                Column(modifier = Modifier.weight(1f)) {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Color(0xFF0F172A),
                        border = BorderStroke(1.dp, Color(0xFF7C3AED).copy(alpha = 0.4f))
                    ) {
                        Text(
                            text = "\"Bugün 5 dakika İngilizce konuşmaya hazır mısın?\"",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Vani ile Konuş CTA Butonu
            Button(
                onClick = onSpeakClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF7C3AED)
                )
            ) {
                Text(
                    text = "🎙️ Vani ile Konuşmaya Başla",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

/**
 * DailyGoalCard: Dairesel Hedef Göstergesi Kartı (%80)
 */
@Composable
private fun DailyGoalCard(
    progress: Float,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.08f))
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(54.dp)
            ) {
                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF38BDF8),
                    trackColor = Color(0xFF0F172A),
                    strokeWidth = 6.dp
                )
                Text(
                    text = "%${(progress * 100).toInt()}",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Column {
                Text(
                    text = "Günlük Hedef",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Kalan: 5 Dakika",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * StatBadgeCard: Seri ve XP için Özelleştirilmiş Rozet Kartı
 */
@Composable
private fun StatBadgeCard(
    icon: String,
    value: String,
    label: String,
    accentColor: Color
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = Color(0xFF1E293B),
        border = BorderStroke(1.dp, accentColor.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = icon, fontSize = 18.sp)
            Column {
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * LearningPathTimeline: Dikey zaman çizelgeli adım adım ders yolu
 */
@Composable
private fun LearningPathTimeline(
    onLessonClick: (LessonStage) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TimelineStepItem(
            stepNumber = "1",
            title = "Kelime Bilgisi",
            subtitle = "A1 Başlangıç Seviye Kelimeler",
            status = LessonStageStatus.COMPLETED,
            onClick = { }
        )

        TimelineStepItem(
            stepNumber = "2",
            title = "Kelime Eşleştirme Pratiği",
            subtitle = "Öğrenilen Kelimeleri Peş Peşe Eşleştir",
            status = LessonStageStatus.CURRENT,
            onClick = { onLessonClick(LessonStage.PRACTICE) }
        )

        TimelineStepItem(
            stepNumber = "3",
            title = "Dinleme & Anlama",
            subtitle = "Cümle Yapılarını Dinleyerek Kavra",
            status = LessonStageStatus.LOCKED,
            onClick = { }
        )

        TimelineStepItem(
            stepNumber = "4",
            title = "Vani ile Canlı Senaryo",
            subtitle = "Sohbet Odasında Gerçek Zamanlı Pratik",
            status = LessonStageStatus.LOCKED,
            onClick = { }
        )
    }
}

/**
 * TimelineStepItem: Zaman Çizelgesi Adım Kartı
 */
@Composable
private fun TimelineStepItem(
    stepNumber: String,
    title: String,
    subtitle: String,
    status: LessonStageStatus,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed && status == LessonStageStatus.CURRENT) 0.98f else 1.0f,
        animationSpec = spring(),
        label = "stepScale"
    )

    val (badgeBg, badgeBorder, badgeText) = when (status) {
        LessonStageStatus.COMPLETED -> Triple(Color(0xFF14532D), Color(0xFF22C55E), "✓")
        LessonStageStatus.CURRENT -> Triple(Color(0xFF7C3AED), Color(0xFF38BDF8), stepNumber)
        LessonStageStatus.LOCKED -> Triple(Color(0xFF0F172A), Color.Gray.copy(alpha = 0.3f), "🔒")
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = status == LessonStageStatus.CURRENT,
                onClick = onClick
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (status == LessonStageStatus.CURRENT) Color(0xFF1E293B) else Color(0xFF0F172A)
        ),
        border = BorderStroke(
            width = if (status == LessonStageStatus.CURRENT) 2.dp else 1.dp,
            color = if (status == LessonStageStatus.CURRENT) Color(0xFF7C3AED) else Color.White.copy(alpha = 0.08f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Adım Numarası / Kilit Dairesi
            Surface(
                modifier = Modifier.size(42.dp),
                shape = CircleShape,
                color = badgeBg,
                border = BorderStroke(1.5.dp, badgeBorder)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = badgeText,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (status == LessonStageStatus.LOCKED) Color.Gray else Color.White
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (status == LessonStageStatus.CURRENT) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Başla",
                    tint = Color(0xFF38BDF8)
                )
            }
        }
    }
}