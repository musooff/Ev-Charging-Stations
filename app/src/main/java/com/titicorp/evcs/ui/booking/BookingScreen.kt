package com.titicorp.evcs.ui.booking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.titicorp.evcs.utils.composables.ScreenTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ToolbarLayout(navController)
            LazyColumn(
                modifier = Modifier
                    .padding(top = 20.dp),
                contentPadding = PaddingValues(bottom = 50.dp)
            ) {
                item {
                    DatePicker(
                        state = rememberDatePickerState(),
                        title = {
                            Text(
                                modifier = Modifier
                                    .padding(start = 20.dp),
                                text = "Select Date",
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                        showModeToggle = false,
                    )
                }
                item {
                    Text(
                        modifier = Modifier
                            .padding(start = 20.dp),
                        text = "Select Time",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                item {
                    TimePickerLayout()
                }
            }
        }

        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            onClick = { /*TODO*/ }) {
            Text(text = "Book")
        }
    }

}

@Composable
private fun ToolbarLayout(navController: NavController) {
    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = {
                navController.navigateUp()
            }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
            )
        }
        ScreenTitle("Booking")
    }
}

@Composable
private fun TimePickerLayout() {
    Column(
        modifier = Modifier
            .padding(start = 20.dp, top = 20.dp, end = 20.dp),
    ) {
        var selectedTime: Int? by remember {
            mutableStateOf(null)
        }
        for (time in 6..20 step 3) {
            TimePickerRow(
                from = time,
                to = time + 2,
                selectedTime = selectedTime,
            ) {
                selectedTime = it
            }
        }

    }
}

@Composable
private fun TimePickerRow(
    from: Int,
    to: Int,
    selectedTime: Int?,
    onItemClicked: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (time in from..to) {
            TimePickerItem(
                time = time,
                selected = time == selectedTime,
            ) {
                onItemClicked(time)
            }
        }
    }
}

@Composable
private fun TimePickerItem(
    time: Int,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val content = @Composable {
        if (time < 10) {
            Text(text = "0$time:00")
        } else {
            Text(text = "$time:00")
        }
    }
    if (selected) {
        Button(
            onClick = onClick,
        ) {
            content()
        }
    } else {
        OutlinedButton(
            onClick = onClick
        ) {
            content()
        }
    }
}