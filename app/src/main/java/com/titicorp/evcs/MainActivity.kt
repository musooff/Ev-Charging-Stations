package com.titicorp.evcs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.titicorp.evcs.ui.booking.BookingScreen
import com.titicorp.evcs.ui.directions.DirectionsScreen
import com.titicorp.evcs.ui.home.HomeScreen
import com.titicorp.evcs.ui.mybookings.MyBookingsScreen
import com.titicorp.evcs.ui.saved.SavedScreen
import com.titicorp.evcs.ui.settings.SettingsScreen
import com.titicorp.evcs.ui.station.StationScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.Home.route) {
                composable(Screen.Home.route) { HomeScreen(navController) }
                composable("${Screen.Station.route}/{stationId}") { backStackEntry ->
                    StationScreen(
                        navController = navController,
                        stationId = requireNotNull(backStackEntry.arguments?.getString("stationId")),
                    )
                }
                composable(Screen.Booking.route) { BookingScreen(navController) }
                composable("${Screen.Directions.route}/{stationId}") { backStackEntry ->
                    DirectionsScreen(
                        navController = navController,
                        stationId = requireNotNull(backStackEntry.arguments?.getString("stationId")),
                    )
                }
                composable(Screen.MyBookings.route) { MyBookingsScreen(navController) }
                composable(Screen.Settings.route) { SettingsScreen(navController) }
                composable(Screen.Saved.route) { SavedScreen(navController) }
            }
        }
    }
}

enum class Screen(val route: String) {
    Home("home"),
    Station("station"),
    Booking("booking"),
    Directions("directions"),
    MyBookings("myBookings"),
    Saved("saved"),
    Settings("settings"),
}
