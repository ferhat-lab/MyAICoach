package com.ferhat.myaicoach.feature.lesson.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * LessonTopBar: Ders akışındaki üst bilgi çubuğu.
 * İlerleme çubuğu (LinearProgressIndicator) ve canlı kalp rozeti barındırır.
 */
@Composable
fun LessonTopBar(
    currentIndex: Int,
    totalCount: Int,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Tamamlanan adım oranının 0.0f ile 1.0f arasında hesaplanması
    val progressRatio = if (totalCount > 0) {
        ((currentIndex + 1).toFloat() / totalCount.toFloat()).coerceIn(0f, 1f)
    } else {
        0f
    }

    // İlerleme çubuğunun adımlar değiştikçe yumuşakça akmasını sağlayan animasyon
    val animatedProgress by animateFloatAsState(
        targetValue = progressRatio,
        animationSpec = spring(stiffness = 300f),
        label = "lessonProgressAnimation"
    )

    // Kalp simgesi için hafif canlı atan kalp (pulse) animasyonu
    val infiniteTransition = rememberInfiniteTransition(label = "heartPulse")
    val heartScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "heartScale"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Çıkış Kapatma Butonu
        IconButton(
            onClick = onCloseClick,
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Dersden Çık",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Tek Parça Kesintisiz Akıcı İlerleme Çubuğu
        LinearProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier
                .weight(1f)
                .height(10.dp)
                .clip(RoundedCornerShape(5.dp)),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
            strokeCap = StrokeCap.Round
        )

        // Canlı Kalp Rozeti (❤️ 5)
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Can",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .scale(heartScale)
                        .size(16.dp)
                )
                Text(
                    text = "5",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
