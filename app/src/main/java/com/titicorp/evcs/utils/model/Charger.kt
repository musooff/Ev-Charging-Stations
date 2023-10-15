package com.titicorp.evcs.utils.model

import com.titicorp.evcs.R
import com.titicorp.evcs.model.Charger

val Charger.icon: Int
    get() = when (id) {
        Charger.J1772 -> R.drawable.ic_charger_j1772
        Charger.CCS_COMBO_1 -> R.drawable.ic_charger_ccs__combo_1
        Charger.CSS_COMBO_2 -> R.drawable.ic_charger_ccs__combo_2
        Charger.TYPE_2 -> R.drawable.ic_charger_type_2
        Charger.CHAdeMO -> R.drawable.ic_charger_chademo
        Charger.TESLA -> R.drawable.ic_charger_tesla
        Charger.WALL_OUTLET -> R.drawable.ic_charger_wall_outlet
        else -> R.drawable.ic_charger_wall_outlet
    }
