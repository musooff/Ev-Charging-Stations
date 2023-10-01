package com.titicorp.evcs.ui.station

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.titicorp.evcs.R
import com.titicorp.evcs.Screen
import com.titicorp.evcs.model.Station

@Composable
fun StationScreen(navController: NavController, stationId: String) {
    val station = Station.byId(stationId)
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        var currentTab by remember {
            mutableStateOf(Tab.Info)
        }
        LazyColumn {
            item {
                AppBarLayout(station)
            }

            item {
                ActionLayout(navController, stationId)
            }

            item {
                TabLayout(
                    tabs = Tab.All,
                    selected = currentTab,
                ) {
                    currentTab = it
                }
            }

            item {
                when (currentTab) {
                    Tab.Info -> InfoLayout()
                    Tab.Chargers -> ChargersLayout()
                    Tab.Reviews -> Reviews()
                }
            }
        }
        ToolbarLayout(navController)
    }
}

@Composable
private fun ToolbarLayout(navController: NavController) {
    Row(
        modifier = Modifier
            .padding(start = 10.dp, top = 10.dp, end = 10.dp),
    ) {
        IconButton(onClick = {
            navController.navigateUp()
        }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = null,
            )
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun AppBarLayout(
    station: Station,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondaryContainer),
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(50.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, MaterialTheme.colorScheme.surface),
                    ),
                ),
        )
    }
    Row(
        modifier = Modifier
            .padding(start = 20.dp, top = 20.dp, end = 20.dp),
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
        ) {
            Text(
                text = station.title,
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(
                text = station.address,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
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
            Text(
                modifier = Modifier,
                text = "4.4",
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
    Row(
        modifier = Modifier
            .padding(top = 10.dp, start = 20.dp),
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
    }
}

@Composable
private fun ActionLayout(navController: NavController, stationId: String) {
    Row(
        modifier = Modifier
            .padding(start = 20.dp, top = 20.dp, end = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Button(
            modifier = Modifier
                .weight(1f),
            onClick = { navController.navigate("${Screen.Directions.route}/$stationId") },
        ) {
            Text(text = "Get Directions")
        }
        OutlinedButton(
            modifier = Modifier
                .weight(1f),
            onClick = { navController.navigate(Screen.Booking.route) },
        ) {
            Text(text = "Book")
        }
    }
}

enum class Tab(val label: String) {
    Info("Info"),
    Chargers("Chargers"),
    Reviews("Reviews"),
    ;

    companion object {
        val All = listOf(Info, Chargers, Reviews)
    }
}

@Composable
private fun TabLayout(
    tabs: List<Tab>,
    selected: Tab,
    onItemClicked: (Tab) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .padding(top = 10.dp),
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(tabs) {
            TabItem(
                title = it.label,
                selected = selected == it,
            ) {
                onItemClicked(it)
            }
        }
    }
}

@Composable
private fun TabItem(
    title: String,
    selected: Boolean = false,
    onClick: () -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
            )
            .padding(vertical = 10.dp),
    ) {
        val (label, indicator) = createRefs()

        Text(
            modifier = Modifier
                .constrainAs(label) {
                    top.linkTo(parent.top)
                },
            text = title,
            style = MaterialTheme.typography.titleSmall,
        )
        Box(
            modifier = Modifier
                .constrainAs(indicator) {
                    width = Dimension.fillToConstraints
                    top.linkTo(label.bottom)
                    start.linkTo(label.start)
                    end.linkTo(label.end)
                }
                .height(3.dp)
                .background(if (selected) MaterialTheme.colorScheme.primary else Color.Transparent),
        )
    }
}

@Composable
private fun InfoLayout() {
    Column(
        modifier = Modifier
            .padding(start = 20.dp, top = 20.dp, end = 20.dp),
    ) {
        Text(
            text = "About",
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = LoremIpsum(100).values.first().toString(),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun ChargersLayout() {
    Column(
        modifier = Modifier
            .padding(start = 20.dp, top = 20.dp, end = 20.dp),
    ) {
        ChargerItem()
        Divider(
            color = Color.LightGray,
            thickness = 0.5.dp,
        )
        ChargerItem()
        Divider(
            color = Color.LightGray,
            thickness = 0.5.dp,
        )
        ChargerItem()
        Divider(
            color = Color.LightGray,
            thickness = 0.5.dp,
        )
        ChargerItem()
    }
}

@Composable
private fun ChargerItem() {
    Row(
        modifier = Modifier
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier
                .size(30.dp),
            imageVector = Icons.Default.Info,
            contentDescription = null,
            tint = Color.Gray,
        )
        Column(
            modifier = Modifier
                .padding(start = 10.dp)
                .weight(1f),
        ) {
            Text(
                text = "Tesla",
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = "100 kW",
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}

@Composable
private fun Reviews() {
    Text(
        modifier = Modifier
            .padding(start = 20.dp, top = 20.dp, end = 20.dp),
        text = "WIP",
        style = MaterialTheme.typography.titleMedium,
    )
}
