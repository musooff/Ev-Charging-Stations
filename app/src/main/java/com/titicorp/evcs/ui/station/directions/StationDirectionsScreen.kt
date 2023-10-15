package com.titicorp.evcs.ui.station.directions

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.titicorp.evcs.ui.station.StationViewModel
import com.titicorp.evcs.utils.composables.TopBarTitle

@Composable
fun StationDirectionsScreen(
    navController: NavController,
    state: StationViewModel.UiState.Success,
) {
    val details = state.data
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        val originLatLng = LatLng(38.5616262920049, 68.80014529107163)
        val destinationLatLng = LatLng(details.lat, details.lng)
        val centerLatLng = LatLng((originLatLng.latitude + destinationLatLng.latitude) / 2, (destinationLatLng.longitude + originLatLng.longitude) / 2)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(centerLatLng, 13f)
        }
        GoogleMap(
            modifier = Modifier
                .fillMaxSize(),
            cameraPositionState = cameraPositionState,
        ) {
            Marker(
                state = MarkerState(position = originLatLng),
                onClick = {
                    return@Marker false
                },
            )
            Marker(
                state = MarkerState(position = destinationLatLng),
                onClick = {
                    return@Marker false
                },
            )
            Polyline(
                points = listOf(originLatLng, destinationLatLng),
                width = 10f,
                color = MaterialTheme.colorScheme.primary,
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
                val url =
                    "https://www.google.com/maps/dir/?api=1&destination=${originLatLng.latitude},${originLatLng.longitude}&origin=${destinationLatLng.latitude},${destinationLatLng.longitude}&travelmode=driving&dir_action=navigate"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            },
        ) {
            Text(text = "Start")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ToolbarLayout(navController: NavController) {
    TopAppBar(
        title = { TopBarTitle("Get Directions") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
        ),
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                )
            }
        },
    )
}
