package app.simplyopen.spatialalarm.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import app.simplyopen.spatialalarm.settings.DistanceUnitSystem
import app.simplyopen.spatialalarm.settings.defaultWakeUpRadius
import com.google.android.gms.maps.model.LatLng

@Composable
fun MapSheetContent(
    distanceUnitSystem: DistanceUnitSystem,
    onClose: () -> Unit,
    onCreateAlarm: (LatLng) -> Unit,
) {
    val targetLocation = LatLng(1.35, 103.87)
    var radiusMeters by rememberSaveable(distanceUnitSystem) {
        mutableFloatStateOf(defaultWakeUpRadius(distanceUnitSystem))
    }
    var showRadiusGuide by rememberSaveable { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        MapSheetHeader(onClose = onClose)
        AlarmRadiusMap(
            targetLocation = targetLocation,
            radiusMeters = radiusMeters.toDouble(),
            modifier = Modifier.weight(0.8f),
        )
        RadiusControls(
            radiusMeters = radiusMeters,
            distanceUnitSystem = distanceUnitSystem,
            onRadiusChange = { radiusMeters = it },
            onShowGuide = { showRadiusGuide = true },
            onCreateAlarm = { onCreateAlarm(targetLocation) },
        )
    }

    if (showRadiusGuide) {
        RadiusGuideDialog(
            distanceUnitSystem = distanceUnitSystem,
            onDismiss = { showRadiusGuide = false },
        )
    }
}
