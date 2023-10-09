package com.titicorp.evcs.ui.station.details

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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.ripple.rememberRipple
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
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.titicorp.evcs.model.StationDetails
import com.titicorp.evcs.ui.station.StationScreen
import com.titicorp.evcs.ui.station.StationViewModel

@Composable
fun StationDetailsScreen(
    navController: NavController,
    state: StationViewModel.UiState.Success,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        var currentTab by remember {
            mutableStateOf(Tab.Info)
        }
        val lazyListState = rememberLazyListState()
        LazyColumn(
            state = lazyListState,
        ) {
            item {
                AppBarLayout(state.data)
            }

            item {
                ActionLayout(navController)
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
                    Tab.Info -> InfoLayout(state.data.description)
                    Tab.Chargers -> ChargersLayout()
                    Tab.Reviews -> Reviews()
                }
            }
        }
        ToolbarLayout(navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ToolbarLayout(navController: NavController) {
    TopAppBar(
        title = { },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
        ),
        navigationIcon = {
            IconButton(onClick = {
                navController.navigateUp()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White,
                )
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = null,
                    tint = Color.White,
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = Color.White,
                )
            }
        },
    )
}

@Composable
private fun AppBarLayout(
    details: StationDetails,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondaryContainer),
            model = details.thumbnail,
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(30.dp)
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
                text = details.title,
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(
                text = details.address,
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
private fun ActionLayout(navController: NavController) {
    Row(
        modifier = Modifier
            .padding(start = 20.dp, top = 20.dp, end = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Button(
            modifier = Modifier
                .weight(1f),
            onClick = { navController.navigate(StationScreen.Directions.route) },
        ) {
            Text(text = "Get Directions")
        }
        OutlinedButton(
            modifier = Modifier
                .weight(1f),
            onClick = { navController.navigate(StationScreen.Booking.route) },
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
private fun InfoLayout(description: String) {
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
            text = description,
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
            imageVector = Icons.Outlined.Info,
            contentDescription = null,
        )
        Column(
            modifier = Modifier
                .padding(start = 10.dp)
                .weight(1f),
        ) {
            Text(
                text = "Tesla",
                style = MaterialTheme.typography.bodySmall,
            )
            Row {
                ProvideTextStyle(value = MaterialTheme.typography.labelMedium) {
                    Text(text = "100 kW")
                    Text(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        text = "·",
                    )
                    Text(text = "Max.Power")
                }
            }
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
