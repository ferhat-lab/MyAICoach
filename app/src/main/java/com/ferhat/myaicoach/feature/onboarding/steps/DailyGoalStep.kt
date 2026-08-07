package com.ferhat.myaicoach.feature.onboarding.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ferhat.myaicoach.ui.components.SelectionCard

@Composable
fun DailyGoalStep(
    selectedMinutes: Int?,
    onGoalSelected: (Int) -> Unit
) {
    val goals = listOf(
        10 to "Yoğun günler için kısa ve sürdürülebilir.",
        20 to "Dengeli ilerlemek için ideal.",
        30 to "Daha hızlı gelişmek isteyenler için.",
        45 to "Daha yoğun çalışmak isteyenler için."
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        goals.forEach { (minutes, description) ->

            SelectionCard(
                title = "$minutes dakika",
                description = description,
                selected = selectedMinutes == minutes,
                onClick = {
                    onGoalSelected(minutes)
                }
            )
        }
    }
}