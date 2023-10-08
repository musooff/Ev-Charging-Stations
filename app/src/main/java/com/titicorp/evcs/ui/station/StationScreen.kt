package com.titicorp.evcs.ui.station

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.titicorp.evcs.Screen
import com.titicorp.evcs.ui.station.booking.StationBookingScreen
import com.titicorp.evcs.ui.station.details.StationDetailsScreen
import com.titicorp.evcs.ui.station.directions.StationDirectionsScreen
import com.titicorp.evcs.utils.composables.Loading

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
private fun WithLoadingScreen(
    navController: NavController,
    content: @Composable (StationViewModel.UiState.Success) -> Unit,
) {
    val navBackStackEntry = remember {
        navController.getBackStackEntry(Screen.Station.route)
    }
    val viewModel = hiltViewModel<StationViewModel>(navBackStackEntry)
    val state by viewModel.uiState.collectAsState()
    when (val currentState = state) {
        StationViewModel.UiState.Loading -> {
            Loading()
        }

        is StationViewModel.UiState.Success -> {
            content(currentState)
        }
    }
}

fun NavGraphBuilder.stationGraph(navController: NavController) {
    navigation(startDestination = StationScreen.Details.route, route = Screen.Station.route) {
        composable(StationScreen.Details.route) {
            WithLoadingScreen(navController) {
                StationDetailsScreen(
                    navController = navController,
                    state = it,
                )
            }
        }
        composable(StationScreen.Booking.route) { StationBookingScreen(navController = navController) }
        composable(StationScreen.Directions.route) {
            WithLoadingScreen(navController) {
                StationDirectionsScreen(
                    navController = navController,
                    state = it,
                )
            }
        }
    }
}

enum class StationScreen(val route: String) {
    Details("details"),
    Booking("booking"),
    Directions("directions"),
}
