package com.titicorp.evcs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.titicorp.evcs.ui.auth.authGraph
import com.titicorp.evcs.ui.home.HomeScreen
import com.titicorp.evcs.ui.my.MyScreen
import com.titicorp.evcs.ui.mybookings.MyBookingsScreen
import com.titicorp.evcs.ui.saved.SavedScreen
import com.titicorp.evcs.ui.settings.SettingsScreen
import com.titicorp.evcs.ui.station.stationGraph
import com.titicorp.evcs.utils.composables.Loading
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel = hiltViewModel<MainViewModel>()
            val uiState by viewModel.uiState.collectAsState()
            when (val state = uiState) {
                MainViewModel.UiState.Loading -> {
                    Loading()
                }

                is MainViewModel.UiState.Success -> {
                    val navController = rememberNavController()
                    val startDestination = if (state.isLoggedIn) Screen.Home.route else Screen.Auth.route
                    NavHost(navController = navController, startDestination = startDestination) {
                        authGraph(navController)
                        composable(Screen.Home.route) { HomeScreen(navController) }
                        stationGraph(navController)
                        composable(Screen.MyBookings.route) { MyBookingsScreen(navController) }
                        composable(Screen.Settings.route) { SettingsScreen(navController) }
                        composable(Screen.Saved.route) { SavedScreen(navController) }
                        composable(Screen.My.route) { MyScreen(navController) }
                    }
                }
            }
        }
    }
}

sealed class Screen(val route: String) {
    object Auth : Screen("auth")
    object Home : Screen("home")
    object Station : Screen("station/{stationId}") {
        fun createRoute(id: String): String {
            return "station/$id"
        }
    }

    object MyBookings : Screen("myBookings")
    object Saved : Screen("saved")
    object Settings : Screen("settings")
    object My : Screen("My")
}
