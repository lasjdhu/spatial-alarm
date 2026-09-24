package online.dmitrii.spatialalarm.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = DarkActionPrimary,
    onPrimary = DarkBackground,
    primaryContainer = DarkSurface,
    onPrimaryContainer = DarkTextPrimary,
    secondary = DarkActionSecondary,
    onSecondary = DarkBackground,
    secondaryContainer = DarkSurface,
    onSecondaryContainer = DarkTextSecondary,
    tertiary = DarkActionSecondary,
    onTertiary = DarkBackground,
    background = DarkBackground,
    onBackground = DarkTextPrimary,
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurface,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkOutline,
    scrim = DarkBackground,
    surfaceContainer = DarkSurface,
    surfaceContainerHigh = DarkSurface,
)

private val LightColorScheme = lightColorScheme(
    primary = LightActionPrimary,
    onPrimary = LightBackground,
    primaryContainer = LightSurface,
    onPrimaryContainer = LightTextPrimary,
    secondary = LightActionSecondary,
    onSecondary = LightBackground,
    secondaryContainer = LightSurface,
    onSecondaryContainer = LightTextSecondary,
    tertiary = LightActionSecondary,
    onTertiary = LightBackground,
    background = LightBackground,
    onBackground = LightTextPrimary,
    surface = LightSurface,
    onSurface = LightTextPrimary,
    surfaceVariant = LightSurface,
    onSurfaceVariant = LightTextSecondary,
    outline = LightOutline,
    scrim = LightActionPrimary,
    surfaceContainer = LightSurface,
    surfaceContainerHigh = LightSurface,
)

@Composable
fun SpatialAlarmTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val insetsController = WindowCompat.getInsetsController(window, view)
            insetsController.isAppearanceLightStatusBars = !darkTheme
            insetsController.isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
