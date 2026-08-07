package com.ferhat.myaicoach.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = Color.White,

    primaryContainer = PrimaryDark,
    onPrimaryContainer = Color.White,

    secondary = Secondary,
    onSecondary = Color.Black,

    secondaryContainer = SecondaryDark,
    onSecondaryContainer = Color.White,

    background = Background,
    onBackground = TextPrimary,

    surface = Surface,
    onSurface = TextPrimary,

    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = TextSecondary,

    error = Error,
    onError = Color.White,

    outline = Outline,
    outlineVariant = SurfaceVariant
)

@Composable
fun MyAICoachTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        shapes = AppShapes,
        content = content
    )
}