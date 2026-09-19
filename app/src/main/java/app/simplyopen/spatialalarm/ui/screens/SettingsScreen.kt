package app.simplyopen.spatialalarm.ui.screens

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.simplyopen.spatialalarm.settings.AppSettings
import app.simplyopen.spatialalarm.settings.DistanceUnitSystem
import app.simplyopen.spatialalarm.settings.ThemeMode
import app.simplyopen.spatialalarm.ui.components.SettingsOption
import app.simplyopen.spatialalarm.ui.components.SettingsSectionTitle
import app.simplyopen.spatialalarm.ui.components.SettingsTopBar

@Composable
fun SettingsScreen(
    settings: AppSettings,
    onDistanceUnitSystemChange: (DistanceUnitSystem) -> Unit,
    onThemeModeChange: (ThemeMode) -> Unit,
    onBack: () -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            SettingsTopBar(onBack = onBack)
        },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .verticalScroll(rememberScrollState()),
        ) {
            SettingsSectionTitle("Distance units")
            SettingsOption(
                title = "Metric",
                description = "Kilometres and metres (km, m)",
                selected = settings.distanceUnitSystem == DistanceUnitSystem.METRIC,
                onClick = { onDistanceUnitSystemChange(DistanceUnitSystem.METRIC) },
            )
            SettingsOption(
                title = "Imperial",
                description = "Miles and feet (mi, ft)",
                selected = settings.distanceUnitSystem == DistanceUnitSystem.IMPERIAL,
                onClick = { onDistanceUnitSystemChange(DistanceUnitSystem.IMPERIAL) },
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.surface,
            )

            SettingsSectionTitle("Theme")
            SettingsOption(
                title = "Automatic",
                description = "Follow the device theme",
                selected = settings.themeMode == ThemeMode.AUTOMATIC,
                onClick = { onThemeModeChange(ThemeMode.AUTOMATIC) },
            )
            SettingsOption(
                title = "Light",
                selected = settings.themeMode == ThemeMode.LIGHT,
                onClick = { onThemeModeChange(ThemeMode.LIGHT) },
            )
            SettingsOption(
                title = "Dark",
                selected = settings.themeMode == ThemeMode.DARK,
                onClick = { onThemeModeChange(ThemeMode.DARK) },
            )
        }
    }
}
