package app.simplyopen.spatialalarm.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.DirectionsTransit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import app.simplyopen.spatialalarm.settings.DistanceUnitSystem
import app.simplyopen.spatialalarm.settings.WakeUpRadiusGuideRange
import app.simplyopen.spatialalarm.settings.formatDistance
import app.simplyopen.spatialalarm.settings.wakeUpRadiusGuideRanges

@Composable
fun RadiusGuideDialog(
    distanceUnitSystem: DistanceUnitSystem,
    onDismiss: () -> Unit,
) {
    val ranges = wakeUpRadiusGuideRanges(distanceUnitSystem)

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.padding(16.dp),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = "Wake Up radius guide",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                RadiusModeItem(
                    icon = Icons.AutoMirrored.Filled.DirectionsWalk,
                    title = "Walking",
                    range = formatRange(ranges[0], distanceUnitSystem),
                )
                RadiusModeItem(
                    icon = Icons.Default.DirectionsBus,
                    title = "Bus",
                    range = formatRange(ranges[1], distanceUnitSystem),
                )
                RadiusModeItem(
                    icon = Icons.Default.DirectionsTransit,
                    title = "Train",
                    range = formatRange(ranges[2], distanceUnitSystem),
                )
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.End),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                ) {
                    Text("Got it")
                }
            }
        }
    }
}

private fun formatRange(
    range: WakeUpRadiusGuideRange,
    unitSystem: DistanceUnitSystem,
): String = "${formatDistance(range.startMeters.toDouble(), unitSystem)} – " +
    formatDistance(range.endMeters.toDouble(), unitSystem)
