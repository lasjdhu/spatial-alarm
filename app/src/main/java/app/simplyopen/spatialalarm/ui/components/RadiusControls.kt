package app.simplyopen.spatialalarm.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.simplyopen.spatialalarm.settings.DistanceUnitSystem
import app.simplyopen.spatialalarm.settings.formatDistance
import app.simplyopen.spatialalarm.settings.wakeUpRadiusPresets
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun RadiusControls(
    radiusMeters: Float,
    distanceUnitSystem: DistanceUnitSystem,
    onRadiusChange: (Float) -> Unit,
    onShowGuide: () -> Unit,
    onCreateAlarm: () -> Unit,
) {
    val radiusPresets = wakeUpRadiusPresets(distanceUnitSystem)
    val selectedPresetIndex = radiusPresets.indices.minBy { index ->
        abs(radiusPresets[index] - radiusMeters)
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(
                        text = "Wake Up radius",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold,
                    )
                    IconButton(
                        onClick = onShowGuide,
                        modifier = Modifier.size(24.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Help",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(16.dp),
                        )
                    }
                }
                Slider(
                    value = selectedPresetIndex.toFloat(),
                    onValueChange = { value ->
                        onRadiusChange(radiusPresets[value.roundToInt()])
                    },
                    steps = radiusPresets.size - 2,
                    valueRange = 0f..radiusPresets.lastIndex.toFloat(),
                )
                Text(
                    text = "${formatDistance(radiusPresets[selectedPresetIndex].toDouble(), distanceUnitSystem)} before arrival",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
            Button(
                onClick = onCreateAlarm,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp),
                )
                Text(
                    text = "Create alarm",
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}
