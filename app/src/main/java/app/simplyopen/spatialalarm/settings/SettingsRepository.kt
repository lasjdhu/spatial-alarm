package app.simplyopen.spatialalarm.settings

import android.content.Context

class SettingsRepository(context: Context) {
    private val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun load(): AppSettings = AppSettings(
        distanceUnitSystem = preferences.getString(DISTANCE_UNITS_KEY, null)
            ?.let { storedValue ->
                DistanceUnitSystem.entries.firstOrNull {
                    it.name.equals(storedValue, ignoreCase = true)
                }
            }
            ?: DistanceUnitSystem.METRIC,
        themeMode = preferences.getString(THEME_KEY, null)
            ?.let { storedValue ->
                ThemeMode.entries.firstOrNull {
                    it.name.equals(storedValue, ignoreCase = true)
                }
            }
            ?: ThemeMode.AUTOMATIC,
    )

    fun saveDistanceUnitSystem(value: DistanceUnitSystem) {
        preferences.edit().putString(DISTANCE_UNITS_KEY, value.name).apply()
    }

    fun saveThemeMode(value: ThemeMode) {
        preferences.edit().putString(THEME_KEY, value.name).apply()
    }

    private companion object {
        const val PREFERENCES_NAME = "app_settings"
        const val DISTANCE_UNITS_KEY = "distance_units"
        const val THEME_KEY = "theme"
    }
}
