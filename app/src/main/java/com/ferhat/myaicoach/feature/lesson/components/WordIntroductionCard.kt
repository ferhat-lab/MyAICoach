package com.ferhat.myaicoach.feature.lesson.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.ferhat.myaicoach.domain.lesson.VocabularyItem

/**
 * WordIntroductionCard: Yeni öğrenilecek kelimeyi hero başlık, ses butonu, IPA telaffuzu
 * ve örnek cümle kartı ile canlı bir şekilde sunan Jetpack Compose bileşeni.
 */
@Composable
fun WordIntroductionCard(
    wordItem: VocabularyItem,
    onPlayAudio: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    // Ses butonuna basılma durumunu takip eden etkileşim kaynağı (InteractionSource)
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Basılma anında mikro ölçekleme animasyonu (0.94f basılma küçülmesi)
    val buttonScale by animateFloatAsState(
        targetValue = if (isPressed) 0.94f else 1.0f,
        animationSpec = spring(stiffness = 500f),
        label = "audioBtnPressScale"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // Üst Etiket: Canlı mor renkte parlak yıldız simgeli "YENİ KELİME" rozeti
        Text(
            text = "✦ YENİ KELİME",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Ana Kelime & Dairesel Gradient Ses Butonu Satırı
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            // İngilizce Kelime Başlığı
            Text(
                text = wordItem.word,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.size(16.dp))

            // Dairesel Canlı Mor-Turkuaz Gradient Ses Butonu
            Box(
                modifier = Modifier
                    .scale(buttonScale)
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.secondary
                            )
                        )
                    )
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        // UI katmanından TTS ses tetikleyici callback yayını
                        onPlayAudio(wordItem.word)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                    contentDescription = "Kelimeyi Dinle",
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }
        }

        // IPA Fonetik Telaffuz Gösterimi (Örn: /neɪm/)
        wordItem.pronunciation?.let { pron ->
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "/$pron/",
                style = MaterialTheme.typography.bodyLarge,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Türkçe Karşılık Vurgusu (Turkuaz ikincil renk)
        Text(
            text = wordItem.translation,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Örnek Cümle Kartı (İçerisinde hedef kelime mor renk ile vurgulanır)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Örnek Cümle",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Örnek cümle içerisinde hedef kelimeyi otomatik bulup mor renkle vurgulama
                val annotatedSentence = buildAnnotatedString {
                    val sentence = wordItem.exampleSentence
                    val target = wordItem.word
                    val startIndex = sentence.indexOf(target, ignoreCase = true)

                    if (startIndex >= 0) {
                        append(sentence.substring(0, startIndex))
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(sentence.substring(startIndex, startIndex + target.length))
                        }
                        append(sentence.substring(startIndex + target.length))
                    } else {
                        append(sentence)
                    }
                }

                Text(
                    text = annotatedSentence,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Cümlenin Türkçe Çevirisi
                Text(
                    text = wordItem.exampleTranslation,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
