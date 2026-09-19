package app.simplyopen.spatialalarm.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.simplyopen.spatialalarm.settings.DistanceUnitSystem
import app.simplyopen.spatialalarm.ui.components.AlarmList
import app.simplyopen.spatialalarm.ui.components.BackgroundScrim
import app.simplyopen.spatialalarm.ui.components.MapSheetContent
import app.simplyopen.spatialalarm.ui.components.CreateAlarmFab
import app.simplyopen.spatialalarm.ui.components.SpatialAlarmTopBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    distanceUnitSystem: DistanceUnitSystem,
    onOpenSettings: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    var isSheetVisible by remember { mutableStateOf(false) }
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false,
        ),
    )
    val dismissSheet: () -> Unit = {
        isSheetVisible = false
        coroutineScope.launch { scaffoldState.bottomSheetState.hide() }
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        containerColor = MaterialTheme.colorScheme.background,
        sheetContent = {
            Box(modifier = Modifier.fillMaxHeight(0.7f)) {
                MapSheetContent(
                    distanceUnitSystem = distanceUnitSystem,
                    onClose = dismissSheet,
                    onCreateAlarm = { dismissSheet() },
                )
            }
        },
        sheetPeekHeight = 0.dp,
        sheetDragHandle = null,
        sheetSwipeEnabled = false,
        sheetContainerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            Box {
                SpatialAlarmTopBar(onOpenSettings = onOpenSettings)
                BackgroundScrim(
                    visible = isSheetVisible,
                    onDismiss = dismissSheet,
                )
            }
        },
    ) { contentPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            AlarmList(contentPadding)

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomEnd,
            ) {
                CreateAlarmFab(
                    onClick = {
                        isSheetVisible = true
                        coroutineScope.launch { scaffoldState.bottomSheetState.expand() }
                    },
                )
            }

            BackgroundScrim(
                visible = isSheetVisible,
                onDismiss = dismissSheet,
            )
        }
    }
}
