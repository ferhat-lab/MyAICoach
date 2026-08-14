package com.ferhat.myaicoach.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SelectionCard(
    title: String,
    description: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    val containerColor = animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surface
        },
        label = "selectionCardColor"
    )

    val borderColor = animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.outlineVariant
        },
        label = "selectionCardBorder"
    )

    val elevation = animateDpAsState(
        targetValue = if (selected) 8.dp else 3.dp,
        label = "selectionCardElevation"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),

        shape = MaterialTheme.shapes.medium,

        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation.value
        ),

        border = BorderStroke(
            width = if (selected) 2.dp else 1.dp,
            color = borderColor.value
        ),

        colors = CardDefaults.cardColors(
            containerColor = containerColor.value
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 6.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}