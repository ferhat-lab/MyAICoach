package com.ferhat.myaicoach.feature.onboarding.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ferhat.myaicoach.data.model.EnglishLevel

@Composable
fun EnglishLevelStep(
    selectedLevel: EnglishLevel?,
    onLevelSelected: (EnglishLevel) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        EnglishLevel.entries.forEach { level ->

            FilterChip(
                selected = selectedLevel == level,
                onClick = {
                    onLevelSelected(level)
                },
                label = {
                    Text(
                        text = "${level.title} • ${level.description}"
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}