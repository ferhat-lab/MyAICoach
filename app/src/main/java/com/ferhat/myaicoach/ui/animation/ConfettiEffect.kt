package com.ferhat.myaicoach.ui.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.withTransform
import kotlin.random.Random

private data class ConfettiParticle(
    val initialX: Float, // Normalized 0..1
    val initialY: Float, // Normalized 0..1
    val vx: Float,
    val vy: Float,
    val color: Color,
    val size: Float,
    val rotationSpeed: Float,
    val isCircle: Boolean
)

@Composable
fun ConfettiEffect(
    modifier: Modifier = Modifier,
    particleCount: Int = 45
) {
    val progress = remember { Animatable(0f) }

    val particles = remember {
        val colors = listOf(
            Color(0xFF7C3AED), // Primary Mor
            Color(0xFF22D3EE), // Secondary Turkuaz
            Color(0xFF22C55E), // Success Yeşil
            Color(0xFFF59E0B), // Sarı / Altın
            Color(0xFFEC4899), // Pembe
            Color(0xFF3B82F6)  // Mavi
        )

        List(particleCount) {
            ConfettiParticle(
                initialX = Random.nextFloat() * 0.6f + 0.2f, // Center 20% to 80%
                initialY = 0.75f, // Burst from bottom area
                vx = (Random.nextFloat() - 0.5f) * 1200f, // Explosive horizontal velocity
                vy = -(Random.nextFloat() * 1000f + 600f), // Upward explosive velocity
                color = colors.random(),
                size = Random.nextFloat() * 12f + 8f,
                rotationSpeed = (Random.nextFloat() - 0.5f) * 720f,
                isCircle = Random.nextBoolean()
            )
        }
    }

    LaunchedEffect(Unit) {
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1500)
        )
    }

    val currentProgress = progress.value

    if (currentProgress < 1f) {
        Canvas(modifier = modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            particles.forEach { p ->
                val time = currentProgress * 1.5f // 1.5s physics timeline
                val gravity = 1800f * time * time // Acceleration down

                val currentX = p.initialX * width + p.vx * time
                val currentY = p.initialY * height + p.vy * time + gravity

                val alpha = (1f - currentProgress).coerceIn(0f, 1f)
                val colorWithAlpha = p.color.copy(alpha = alpha)

                val rotation = p.rotationSpeed * currentProgress

                withTransform({
                    rotate(degrees = rotation, pivot = Offset(currentX, currentY))
                }) {
                    if (p.isCircle) {
                        drawCircle(
                            color = colorWithAlpha,
                            radius = p.size / 2,
                            center = Offset(currentX, currentY)
                        )
                    } else {
                        drawRect(
                            color = colorWithAlpha,
                            topLeft = Offset(currentX - p.size / 2, currentY - p.size / 2),
                            size = Size(p.size, p.size * 1.4f)
                        )
                    }
                }
            }
        }
    }
}
