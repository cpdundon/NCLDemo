package com.example.ncl.data.repository

import com.example.ncl.data.remote.models.CruiseShipResponse

interface ICruiseShipRepository {
    suspend fun getCruiseShips(cruiseShip: String): Result<CruiseShipResponse>
}