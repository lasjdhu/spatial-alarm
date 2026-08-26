package app.simplyopen.spatialalarm.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = ObsidianPrimaryDark,
    onPrimary = ObsidianOnPrimaryDark,
    primaryContainer = ObsidianPrimaryContainerDark,
    onPrimaryContainer = ObsidianOnPrimaryContainerDark,
    secondary = ObsidianSecondaryDark,
    onSecondary = ObsidianOnSecondaryDark,
    secondaryContainer = ObsidianSecondaryContainerDark,
    onSecondaryContainer = ObsidianOnSecondaryContainerDark,
    tertiary = ObsidianTertiaryDark,
    onTertiary = ObsidianOnTertiaryDark,
    error = ErrorRed,
    onError = OnErrorRed,
    errorContainer = ErrorContainerRed,
    onErrorContainer = OnErrorContainerRed,
    background = ObsidianBackgroundDark,
    onBackground = ObsidianOnBackgroundDark,
    surface = ObsidianSurfaceDark,
    onSurface = ObsidianOnSurfaceDark,
    surfaceVariant = ObsidianSurfaceVariantDark,
    onSurfaceVariant = ObsidianOnSurfaceVariantDark,
    outline = ObsidianOutlineDark,
    surfaceContainer = ObsidianSurfaceContainerDark,
)

private val LightColorScheme = lightColorScheme(
    primary = ObsidianPrimaryLight,
    onPrimary = ObsidianOnPrimaryLight,
    primaryContainer = ObsidianPrimaryContainerLight,
    onPrimaryContainer = ObsidianOnPrimaryContainerLight,
    secondary = ObsidianSecondaryLight,
    onSecondary = ObsidianOnSecondaryLight,
    secondaryContainer = ObsidianSecondaryContainerLight,
    onSecondaryContainer = ObsidianOnSecondaryContainerLight,
    tertiary = ObsidianTertiaryLight,
    onTertiary = ObsidianOnTertiaryLight,
    error = ErrorRedLight,
    onError = OnErrorRedLight,
    errorContainer = ErrorContainerRedLight,
    onErrorContainer = OnErrorContainerRedLight,
    background = ObsidianBackgroundLight,
    onBackground = ObsidianOnBackgroundLight,
    surface = ObsidianSurfaceLight,
    onSurface = ObsidianOnSurfaceLight,
    surfaceVariant = ObsidianSurfaceVariantLight,
    onSurfaceVariant = ObsidianOnSurfaceVariantLight,
    outline = ObsidianOutlineLight,
    surfaceContainer = ObsidianSurfaceContainerLight,
)

@Composable
fun SpatialAlarmTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
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
