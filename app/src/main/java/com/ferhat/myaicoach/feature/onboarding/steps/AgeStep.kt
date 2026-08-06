package com.ferhat.myaicoach.feature.onboarding.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ferhat.myaicoach.data.model.AgeRange

@Composable
fun AgeStep(
    selectedAgeRange: AgeRange?,
    onAgeRangeSelected: (AgeRange) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        AgeRange.entries.forEach { ageRange ->

            FilterChip(
                selected = selectedAgeRange == ageRange,
                onClick = {
                    onAgeRangeSelected(ageRange)
                },
                label = {
                    Text(text = ageRange.title)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}