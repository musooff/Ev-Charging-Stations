package com.titicorp.evcs.ui.directions

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.titicorp.evcs.model.Station
import com.titicorp.evcs.utils.composables.ScreenTitle


@Composable
fun DirectionsScreen(navController: NavController, stationId: String) {
    val station = Station.byId(stationId)

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(station.lat, station.lng), 15f)
        }
        GoogleMap(
            modifier = Modifier
                .fillMaxSize(),
            cameraPositionState = cameraPositionState,
        ) {
            Marker(
                state = MarkerState(position = LatLng(station.lat, station.lng)),
                onClick = {
                    return@Marker false
                },
            )
        }
        ToolbarLayout(navController)
        val context = LocalContext.current
        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            onClick = {
                val url = "https://www.google.com/maps/dir/?api=1&destination=Madrid,Spain&origin=Barcelona,Spain&travelmode=driving&dir_action=navigate"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            },
        ) {
            Text(text = "Start")
        }
    }
}

@Composable
private fun ToolbarLayout(navController: NavController) {
    Row(
        modifier = Modifier
            .padding(start = 10.dp, top = 10.dp, end = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = { navController.navigateUp() }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
            )
        }
        ScreenTitle("Get Directions")
    }
}
