package com.ferhat.myaicoach.feature.onboarding.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ferhat.myaicoach.data.model.Interest

@Composable
fun InterestStep(
    selectedInterests: List<Interest>,
    onInterestClick: (Interest) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "İlgini çeken 3–5 konu seç"
        )

        Text(
            text = "${selectedInterests.size} / 5 seçildi"
        )

        Interest.entries.forEach { interest ->

            FilterChip(
                selected = interest in selectedInterests,
                onClick = {
                    onInterestClick(interest)
                },
                label = {
                    Text(
                        text = "${interest.icon} ${interest.title}"
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}