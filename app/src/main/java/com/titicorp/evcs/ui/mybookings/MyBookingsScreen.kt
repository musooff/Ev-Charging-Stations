package com.titicorp.evcs.ui.mybookings

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProvideTextStyle
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.titicorp.evcs.R
import com.titicorp.evcs.Screen
import com.titicorp.evcs.model.Station
import com.titicorp.evcs.utils.composables.ScreenTitle
import com.titicorp.evcs.utils.composables.Status
import com.titicorp.evcs.utils.composables.StatusItem

@Composable
fun MyBookingsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        ToolbarLayout(navController = navController)
        var currentFilter by remember {
            mutableStateOf(Filter.Upcoming)
        }
        FilterLayout(
            modifier = Modifier
                .padding(top = 20.dp),
            selected = currentFilter,
            onItemClicked = {
                currentFilter = it
            },
        )
        Spacer(modifier = Modifier.height(10.dp))
        Divider()
        LazyColumn(
            contentPadding = PaddingValues(20.dp),
        ) {
            itemsIndexed(Station.Nearby) { index, item ->
                CompletedBooking(navController, item)
                if (index != Station.Nearby.size - 1) {
                    Divider(
                        modifier = Modifier.padding(vertical = 10.dp),
                    )
                }
            }
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
        ScreenTitle("My Bookings")
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun FilterLayout(
    modifier: Modifier = Modifier,
    selected: Filter,
    onItemClicked: (Filter) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        listOf(Filter.Upcoming, Filter.Completed).forEach {
            FilterItem(
                modifier = Modifier
                    .weight(1f),
                filter = it,
                selected = it == selected,
            ) {
                onItemClicked(it)
            }
        }
    }
}

@Composable
private fun FilterItem(
    modifier: Modifier = Modifier,
    filter: Filter,
    selected: Boolean,
    onClick: () -> Unit,
) {
    if (selected) {
        FilledTonalButton(
            modifier = modifier,
            onClick = onClick,
        ) {
            Text(text = filter.label)
        }
    } else {
        TextButton(
            modifier = modifier,
            onClick = onClick,
        ) {
            Text(text = filter.label)
        }
    }
}

enum class Filter(val label: String) {
    Upcoming("Upcoming"),
    Completed("Completed"),
}

@Composable
private fun CompletedBooking(navController: NavController, station: Station = Station.Sample) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
        ) {
            Image(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .size(80.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
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
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ProvideTextStyle(value = MaterialTheme.typography.labelMedium) {
                Text(text = "10:00")
                Text(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    text = "·",
                )
                Text(text = "31 Jan 2023")
            }
            Spacer(modifier = Modifier.weight(1f))
            StatusItem(
                status = if (station.title.startsWith("Second")) Status.InUse else Status.Available,
            )
        }
        InfoLayout()
        Spacer(modifier = Modifier.height(10.dp))
        ActionLayout(navController = navController, stationId = station.id)
    }
}

@Composable
private fun InfoLayout() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            Text(
                text = "Tesla(Plug)",
                style = MaterialTheme.typography.bodySmall,
            )
            Icon(
                modifier = Modifier
                    .size(16.dp),
                imageVector = Icons.Outlined.Info,
                contentDescription = null,
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            Text(
                text = "Tesla(Plug)",
                style = MaterialTheme.typography.bodySmall,
            )
            Text(
                text = "Max. Power",
                style = MaterialTheme.typography.labelMedium,
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            Text(
                text = "Duration",
                style = MaterialTheme.typography.bodySmall,
            )
            Text(
                text = "1 hour",
                style = MaterialTheme.typography.labelMedium,
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            Text(
                text = "Amount",
                style = MaterialTheme.typography.bodySmall,
            )
            Text(
                text = "$14.25",
                style = MaterialTheme.typography.labelMedium,
            )
        }
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
            onClick = { navController.navigate(Screen.Booking.route) },
        ) {
            Text(text = "Book Again")
        }
    }
}
