package com.ferhat.myaicoach.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onLessonClick: (LessonStage) -> Unit
) {
    val nickname = "Ferhat"
    val streakDays = 7
    val xp = 1240
    val coins = 320

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                horizontal = 24.dp,
                vertical = 32.dp
            )
    ) {

        Text(
            text = "Merhaba, $nickname 👋",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Bugünkü İngilizce yolculuğuna devam edelim.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HomeStat(
                value = "$streakDays",
                label = "🔥 Seri"
            )

            HomeStat(
                value = "$xp",
                label = "⭐ XP"
            )

            HomeStat(
                value = "$coins",
                label = "🪙 Coin"
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Bugünkü Yolculuğun",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        LessonJourney(
            onLessonClick = onLessonClick
        )
    }
}

@Composable
private fun HomeStat(
    value: String,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun LessonJourney(
    onLessonClick: (LessonStage) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        JourneyItem(
            stage = LessonStage.LEARN,
            status = LessonStageStatus.COMPLETED,
            title = "Öğren",
            description = "Yeni kelime ve kalıpları öğren",
            onClick = { }
        )

        JourneyItem(
            stage = LessonStage.PRACTICE,
            status = LessonStageStatus.CURRENT,
            title = "Pratik Yap",
            description = "Öğrendiklerini kullan",
            onClick = {
                onLessonClick(LessonStage.PRACTICE)
            }
        )

        JourneyItem(
            stage = LessonStage.LISTEN,
            status = LessonStageStatus.LOCKED,
            title = "Dinle",
            description = "İngilizceyi dinleyerek pekiştir",
            onClick = { }
        )

        JourneyItem(
            stage = LessonStage.SPEAK,
            status = LessonStageStatus.LOCKED,
            title = "Vani ile Konuş",
            description = "Bugün öğrendiklerini gerçek bir konuşmada kullan",
            onClick = { }
        )
    }
}

@Composable
private fun JourneyItem(
    stage: LessonStage,
    status: LessonStageStatus,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    val icon = when (status) {
        LessonStageStatus.COMPLETED -> "✓"
        LessonStageStatus.CURRENT -> "●"
        LessonStageStatus.LOCKED -> "🔒"
    }

    val containerColor = when (status) {
        LessonStageStatus.COMPLETED ->
            MaterialTheme.colorScheme.secondaryContainer

        LessonStageStatus.CURRENT ->
            MaterialTheme.colorScheme.primaryContainer

        LessonStageStatus.LOCKED ->
            MaterialTheme.colorScheme.surfaceVariant
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = status == LessonStageStatus.CURRENT,
                onClick = onClick
            ),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = icon,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}