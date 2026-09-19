package app.simplyopen.spatialalarm

import app.simplyopen.spatialalarm.settings.DistanceUnitSystem
import app.simplyopen.spatialalarm.settings.formatDistance
import java.util.Locale
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DistanceFormatterTest {
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
    fun metricUsesMetersAndKilometres() {
        assertEquals("750 m", formatDistance(750.0, DistanceUnitSystem.METRIC))
        assertEquals("1.75 km", formatDistance(1750.0, DistanceUnitSystem.METRIC))
    }

    @Test
    fun imperialUsesFeetAndMiles() {
        assertEquals("820 ft", formatDistance(250.0, DistanceUnitSystem.IMPERIAL))
        assertEquals("1 mi", formatDistance(1609.344, DistanceUnitSystem.IMPERIAL))
    }
}
