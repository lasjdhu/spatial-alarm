package app.simplyopen.spatialalarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import app.simplyopen.spatialalarm.ui.components.AlarmList
import app.simplyopen.spatialalarm.ui.components.MapSheetContent
import app.simplyopen.spatialalarm.ui.components.NewAlarmFab
import app.simplyopen.spatialalarm.ui.theme.SpatialAlarmTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpatialAlarmTheme {
                val scope = rememberCoroutineScope()
                var isSheetVisible by remember { mutableStateOf(false) }
                val scaffoldState = rememberBottomSheetScaffoldState(
                    bottomSheetState = rememberStandardBottomSheetState(
                        initialValue = SheetValue.Hidden,
                        skipHiddenState = false
                    )
                )

                BottomSheetScaffold(
                    scaffoldState = scaffoldState,
                    containerColor = MaterialTheme.colorScheme.background,
                    sheetContent = {
                        Box(
                            modifier = Modifier.fillMaxHeight(0.7f)
                        ) {
                            MapSheetContent(
                                onClose = {
                                    isSheetVisible = false
                                    scope.launch { scaffoldState.bottomSheetState.hide() }
                                },
                                onCreateAlarm = { _ ->
                                    isSheetVisible = false
                                    scope.launch { scaffoldState.bottomSheetState.hide() }
                                }
                            )
                        }
                    },
                    sheetPeekHeight = 0.dp,
                    sheetDragHandle = null,
                    sheetSwipeEnabled = false,
                    sheetContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(style = SpanStyle(
                                            fontWeight = FontWeight.Black,
                                            letterSpacing = 4.sp,
                                            color = MaterialTheme.colorScheme.primary
                                        )) {
                                            append("SPACE ALARM")
                                        }
                                    },
                                    style = MaterialTheme.typography.titleLarge
                                )
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                                titleContentColor = MaterialTheme.colorScheme.onSurface,
                            )
                        )
                    }
                ) { contentPadding ->
                    Box(modifier = Modifier.fillMaxSize()) {
                        AlarmList(contentPadding)

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            NewAlarmFab(onClick = {
                                isSheetVisible = true
                                scope.launch { scaffoldState.bottomSheetState.expand() }
                            })
                        }

                        if (isSheetVisible) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black.copy(alpha = 0.32f))
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {
                                        isSheetVisible = false
                                        scope.launch { scaffoldState.bottomSheetState.hide() }
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}
