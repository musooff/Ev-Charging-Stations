package com.titicorp.evcs.data.repository.utils

import com.titicorp.evcs.model.Booking
import com.titicorp.evcs.model.Charger
import com.titicorp.evcs.model.Station
import com.titicorp.evcs.model.StationDetails
import com.titicorp.evcs.network.model.NetworkBooking
import com.titicorp.evcs.network.model.NetworkCharger
import com.titicorp.evcs.network.model.NetworkStation
import com.titicorp.evcs.network.model.NetworkStationDetails

val NetworkStation.toStation
    get() = Station(
        id = this.id,
        title = this.title,
        address = this.address,
        lat = this.lat,
        lng = this.lng,
        thumbnail = this.thumbnail,
        chargers = this.chargers.map { it.toCharger },
    )

val NetworkStationDetails.toStationDetails
    get() = StationDetails(
        id = this.id,
        title = this.title,
        description = this.description,
        address = this.address,
        lat = this.lat,
        lng = this.lng,
        thumbnail = this.thumbnail,
        chargers = this.chargers.map { it.toCharger },
        reviews = this.reviews,
    )

val NetworkCharger.toCharger
    get() = Charger(
        id = this.id,
        name = this.name,
    )

val NetworkBooking.toBooking
    get() = Booking(
        station = this.station.toStation,
        charger = this.charger.toCharger,
        amount = this.amount,
        startedAt = this.startedAt,
        endAt = this.endAt,
    )
