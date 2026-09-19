package app.simplyopen.spatialalarm.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import app.simplyopen.spatialalarm.settings.SettingsRepository
import app.simplyopen.spatialalarm.settings.ThemeMode
import app.simplyopen.spatialalarm.ui.screens.HomeScreen
import app.simplyopen.spatialalarm.ui.screens.SettingsScreen
import app.simplyopen.spatialalarm.ui.theme.SpatialAlarmTheme

@Composable
fun SpatialAlarmApp() {
    val applicationContext = LocalContext.current.applicationContext
    val settingsRepository = remember(applicationContext) {
        SettingsRepository(applicationContext)
    }
    var settings by remember { mutableStateOf(settingsRepository.load()) }
    var currentScreen by rememberSaveable { mutableStateOf(AppScreen.Home) }

    val darkTheme = when (settings.themeMode) {
        ThemeMode.AUTOMATIC -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    SpatialAlarmTheme(darkTheme = darkTheme) {
        BackHandler(enabled = currentScreen == AppScreen.Settings) {
            currentScreen = AppScreen.Home
        }

        when (currentScreen) {
            AppScreen.Home -> HomeScreen(
                distanceUnitSystem = settings.distanceUnitSystem,
                onOpenSettings = { currentScreen = AppScreen.Settings },
            )

            AppScreen.Settings -> SettingsScreen(
                settings = settings,
                onDistanceUnitSystemChange = { value ->
                    settingsRepository.saveDistanceUnitSystem(value)
                    settings = settings.copy(distanceUnitSystem = value)
                },
                onThemeModeChange = { value ->
                    settingsRepository.saveThemeMode(value)
                    settings = settings.copy(themeMode = value)
                },
                onBack = { currentScreen = AppScreen.Home },
            )
        }
    }
}

private enum class AppScreen {
    Home,
    Settings,
}
