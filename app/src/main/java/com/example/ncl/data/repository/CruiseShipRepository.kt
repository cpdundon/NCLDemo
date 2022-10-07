package com.example.ncl.data.repository

import com.example.ncl.data.remote.models.CruiseShipResponse
import com.example.ncl.data.remote.retrofit.CruiseShipService
import javax.inject.Inject
import kotlin.Exception

class CruiseShipRepository @Inject constructor(
    private val cruiseShipService: CruiseShipService
) : ICruiseShipRepository {
    override suspend fun getCruiseShips(cruiseShip: String): Result<CruiseShipResponse> {
        return try {
            val response = cruiseShipService.getCruiseShips(cruiseShip)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(""))
            }
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }
}
