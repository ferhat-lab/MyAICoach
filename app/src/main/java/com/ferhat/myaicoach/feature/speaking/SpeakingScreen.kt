package com.ferhat.myaicoach.feature.speaking

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ferhat.myaicoach.domain.mascot.MascotState
import com.ferhat.myaicoach.feature.speaking.turn.ConversationTurn
import com.ferhat.myaicoach.feature.speaking.turn.TurnState

/**
 * SpeakingScreen: Vani Canlı AI Konuşma ve Senaryo Deneyimi Ekranı.
 * Turn State Machine ve User Barge-In (Araya Girme) tepkilerini görselleştirir.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeakingScreen(
    viewModel: SpeakingViewModel = viewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val activeTurn = uiState.activeTurn
    val mascotState = uiState.mascotState

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = uiState.scenario?.title ?: "Vani ile Konuşma",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Canlı AI Konuşma Senaryosu",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Geri",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    if (activeTurn != null && !activeTurn.state.isTerminal) {
                        IconButton(onClick = viewModel::cancelTurn) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Turu İptal Et",
                                tint = Color(0xFFEF4444)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // 1. ÜST: VANI REAKTİF KEDİ MASKOTU HERO ALANI
            VaniReactiveMascotHero(
                mascotState = mascotState,
                activeTurnState = activeTurn?.state ?: TurnState.IDLE
            )

            // 2. ORTA: CANLI KONUŞMA BALONU (Speech Bubble)
            SpeakingBubbleSection(
                activeTurn = activeTurn,
                mascotState = mascotState
            )

            // 3. ALT: MİKROFON VE BARGE-IN KONTROL ALANI
            SpeakingMicControlSection(
                activeTurnState = activeTurn?.state ?: TurnState.IDLE,
                onMicClick = viewModel::onMicPress
            )
        }
    }
}

/**
 * VaniReactiveMascotHero: Vani'nin 6 reaktif durumuna göre parıltı aura ve rozet animasyonlarını yönetir.
 */
@Composable
private fun VaniReactiveMascotHero(
    mascotState: MascotState,
    activeTurnState: TurnState
) {
    val infiniteTransition = rememberInfiniteTransition(label = "mascotAura")
    val auraScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = if (mascotState == MascotState.LISTENING || mascotState == MascotState.SPEAKING) 1.12f else 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "auraScale"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 8.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(140.dp)
        ) {
            // Dış Halka Aura Işığı
            Surface(
                modifier = Modifier
                    .size(130.dp)
                    .scale(auraScale),
                shape = CircleShape,
                color = when (mascotState) {
                    MascotState.LISTENING -> Color(0xFF38BDF8).copy(alpha = 0.25f) // Mavi
                    MascotState.THINKING -> Color(0xFF7C3AED).copy(alpha = 0.25f) // Mor
                    MascotState.SPEAKING -> Color(0xFFEC4899).copy(alpha = 0.25f) // Pembe
                    MascotState.HAPPY_CHEERING -> Color(0xFF22C55E).copy(alpha = 0.25f) // Yeşil
                    MascotState.GENTLE_HINT -> Color(0xFFF59E0B).copy(alpha = 0.25f) // Altın
                    MascotState.IDLE -> Color.White.copy(alpha = 0.05f)
                }
            ) {}

            // Vani Avatar Çerçevesi
            Surface(
                modifier = Modifier.size(110.dp),
                shape = CircleShape,
                color = Color(0xFF1E293B),
                border = BorderStroke(
                    2.5.dp,
                    when (mascotState) {
                        MascotState.LISTENING -> Color(0xFF38BDF8)
                        MascotState.THINKING -> Color(0xFF7C3AED)
                        MascotState.SPEAKING -> Color(0xFFEC4899)
                        MascotState.HAPPY_CHEERING -> Color(0xFF22C55E)
                        MascotState.GENTLE_HINT -> Color(0xFFF59E0B)
                        MascotState.IDLE -> Color(0xFF475569)
                    }
                )
            ) {
                Box(contentAlignment = Alignment.Center) {
                    // Vani Maskot İkonu ve Durum İfadesi
                    val mascotEmoji = when (mascotState) {
                        MascotState.LISTENING -> "🐱👂"
                        MascotState.THINKING -> "🐱🤔"
                        MascotState.SPEAKING -> "🐱💬"
                        MascotState.HAPPY_CHEERING -> "🐱🎉"
                        MascotState.GENTLE_HINT -> "🐱💡"
                        MascotState.IDLE -> "🐱"
                    }
                    Text(text = mascotEmoji, fontSize = 48.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Durum Etiketi Rozeti
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = Color(0xFF1E293B),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val stateText = when (activeTurnState) {
                    TurnState.LISTENING -> "Vani Seni Dinliyor..."
                    TurnState.TRANSCRIBED -> "Ses Analiz Edildi"
                    TurnState.LLM_GENERATING -> "Vani Düşünüyor..."
                    TurnState.TTS_GENERATING -> "Ses Hazırlanıyor..."
                    TurnState.PLAYING -> "Vani Konuşuyor..."
                    TurnState.COMPLETED -> "Harika Konuştun! 👏"
                    TurnState.CANCELLED -> "Araya Girildi / İptal"
                    TurnState.FAILED -> "Bağlantı Hatası"
                    TurnState.TIMED_OUT -> "Zaman Aşımı"
                    TurnState.IDLE -> "Konuşmak için mikrofona dokun"
                }

                Text(
                    text = stateText,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

/**
 * SpeakingBubbleSection: Canlı diyalog balonu.
 */
@Composable
private fun SpeakingBubbleSection(
    activeTurn: ConversationTurn?,
    mascotState: MascotState
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.08f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            when {
                activeTurn?.aiResponseText != null && (activeTurn.state == TurnState.PLAYING || activeTurn.state == TurnState.COMPLETED) -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Vani:",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFEC4899)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "\"${activeTurn.aiResponseText}\"",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                activeTurn?.userTranscript != null -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Sen:",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF38BDF8)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "\"${activeTurn.userTranscript}\"",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                else -> {
                    Text(
                        text = "Vani seni dinlemeye hazır! Aşağıdaki mikrofon butonuna dokunup İngilizce konuşmaya başla.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

/**
 * SpeakingMicControlSection: Mikrofon ve User Barge-In buton alanı.
 */
@Composable
private fun SpeakingMicControlSection(
    activeTurnState: TurnState,
    onMicClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1.0f,
        animationSpec = spring(),
        label = "micScale"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        // User Barge-In İkazı
        if (activeTurnState == TurnState.PLAYING || activeTurnState == TurnState.LLM_GENERATING) {
            Text(
                text = "⚡ Vani konuşurken mikrofona basarak araya girebilirsin (Barge-In)",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFFF59E0B),
                fontSize = 11.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Ana Mikrofon Butonu
        Surface(
            modifier = Modifier
                .size(80.dp)
                .scale(scale)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onMicClick
                ),
            shape = CircleShape,
            color = when (activeTurnState) {
                TurnState.LISTENING -> Color(0xFF38BDF8)
                TurnState.PLAYING -> Color(0xFFEC4899)
                else -> Color(0xFF7C3AED)
            },
            shadowElevation = 8.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = when (activeTurnState) {
                        TurnState.LISTENING -> Icons.Default.GraphicEq
                        TurnState.LLM_GENERATING -> Icons.Default.Psychology
                        TurnState.PLAYING -> Icons.Default.VolumeUp
                        else -> Icons.Default.Mic
                    },
                    contentDescription = "Mikrofon",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
}
