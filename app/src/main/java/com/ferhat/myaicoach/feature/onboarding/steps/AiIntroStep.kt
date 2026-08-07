package com.ferhat.myaicoach.feature.onboarding.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ferhat.myaicoach.data.model.UserProfile

@Composable
fun AiIntroStep(
    userProfile: UserProfile
) {
    val interestsText = userProfile.interests
        .take(3)
        .joinToString(", ") { it.title }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🐱",
            style = MaterialTheme.typography.displaySmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Merhaba ${userProfile.nickname}!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Ben Vani. Artık İngilizce koçun olarak sana eşlik edeceğim.",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        userProfile.englishLevel?.let { level ->
            Text(
                text = "İngilizce seviyeni ${level.title} olarak ayarladım.",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        userProfile.dailyGoalMinutes?.let { minutes ->
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Her gün yaklaşık $minutes dakika birlikte çalışacağız.",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        if (interestsText.isNotBlank()) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$interestsText gibi ilgini çeken konuları derslerinde kullanacağım.",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "İlk öğrenme planın hazırlanıyor...",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(12.dp))

        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth()
        )
    }
}