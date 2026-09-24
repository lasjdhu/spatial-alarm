package online.dmitrii.spatialalarm.settings

private const val METERS_PER_FOOT = 0.3048f
private const val METERS_PER_MILE = 1609.344f

data class WakeUpRadiusGuideRange(
    val startMeters: Float,
    val endMeters: Float,
)

private val metricPresets = (1..12).map { step -> step * 250f }

private val imperialPresets = listOf(
    750f * METERS_PER_FOOT,
    1_500f * METERS_PER_FOOT,
    0.5f * METERS_PER_MILE,
    0.75f * METERS_PER_MILE,
    1f * METERS_PER_MILE,
    1.25f * METERS_PER_MILE,
    1.5f * METERS_PER_MILE,
    1.75f * METERS_PER_MILE,
    2f * METERS_PER_MILE,
)

fun wakeUpRadiusPresets(unitSystem: DistanceUnitSystem): List<Float> = when (unitSystem) {
    DistanceUnitSystem.METRIC -> metricPresets
    DistanceUnitSystem.IMPERIAL -> imperialPresets
}

fun defaultWakeUpRadius(unitSystem: DistanceUnitSystem): Float = when (unitSystem) {
    DistanceUnitSystem.METRIC -> 1_000f
    DistanceUnitSystem.IMPERIAL -> 0.5f * METERS_PER_MILE
}

fun wakeUpRadiusGuideRanges(unitSystem: DistanceUnitSystem): List<WakeUpRadiusGuideRange> {
    val presets = wakeUpRadiusPresets(unitSystem)
    val breakpoints = when (unitSystem) {
        DistanceUnitSystem.METRIC -> listOf(0, 2, 6, 11)
        DistanceUnitSystem.IMPERIAL -> listOf(0, 2, 4, 8)
    }

    return breakpoints.zipWithNext { start, end ->
        WakeUpRadiusGuideRange(
            startMeters = presets[start],
            endMeters = presets[end],
        )
    }
}
