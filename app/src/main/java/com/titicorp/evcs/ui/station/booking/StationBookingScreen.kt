package com.titicorp.evcs.ui.station.booking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.titicorp.evcs.Screen
import com.titicorp.evcs.model.Charger
import com.titicorp.evcs.ui.home.ChargerItem
import com.titicorp.evcs.ui.station.StationScreen
import com.titicorp.evcs.utils.composables.Loading
import com.titicorp.evcs.utils.composables.TopBarTitle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StationBookingScreen(
    navController: NavController,
    viewModel: StationBookingViewModel = hiltViewModel(),
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ToolbarLayout(navController)
            var selectedTime: Int? by remember {
                mutableStateOf(null)
            }
            var selectedCharger: Charger? by remember {
                mutableStateOf(null)
            }
            val uiState by viewModel.uiState.collectAsState()
            when (val state = uiState) {
                is StationBookingViewModel.UiState.Loading -> {
                    Loading()
                }

                is StationBookingViewModel.UiState.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        item(span = { GridItemSpan(3) }) {
                            Column {
                                Header("Select Date")
                                DatePicker(
                                    state = rememberDatePickerState(),
                                    title = null,
                                    headline = null,
                                    showModeToggle = false,
                                    dateValidator = { it >= System.currentTimeMillis() },
                                )
                                Header("Select Charger")
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }

                        items(state.chargers) {
                            ChargerItem(
                                charger = it,
                                selected = selectedCharger == it,
                                onClick = {
                                    selectedCharger = it
                                },
                            )
                        }

                        item(span = { GridItemSpan(3) }) {
                            Column {
                                Header("Select Time")
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                        for (time in 6..20) {
                            item {
                                TimePickerItem(
                                    time = time,
                                    selected = selectedTime == time,
                                    onClick = {
                                        selectedTime = time
                                    },
                                )
                            }
                        }
                    }
                }
            }
        }
        var bookingSuccessful by remember { mutableStateOf(false) }

        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            onClick = {
                bookingSuccessful = true
            },
        ) {
            Text(text = "Book")
        }
        val sheetState = rememberModalBottomSheetState()
        val scope = rememberCoroutineScope()
        if (bookingSuccessful) {
            BookingSuccessfulBottomSheet(navController, sheetState, scope)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun BookingSuccessfulBottomSheet(navController: NavController, sheetState: SheetState, scope: CoroutineScope) {
    ModalBottomSheet(
        onDismissRequest = {
            navController.navigateUp()
        },
        sheetState = sheetState,
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = "Booking Successful!",
            style = MaterialTheme.typography.titleLarge,
        )
        Row(
            modifier = Modifier
                .padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            OutlinedButton(
                modifier = Modifier
                    .weight(1f),
                onClick = {
                    scope.launch { sheetState.hide() }
                        .invokeOnCompletion {
                            navController.navigateUp()
                        }
                },
            ) {
                Text(text = "Go Back")
            }
            Button(
                modifier = Modifier
                    .weight(1f),
                onClick = {
                    scope.launch { sheetState.hide() }
                        .invokeOnCompletion {
                            navController.navigate(
                                route = Screen.MyBookings.route,
                                navOptions = navOptions {
                                    popUpTo(StationScreen.Details.route)
                                },
                            )
                        }
                },
            ) {
                Text(text = "My Bookings")
            }
        }
    }
}

@Composable
private fun Header(text: String) {
    Text(
        modifier = Modifier
            .padding(start = 20.dp),
        text = text,
        style = MaterialTheme.typography.headlineSmall,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ToolbarLayout(navController: NavController) {
    TopAppBar(
        title = { TopBarTitle("Booking") },
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.navigateUp()
                },
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                )
            }
        },
    )
}

@Composable
private fun SelectDateLayout() {
}

@Composable
private fun TimePickerItem(
    time: Int,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val content = @Composable {
        if (time < 10) {
            Text(text = "0$time:00")
        } else {
            Text(text = "$time:00")
        }
    }
    val modifier = Modifier
        .padding(
            start = when (time.mod(3)) {
                0 -> 20.dp
                1 -> 10.dp
                else -> 0.dp
            },
            end = when (time.mod(3)) {
                2 -> 20.dp
                1 -> 10.dp
                else -> 0.dp
            },
        )
    if (selected) {
        Button(
            modifier = modifier,
            onClick = onClick,
            contentPadding = PaddingValues(0.dp),
        ) {
            content()
        }
    } else {
        OutlinedButton(
            modifier = modifier,
            onClick = onClick,
            contentPadding = PaddingValues(0.dp),
            enabled = time > 9,
        ) {
            content()
        }
    }
}
