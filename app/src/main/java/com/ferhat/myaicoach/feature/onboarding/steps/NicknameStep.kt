package com.ferhat.myaicoach.feature.onboarding.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun NicknameStep(
    nickname: String,
    onNicknameChange: (String) -> Unit
) {

    Column(
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "👋",
            style = MaterialTheme.typography.displaySmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Sana nasıl hitap etmemi istersin?",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Bu ismi konuşmalarımızda ve derslerde kullanacağım.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = nickname,
            onValueChange = onNicknameChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = {
                Text("Takma ad")
            }
        )
    }
}