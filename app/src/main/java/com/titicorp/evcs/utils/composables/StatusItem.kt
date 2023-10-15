package com.titicorp.evcs.utils.composables

import androidx.compose.runtime.Composable

@Composable
fun StatusItem(
    status: Status,
) {
    Label(
        kind = when (status) {
            Status.Available -> Kind.Info
            Status.InUse -> Kind.Warning
        },
        text = status.label,
    )
}

enum class Status(val label: String) {
    Available("Available"), InUse("In Use")
}
