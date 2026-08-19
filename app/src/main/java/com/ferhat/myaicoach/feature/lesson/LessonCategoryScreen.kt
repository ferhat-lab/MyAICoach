package com.ferhat.myaicoach.feature.lesson

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Ders Kategorisi Veri Modeli
 */
data class LessonCategory(
    val id: String,
    val title: String,
    val description: String,
    val icon: String,
    val cefrLevel: String,
    val progress: Float,
    val isLocked: Boolean,
    val accentColor: Color
)

/**
 * LessonCategoryScreen: Ders ve Kategori Seçim Ekranı.
 * - Öne Çıkan Ünite Hero Kartı (Gradient Arka Plan + Vani Mesajı)
 * - 2 Sütunlu Asimetrik Kategori Kartları (Gramer, Günlük Yaşam, Seyahat, İş)
 * - CEFR Seviye Rozetleri (A1, A2, B1) ve İlerleme Çubukları
 */
@Composable
fun LessonCategoryScreen(
    onCategoryClick: (LessonCategory) -> Unit,
    onContinueFeaturedUnit: () -> Unit
) {
    // Örnek Müfredat Kategorileri
    val categories = remember {
        listOf(
            LessonCategory(
                id = "cat_grammar",
                title = "Gramer Temelleri",
                description = "Cümle kurma ve zamanlar",
                icon = "📚",
                cefrLevel = "A1",
                progress = 1.0f,
                isLocked = false,
                accentColor = Color(0xFF7C3AED) // Mor
            ),
            LessonCategory(
                id = "cat_daily",
                title = "Günlük Yaşam",
                description = "Selamlaşma, tanışma ve alışveriş",
                icon = "☕",
                cefrLevel = "A1",
                progress = 0.5f,
                isLocked = false,
                accentColor = Color(0xFF38BDF8) // Mavi
            ),
            LessonCategory(
                id = "cat_travel",
                title = "Seyahat & Mekanlar",
                description = "Havaalanı, otel ve yön sorma",
                icon = "✈️",
                cefrLevel = "A2",
                progress = 0.0f,
                isLocked = true,
                accentColor = Color(0xFFF59E0B) // Kehribar
            ),
            LessonCategory(
                id = "cat_business",
                title = "İş & Kariyer",
                description = "E-posta yazma ve toplantı kalıpları",
                icon = "💼",
                cefrLevel = "B1",
                progress = 0.0f,
                isLocked = true,
                accentColor = Color(0xFFEC4899) // Pembe
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        // Üst Başlık
        Text(
            text = "Ders Kategorileri 🎯",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Seviyene uygun üniteyi seç ve öğrenmeye başla.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 🌟 ÖNE ÇIKAN ÜNİTE HERO KARTI
        FeaturedUnitHeroCard(
            onContinueClick = onContinueFeaturedUnit
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Kategori Izgara Başlığı
        Text(
            text = "Tüm Kategoriler",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(14.dp))

        // 2 Sütunlu Asimetrik Kategori Kartları Izgarası
        val chunkedCategories = categories.chunked(2)
        chunkedCategories.forEach { rowCategories ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowCategories.forEach { category ->
                    CategoryGridCard(
                        category = category,
                        onClick = {
                            if (!category.isLocked) {
                                onCategoryClick(category)
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
                // Tek eleman kalırsa boşluk doldurma
                if (rowCategories.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

/**
 * FeaturedUnitHeroCard: Öne çıkan üniteyi büyük gradient kart olarak gösterir.
 */
@Composable
private fun FeaturedUnitHeroCard(
    onContinueClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
        border = BorderStroke(
            1.5.dp,
            Brush.horizontalGradient(
                colors = listOf(Color(0xFF7C3AED), Color(0xFFEC4899))
            )
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Seviye Rozeti
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF7C3AED)
                ) {
                    Text(
                        text = "ÖNE ÇIKAN • A1 SEVİYE",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                // Vani Maskot Rozeti
                Text(text = "🐱", fontSize = 24.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Günlük Hayat ve Selamlaşma",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "İngilizcede ilk iletişim adımlarını at ve Vani ile pratik yap.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            // İlerleme Çubuğu (%33)
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "İlerleme: 4 / 12 Ders",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "%33",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF38BDF8),
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                LinearProgressIndicator(
                    progress = { 0.33f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(CircleShape),
                    color = Color(0xFF38BDF8),
                    trackColor = Color(0xFF0F172A)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Devam Et Butonu
            Button(
                onClick = onContinueClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF7C3AED)
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Üniteye Devam Et",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "İlerle",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

/**
 * CategoryGridCard: Izgara üzerindeki tek bir ders kategorisi kartı.
 */
@Composable
private fun CategoryGridCard(
    category: LessonCategory,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed && !category.isLocked) 0.96f else 1.0f,
        animationSpec = spring(),
        label = "categoryPressScale"
    )

    Card(
        modifier = modifier
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = !category.isLocked,
                onClick = onClick
            ),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (category.isLocked) Color(0xFF0F172A) else Color(0xFF1E293B)
        ),
        border = BorderStroke(
            width = if (category.isLocked) 1.dp else 1.5.dp,
            color = if (category.isLocked) Color.White.copy(alpha = 0.08f) else category.accentColor.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Kategori İkonu
                Text(text = category.icon, fontSize = 28.sp)

                // Kilitli / CEFR Rozeti
                if (category.isLocked) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Kilitli",
                        tint = Color.Gray,
                        modifier = Modifier.size(18.dp)
                    )
                } else {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = category.accentColor.copy(alpha = 0.2f),
                        border = BorderStroke(1.dp, category.accentColor)
                    ) {
                        Text(
                            text = category.cefrLevel,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = category.accentColor,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = category.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = if (category.isLocked) Color.Gray else Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = category.description,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(14.dp))

            // İlerleme Durumu
            if (!category.isLocked) {
                if (category.progress >= 1.0f) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0xFF14532D)
                    ) {
                        Text(
                            text = "✓ Tamamlandı",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4ADE80),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                } else {
                    LinearProgressIndicator(
                        progress = { category.progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(CircleShape),
                        color = category.accentColor,
                        trackColor = Color(0xFF0F172A)
                    )
                }
            } else {
                Text(
                    text = "🔒 Seviye ${category.cefrLevel}",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }
        }
    }
}
