package com.ferhat.myaicoach.feature.profile

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
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Başarım Rozeti Veri Modeli
 */
data class AchievementBadge(
    val id: String,
    val title: String,
    val description: String,
    val icon: String,
    val isUnlocked: Boolean,
    val accentColor: Color
)

/**
 * ProfileScreen: Profil ve Başarımlar Ekranı.
 * - Kullanıcı Profil Kartı & CEFR Seviye Rozeti
 * - Toplam XP, Seri ve Tamamlanan Ders İstatistikleri
 * - 2x2 Başarım Rozetleri Izgarası (Parıltılı Açık vs Kilitli Rozetler)
 * - Haftalık Konuşma İlerleme Grafiği
 */
@Composable
fun ProfileScreen(
    onSettingsClick: () -> Unit = {}
) {
    val nickname = "Ferhat"
    val level = "A1 Başlangıç"
    val streakDays = 7
    val xp = 1240
    val completedLessons = 14

    // Başarım Rozetleri Listesi
    val achievements = remember {
        listOf(
            AchievementBadge(
                id = "ach_first",
                title = "İlk Adım",
                description = "İlk dersini başarıyla tamamla",
                icon = "🌱",
                isUnlocked = true,
                accentColor = Color(0xFF22C55E) // Yeşil
            ),
            AchievementBadge(
                id = "ach_streak",
                title = "Seri Canavarı",
                description = "7 gün üst üste pratik yap",
                icon = "🔥",
                isUnlocked = true,
                accentColor = Color(0xFFEC4899) // Pembe
            ),
            AchievementBadge(
                id = "ach_vocab",
                title = "Kelime Ustası",
                description = "100 yeni kelime öğren",
                icon = "🧠",
                isUnlocked = true,
                accentColor = Color(0xFFF59E0B) // Kehribar
            ),
            AchievementBadge(
                id = "ach_vani",
                title = "Vani Dostu",
                description = "Vani ile 10 sesli konuşma yap",
                icon = "🐱",
                isUnlocked = false,
                accentColor = Color(0xFF38BDF8) // Mavi
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
            text = "Profilim 👤",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(18.dp))

        // 👤 KULLANICI PROFİL HERO KARTI
        UserProfileHeroCard(
            nickname = nickname,
            level = level,
            streakDays = streakDays
        )

        Spacer(modifier = Modifier.height(20.dp))

        // İSTATİSTİK ÖZETİ
        ProfileStatsRow(
            xp = xp,
            streak = streakDays,
            completedLessons = completedLessons
        )

        Spacer(modifier = Modifier.height(28.dp))

        // BAŞARIMLAR VE ROZETLER BAŞLIĞI
        Text(
            text = "Başarımlar ve Rozetler",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(14.dp))

        // 2x2 Başarım Rozetleri Izgarası
        val chunkedAchievements = achievements.chunked(2)
        chunkedAchievements.forEach { rowAchievements ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowAchievements.forEach { badge ->
                    AchievementBadgeCard(
                        badge = badge,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // HAFTALIK AKTİVİTE GRAFİĞİ
        WeeklyActivityChartCard()
    }
}

/**
 * UserProfileHeroCard: Kullanıcı Avatarı ve Seviye Rozeti
 */
@Composable
private fun UserProfileHeroCard(
    nickname: String,
    level: String,
    streakDays: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
        border = BorderStroke(
            1.5.dp,
            Brush.horizontalGradient(
                colors = listOf(Color(0xFF7C3AED), Color(0xFF38BDF8))
            )
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Avatar Çerçevesi
            Surface(
                modifier = Modifier.size(64.dp),
                shape = CircleShape,
                color = Color(0xFF0F172A),
                border = BorderStroke(2.dp, Color(0xFF7C3AED))
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profil",
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = nickname,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(4.dp))

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF7C3AED).copy(alpha = 0.2f),
                    border = BorderStroke(1.dp, Color(0xFF7C3AED))
                ) {
                    Text(
                        text = "🎓 $level",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFC084FC),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }
        }
    }
}

/**
 * ProfileStatsRow: 3 Sütunlu İstatistik Kartları
 */
@Composable
private fun ProfileStatsRow(
    xp: Int,
    streak: Int,
    completedLessons: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ProfileStatTile(
            icon = "⭐",
            value = "$xp",
            label = "XP Puanı",
            modifier = Modifier.weight(1f)
        )
        ProfileStatTile(
            icon = "🔥",
            value = "$streak Gün",
            label = "Aktif Seri",
            modifier = Modifier.weight(1f)
        )
        ProfileStatTile(
            icon = "🎯",
            value = "$completedLessons",
            label = "Tamamlanan",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun ProfileStatTile(
    icon: String,
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        color = Color(0xFF1E293B),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.08f))
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = icon, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 10.sp
            )
        }
    }
}

/**
 * AchievementBadgeCard: Başarım Rozeti Kartı
 */
@Composable
private fun AchievementBadgeCard(
    badge: AchievementBadge,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (badge.isUnlocked) Color(0xFF1E293B) else Color(0xFF0F172A)
        ),
        border = BorderStroke(
            width = if (badge.isUnlocked) 1.5.dp else 1.dp,
            color = if (badge.isUnlocked) badge.accentColor.copy(alpha = 0.6f) else Color.White.copy(alpha = 0.08f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Rozet İkonu & Işık Dairesi
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(52.dp)
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = CircleShape,
                    color = if (badge.isUnlocked) badge.accentColor.copy(alpha = 0.2f) else Color.Transparent,
                    border = BorderStroke(
                        1.dp,
                        if (badge.isUnlocked) badge.accentColor else Color.Gray.copy(alpha = 0.3f)
                    )
                ) {}

                Text(
                    text = badge.icon,
                    fontSize = 26.sp,
                    color = if (badge.isUnlocked) Color.Unspecified else Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = badge.title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = if (badge.isUnlocked) Color.White else Color.Gray
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = badge.description,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 11.sp,
                maxLines = 2
            )
        }
    }
}

/**
 * WeeklyActivityChartCard: Haftalık Aktiflik Grafiği
 */
@Composable
private fun WeeklyActivityChartCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.08f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Haftalık Konuşma Aktifliği",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(14.dp))

            val days = listOf("Pzt", "Sal", "Çar", "Per", "Cum", "Cmt", "Paz")
            val heights = listOf(0.4f, 0.7f, 0.5f, 0.9f, 0.8f, 1.0f, 0.6f)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                days.forEachIndexed { index, day ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier.height(90.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .width(18.dp)
                                .height((60 * heights[index]).dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(
                                    if (index == 5) Color(0xFF7C3AED) else Color(0xFF38BDF8).copy(alpha = 0.6f)
                                )
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = day,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }
}
