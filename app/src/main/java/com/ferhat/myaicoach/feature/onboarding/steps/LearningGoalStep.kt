package com.ferhat.myaicoach.feature.onboarding.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ferhat.myaicoach.data.model.LearningGoal
import com.ferhat.myaicoach.ui.components.SelectionCard

@Composable
fun LearningGoalStep(
    selectedGoal: LearningGoal?,
    onGoalSelected: (LearningGoal) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        LearningGoal.entries.forEach { goal ->
            SelectionCard(
                title = goal.title,
                description = goal.description,
                selected = selectedGoal == goal,
                onClick = {
                    onGoalSelected(goal)
                }
            )
        }
    }
}