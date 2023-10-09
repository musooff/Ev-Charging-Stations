package com.titicorp.evcs.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.titicorp.evcs.Screen
import com.titicorp.evcs.model.Filter
import com.titicorp.evcs.model.Station
import com.titicorp.evcs.utils.composables.Loading
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerSheet(navController = navController, drawerState = drawerState)
        },
        gesturesEnabled = drawerState.isOpen,
        content = {
            Content(
                navController = navController,
                drawerState = drawerState,
                viewModel = viewModel,
            )
        },
    )
}

@Composable
private fun DrawerSheet(
    navController: NavController,
    drawerState: DrawerState,
) {
    val scope = rememberCoroutineScope()
    ModalDrawerSheet(
        modifier = Modifier
            .width(320.dp),
    ) {
        GreetingLayout()
        Spacer(modifier = Modifier.height(20.dp))
        NavigationDrawerItem(
            label = {
                Text(
                    text = "My Bookings",
                    style = MaterialTheme.typography.titleMedium,
                )
            },
            icon = {
                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null)
            },
            selected = false,
            onClick = {
                scope.launch {
                    drawerState.close()
                }
                navController.navigate(Screen.MyBookings.route)
            },
        )
        NavigationDrawerItem(
            label = {
                Text(
                    text = "Saved",
                    style = MaterialTheme.typography.titleMedium,
                )
            },
            icon = {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
            },
            selected = false,
            onClick = {
                scope.launch {
                    drawerState.close()
                }
                navController.navigate(Screen.Saved.route)
            },
        )
        NavigationDrawerItem(
            label = {
                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.titleMedium,
                )
            },
            icon = {
                Icon(imageVector = Icons.Default.Settings, contentDescription = null)
            },
            selected = false,
            onClick = {
                scope.launch {
                    drawerState.close()
                }
                navController.navigate(Screen.Settings.route)
            },
        )
        Spacer(modifier = Modifier.weight(1f))
        NavigationDrawerItem(
            label = {
                Text(
                    text = "Sign Out",
                    style = MaterialTheme.typography.titleMedium,
                )
            },
            icon = {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            },
            selected = false,
            onClick = { /*TODO*/ },
        )
    }
}

@Composable
private fun Content(
    navController: NavController,
    drawerState: DrawerState,
    viewModel: HomeViewModel,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        val uiState by viewModel.uiState.collectAsState()
        when (val state = uiState) {
            HomeViewModel.UiState.Loading -> {
                Loading()
            }

            is HomeViewModel.UiState.Stations -> {
                var currentStation: Station by remember {
                    mutableStateOf(state.data.first())
                }
                Column {
                    val scope = rememberCoroutineScope()
                    ToolbarLayout(
                        onMenuClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        },
                        onMyClick = {
                            navController.navigate(Screen.My.route)
                        },
                    )
                    GreetingLayout()

                    var currentFilter by remember {
                        mutableStateOf(Filter.Nearby)
                    }
                    FilterLayout(
                        modifier = Modifier
                            .padding(top = 20.dp),
                        selected = currentFilter,
                    ) {
                        currentFilter = it
                    }

                    MapLayout(
                        stations = state.data,
                        selected = currentStation,
                        onItemClick = {
                            currentStation = it
                        },
                    )
                }
                StationLayout(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 20.dp),
                    stations = state.data,
                    selected = currentStation,
                    onItemClick = {
                        navController.navigate(Screen.Station.createRoute(currentStation.id))
                    },
                    onItemFocus = {
                        currentStation = it
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ToolbarLayout(
    onMenuClick: () -> Unit,
    onMyClick: () -> Unit,
) {
    TopAppBar(
        title = { /*TODO*/ },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
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
            IconButton(onClick = onMyClick) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                )
            }
        },
    )
}

@Composable
private fun GreetingLayout() {
    Text(
        modifier = Modifier
            .padding(start = 20.dp, top = 20.dp),
        text = "Hello",
        style = MaterialTheme.typography.bodySmall,
    )
    Text(
        modifier = Modifier
            .padding(start = 20.dp),
        text = "Thomas Shelby",
        style = MaterialTheme.typography.headlineSmall,
    )
}

@Composable
private fun FilterLayout(
    modifier: Modifier,
    selected: Filter,
    onItemClicked: (Filter) -> Unit,
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(Filter.All) {
            FilterItem(
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
    filter: Filter,
    selected: Boolean,
    onClick: () -> Unit,
) {
    if (selected) {
        Button(onClick = onClick) {
            Text(text = filter.label)
        }
    } else {
        TextButton(onClick = onClick) {
            Text(text = filter.label)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun StationLayout(
    modifier: Modifier = Modifier,
    stations: List<Station>,
    selected: Station,
    onItemClick: (Station) -> Unit,
    onItemFocus: (Station) -> Unit,
) {
    val pagerState = rememberPagerState(
        initialPage = stations.indexOf(selected),
        pageCount = { stations.size },
    )
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .collect { page ->
                onItemFocus(stations[page])
            }
    }
    if (stations.indexOf(selected) != pagerState.currentPage) {
        LaunchedEffect(selected) {
            pagerState.scrollToPage(stations.indexOf(selected))
        }
    }
    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 60.dp),
        pageSpacing = 20.dp,
    ) { page ->
        val station = stations[page]
        StationItem(station = station) {
            onItemClick(station)
        }
    }
}

@Composable
private fun StationItem(
    station: Station,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(10.dp),
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .height(80.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer),
            model = station.thumbnail,
            contentScale = ContentScale.Crop,
            contentDescription = null,
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
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    modifier = Modifier,
                    text = station.address,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Row(
                    modifier = Modifier
                        .padding(top = 10.dp),
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
            IconButton(
                modifier = Modifier,
                onClick = onClick,
                colors = IconButtonDefaults.filledIconButtonColors(),
            ) {
                Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
            }
        }
    }
}

@Composable
private fun MapLayout(
    stations: List<Station>,
    selected: Station,
    onItemClick: (Station) -> Unit,
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(selected.lat, selected.lng), 15f)
    }
    val currentPosition = cameraPositionState.position.target
    if (currentPosition.latitude != selected.lat || currentPosition.longitude != selected.lng) {
        LaunchedEffect(selected) {
            cameraPositionState.animate(
                CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(LatLng(selected.lat, selected.lng), 15f)),
            )
        }
    }
    Box(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxSize(),
    ) {
        GoogleMap(
            modifier = Modifier
                .fillMaxSize(),
            cameraPositionState = cameraPositionState,
        ) {
            repeat(stations.size) { index ->
                val station = stations[index]
                Marker(
                    state = MarkerState(position = LatLng(station.lat, station.lng)),
                    onClick = {
                        onItemClick(station)
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
                    ),
                ),
        )
    }
}
