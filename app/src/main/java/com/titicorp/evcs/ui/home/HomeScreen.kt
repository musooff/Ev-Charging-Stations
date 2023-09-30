package com.titicorp.evcs.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.titicorp.evcs.R
import com.titicorp.evcs.model.Filter
import com.titicorp.evcs.model.Station

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val stations = Station.Nearby
        var currentStation: Station by remember {
            mutableStateOf(stations.first())
        }
        Column(
            modifier = Modifier
                .padding(top = 10.dp)
        ) {
            ToolbarLayout()
            GreetingLayout()

            var currentFilter by remember {
                mutableStateOf(Filter.Nearby)
            }
            FilterLayout(
                modifier = Modifier
                    .padding(top = 20.dp),
                selected = currentFilter
            ) {
                currentFilter = it
            }


            MapLayout(stations = stations)
        }
        StationLayout(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),
            stations = stations,
        )
    }


}

@Composable
private fun ToolbarLayout() {
    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp)
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun GreetingLayout() {
    Text(
        modifier = Modifier
            .padding(start = 20.dp, top = 20.dp),
        text = "Hello",
        style = MaterialTheme.typography.bodySmall
    )
    Text(
        modifier = Modifier
            .padding(start = 20.dp),
        text = "Thomas Shelby",
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
private fun FilterLayout(
    modifier: Modifier,
    selected: Filter,
    onItemClicked: (Filter) -> Unit
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(Filter.All) {
            FilterItem(
                filter = it,
                selected = it == selected
            ) {
                onItemClicked(it)
            }
        }
    }
}

@Composable
private fun FilterItem(
    filter: Filter,
    selected: Boolean,
    onClick: () -> Unit,
) {
    if (selected) {
        Button(onClick = onClick) {
            Text(text = filter.title)
        }
    } else {
        TextButton(onClick = onClick) {
            Text(text = filter.title)
        }
    }
}

@Composable
private fun StationLayout(
    modifier: Modifier = Modifier,
    stations: List<Station>,
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(stations) {
            StationItem(Station.Sample)
        }
    }
}

@Composable
private fun StationItem(
    station: Station,
) {
    Column(
        modifier = Modifier
            .width(220.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(10.dp),
    ) {
        Image(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .height(80.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer),
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f),
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 10.dp),
                    text = station.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    modifier = Modifier,
                    text = station.address,
                    style = MaterialTheme.typography.bodySmall
                )

                Row(
                    modifier = Modifier
                        .padding(top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier
                            .size(12.dp),
                        imageVector = Icons.Default.Place, contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        modifier = Modifier,
                        text = "1.6km",
                        style = MaterialTheme.typography.labelSmall
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        modifier = Modifier
                            .size(12.dp),
                        imageVector = Icons.Default.Info, contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        modifier = Modifier,
                        text = "5 mins",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            IconButton(
                modifier = Modifier,
                onClick = { /*TODO*/ },
                colors = IconButtonDefaults.filledIconButtonColors(),
            ) {
                Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
            }
        }

    }
}

@Composable
private fun MapLayout(stations: List<Station>) {
    val first = stations.first()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(first.lat, first.lng), 15f)
    }
    Box(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxSize()
    ) {
        GoogleMap(
            modifier = Modifier
                .fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            repeat(stations.size) { index ->
                Marker(
                    state = MarkerState(position = LatLng(stations[index].lat, stations[index].lng)),
                    title = first.title,
                    snippet = first.address,
                    onClick = {
                        return@Marker false
                    },
                )
            }

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(MaterialTheme.colorScheme.surface, Color.Transparent),
                    )
                )
        )
    }
}
