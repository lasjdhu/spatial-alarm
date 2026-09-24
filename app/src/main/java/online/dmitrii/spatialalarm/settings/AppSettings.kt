package online.dmitrii.spatialalarm.settings

data class AppSettings(
    val distanceUnitSystem: DistanceUnitSystem = DistanceUnitSystem.METRIC,
    val themeMode: ThemeMode = ThemeMode.AUTOMATIC,
)
