package com.titicorp.evcs.utils.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun StatusItem(
    status: Status,
) {
    Text(
        modifier = Modifier
            .height(20.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(if (status == Status.Available) Color(0xff6BCB77) else Color(0xffFF6B6B))
            .padding(horizontal = 10.dp),
        text = status.label,
        style = MaterialTheme.typography.labelMedium,
        color = Color.White,
        textAlign = TextAlign.Center,
    )
}

enum class Status(val label: String) {
    Available("Available"), InUse("In Use")
}
