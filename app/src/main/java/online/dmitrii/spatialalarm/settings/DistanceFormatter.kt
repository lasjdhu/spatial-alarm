package online.dmitrii.spatialalarm.settings

import java.text.NumberFormat

private const val METERS_PER_MILE = 1609.344
private const val FEET_PER_METER = 3.28084
private const val MILE_DISPLAY_THRESHOLD_METERS = METERS_PER_MILE / 2.0 - 0.01

fun formatDistance(meters: Double, unitSystem: DistanceUnitSystem): String = when (unitSystem) {
    DistanceUnitSystem.METRIC -> {
        if (meters < 1000.0) {
            "${formatNumber(meters, 0)} m"
        } else {
            "${formatNumber(meters / 1000.0, 2)} km"
        }
    }

    DistanceUnitSystem.IMPERIAL -> {
        if (meters < MILE_DISPLAY_THRESHOLD_METERS) {
            "${formatNumber(meters * FEET_PER_METER, 0)} ft"
        } else {
            "${formatNumber(meters / METERS_PER_MILE, 2)} mi"
        }
    }
}

private fun formatNumber(value: Double, maximumFractionDigits: Int): String =
    NumberFormat.getNumberInstance().apply {
        minimumFractionDigits = 0
        this.maximumFractionDigits = maximumFractionDigits
    }.format(value)
