package app.simplyopen.spatialalarm

import app.simplyopen.spatialalarm.settings.DistanceUnitSystem
import app.simplyopen.spatialalarm.settings.defaultWakeUpRadius
import app.simplyopen.spatialalarm.settings.formatDistance
import app.simplyopen.spatialalarm.settings.wakeUpRadiusGuideRanges
import app.simplyopen.spatialalarm.settings.wakeUpRadiusPresets
import java.util.Locale
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class WakeUpRadiusPresetsTest {
    private lateinit var originalLocale: Locale

    @Before
    fun setUp() {
        originalLocale = Locale.getDefault()
        Locale.setDefault(Locale.US)
    }

    @After
    fun tearDown() {
        Locale.setDefault(originalLocale)
    }

    @Test
    fun metricPresetsRetainQuarterKilometreSteps() {
        val labels = wakeUpRadiusPresets(DistanceUnitSystem.METRIC).map {
            formatDistance(it.toDouble(), DistanceUnitSystem.METRIC)
        }

        assertEquals(12, labels.size)
        assertEquals("250 m", labels.first())
        assertEquals("3 km", labels.last())
        assertEquals("1 km", formatDistance(defaultWakeUpRadius(DistanceUnitSystem.METRIC).toDouble(), DistanceUnitSystem.METRIC))
    }

    @Test
    fun imperialPresetsUseRoundFeetAndQuarterMiles() {
        val labels = wakeUpRadiusPresets(DistanceUnitSystem.IMPERIAL).map {
            formatDistance(it.toDouble(), DistanceUnitSystem.IMPERIAL)
        }

        assertEquals(
            listOf("750 ft", "1,500 ft", "0.5 mi", "0.75 mi", "1 mi", "1.25 mi", "1.5 mi", "1.75 mi", "2 mi"),
            labels,
        )
        assertEquals("0.5 mi", formatDistance(defaultWakeUpRadius(DistanceUnitSystem.IMPERIAL).toDouble(), DistanceUnitSystem.IMPERIAL))
    }

    @Test
    fun guideBreakpointsAreAvailableSliderPresets() {
        DistanceUnitSystem.entries.forEach { unitSystem ->
            val presets = wakeUpRadiusPresets(unitSystem)
            wakeUpRadiusGuideRanges(unitSystem).forEach { range ->
                assertTrue(range.startMeters in presets)
                assertTrue(range.endMeters in presets)
            }
        }
    }
}
