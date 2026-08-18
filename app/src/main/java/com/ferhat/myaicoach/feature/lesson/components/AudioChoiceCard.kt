package com.ferhat.myaicoach.feature.lesson.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.ferhat.myaicoach.domain.lesson.AudioChoiceActivity
import com.ferhat.myaicoach.feature.lesson.AnswerState

/**
 * AudioChoiceCard: Dinleme egzersizlerinde dairesel genişleyen pulse halkaları,
 * büyük ses ikonu ve seçenek kartları sunan Compose bileşeni.
 */
@Composable
fun AudioChoiceCard(
    activity: AudioChoiceActivity,
    selectedAnswer: String?,
    answerState: AnswerState,
    onAnswerClick: (String) -> Unit,
    onPlayAudio: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    // Ses butonuna dokunulma durumunu takip eden etkileşim nesnesi
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Sürekli genişleyip küçülen canlı pulse (nabız) animasyonu
    val infiniteTransition = rememberInfiniteTransition(label = "pulseTransition")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    // Dışarıya yayılan şeffaf ses halkası animasyonu
    val ringScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ringScale"
    )

    // Tıklanma anındaki basılma hissiyatı (0.94f ölçekleme)
    val pressScale by animateFloatAsState(
        targetValue = if (isPressed) 0.94f else 1.0f,
        animationSpec = spring(stiffness = 500f),
        label = "audioPressScale"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Üst Başlık: "DİNLEME EGZERSİZİ" Etiketi
        Text(
            text = "DİNLEME EGZERSİZİ",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Soru İfadesi
        Text(
            text = "Dinlediğin kelime hangisi?",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Dış Katmanda Şeffaf Dalga Halkası & İçte Ana Ses Butonu
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(120.dp)
        ) {
            // Dış Şeffaf Nabız Halkası
            Box(
                modifier = Modifier
                    .scale(ringScale)
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.25f))
            )

            // İç Dairesel Ana Ses Butonu (Purple Radial Gradient)
            Box(
                modifier = Modifier
                    .scale(pulseScale * pressScale)
                    .size(92.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                com.ferhat.myaicoach.ui.theme.PrimaryDark
                            )
                        )
                    )
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        // UI katmanından TTS ses tetikleyici callback yayını
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
        }

        Spacer(modifier = Modifier.height(12.dp))

        // "Tekrar Dinle" Yardımcı Rozeti
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
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

        Spacer(modifier = Modifier.height(28.dp))

        // Seçenekler Listesi (LessonChoiceOption ile mikro animasyonlu şıklar)
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
