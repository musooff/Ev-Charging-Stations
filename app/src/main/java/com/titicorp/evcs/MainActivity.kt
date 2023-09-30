package com.titicorp.evcs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.titicorp.evcs.ui.home.HomeScreen
import com.titicorp.evcs.ui.station.StationScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //HomeScreen()
            StationScreen()
        }
    }
}