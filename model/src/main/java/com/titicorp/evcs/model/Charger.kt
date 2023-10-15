package com.titicorp.evcs.model

data class Charger(
    val id: String,
    val name: String,
) {
    companion object {
        const val J1772 = "j1772"
        const val CCS_COMBO_1 = "css+combo_1"
        const val CSS_COMBO_2 = "css_combo_2"
        const val TYPE_2 = "type_2"
        const val CHAdeMO = "chademo"
        const val TESLA = "tesla"
        const val WALL_OUTLET = "wall_outlet"

        val All = listOf(
            Charger(
                id = J1772,
                name = "J1772",
            ),
            Charger(
                id = CCS_COMBO_1,
                name = "CSS (Combo 1)",
            ),
            Charger(
                id = CSS_COMBO_2,
                name = "CSS (Combo 2)",
            ),
            Charger(
                id = TYPE_2,
                name = "Type 2",
            ),
            Charger(
                id = CHAdeMO,
                name = "CHAdeMo",
            ),
            Charger(
                id = TESLA,
                name = "Tesla",
            ),
            Charger(
                id = WALL_OUTLET,
                name = "Wall outlet",
            ),
        )
    }
}
