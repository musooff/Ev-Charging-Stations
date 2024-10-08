package com.titicorp.evcs.ui.saved

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.titicorp.evcs.Screen
import com.titicorp.evcs.model.Charger
import com.titicorp.evcs.model.Station
import com.titicorp.evcs.utils.composables.Status
import com.titicorp.evcs.utils.composables.StatusItem
import com.titicorp.evcs.utils.composables.TopBarTitle
import com.titicorp.evcs.utils.model.icon

@Composable
fun SavedScreen(
    navController: NavController,
    viewModel: SavedViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        ToolbarLayout(navController = navController)
        val stations by viewModel.stations.collectAsState()
        LazyColumn(
            contentPadding = PaddingValues(20.dp),
        ) {
            itemsIndexed(stations) { index, item ->
                SavedItem(navController, item)
                if (index != stations.size - 1) {
                    Divider(
                        modifier = Modifier.padding(vertical = 10.dp),
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ToolbarLayout(navController: NavController) {
    TopAppBar(
        title = {
            TopBarTitle("Saved")
        },
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
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                )
            }
        },

    )
}

@Composable
private fun SavedItem(navController: NavController, station: Station) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
        ) {
            AsyncImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .size(80.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                model = station.thumbnail,
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.size(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 10.dp),
            ) {
                Text(
                    text = station.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = station.address,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier
                            .size(15.dp),
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color.Magenta,
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    ProvideTextStyle(value = MaterialTheme.typography.bodySmall) {
                        Text(text = "4.4")
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(text = "(98 Reviews)")
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFFEEEEEE))
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier
                    .size(12.dp),
                imageVector = Icons.Default.Place,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                modifier = Modifier,
                text = "1.6km",
                style = MaterialTheme.typography.labelSmall,
            )
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                modifier = Modifier
                    .size(12.dp),
                imageVector = Icons.Default.Info,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                modifier = Modifier,
                text = "5 mins",
                style = MaterialTheme.typography.labelSmall,
            )
            Spacer(modifier = Modifier.weight(1f))
            StatusItem(
                status = if (station.title.startsWith("Exxon")) Status.InUse else Status.Available,
            )
        }
        ChargersLayout(station.chargers)
        Spacer(modifier = Modifier.height(10.dp))
        ActionLayout(navController = navController, stationId = station.id)
    }
}

@Composable
private fun ChargersLayout(chargers: List<Charger>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            chargers.forEach {
                Icon(
                    painter = painterResource(id = it.icon),
                    contentDescription = null,
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "${chargers.size} Chargers",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.secondary,
        )
    }
}

@Composable
private fun ActionLayout(navController: NavController, stationId: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        OutlinedButton(
            modifier = Modifier
                .weight(1f),
            onClick = { },
        ) {
            Text(text = "View")
        }
        Button(
            modifier = Modifier
                .weight(1f),
            onClick = { navController.navigate(Screen.Station.createRoute(stationId)) },
        ) {
            Text(text = "Book")
        }
    }
}
