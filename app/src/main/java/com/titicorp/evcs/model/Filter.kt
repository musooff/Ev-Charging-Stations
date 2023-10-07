package com.titicorp.evcs.model

enum class Filter(
    val label: String,
) {
    Nearby("Nearby"),
    Favorite("Favorite"),
    Recent("Recent"),
    Famous("Famous"),
    ;

    companion object {
        val All = listOf(Nearby, Favorite, Recent, Famous)
    }
}
