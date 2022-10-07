package com.example.ncl.data.remote.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CruiseShipResponse(
    val shipFacts: ShipFacts,
    val shipName: String,
) {
    @JsonClass(generateAdapter = true)
    data class ShipFacts(
        val crew: String,
        val inauguralDate: String,
        val passengerCapacity: String,
    )
}
