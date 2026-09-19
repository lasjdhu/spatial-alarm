package app.simplyopen.spatialalarm.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import app.simplyopen.spatialalarm.ui.theme.MapRadiusBlue
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState

@Composable
fun AlarmRadiusMap(
    targetLocation: LatLng,
    radiusMeters: Double,
    modifier: Modifier = Modifier,
) {
    val markerState = rememberUpdatedMarkerState(position = targetLocation)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(targetLocation, 15f)
    }

    LaunchedEffect(radiusMeters, markerState.position) {
        val radiusInDegrees = radiusMeters / METERS_PER_LATITUDE_DEGREE
        val bounds = LatLngBounds(
            LatLng(
                markerState.position.latitude - radiusInDegrees,
                markerState.position.longitude - radiusInDegrees,
            ),
            LatLng(
                markerState.position.latitude + radiusInDegrees,
                markerState.position.longitude + radiusInDegrees,
            ),
        )
        cameraPositionState.animate(
            CameraUpdateFactory.newLatLngBounds(bounds, MAP_PADDING_PX),
            durationMs = CAMERA_ANIMATION_DURATION_MS,
        )
    }

    GoogleMap(
        modifier = modifier.fillMaxWidth(),
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(zoomControlsEnabled = false),
    ) {
        Marker(
            state = markerState,
            title = "Target Location",
        )
        Circle(
            center = markerState.position,
            radius = radiusMeters,
            fillColor = MapRadiusBlue.copy(alpha = 0.28f),
            strokeColor = MapRadiusBlue,
            strokeWidth = 4f,
        )
    }
}

private const val METERS_PER_LATITUDE_DEGREE = 111_000.0
private const val MAP_PADDING_PX = 100
private const val CAMERA_ANIMATION_DURATION_MS = 1_000
