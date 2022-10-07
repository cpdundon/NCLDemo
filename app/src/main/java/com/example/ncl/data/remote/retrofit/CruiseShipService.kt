package com.example.ncl.data.remote.retrofit

import com.example.ncl.data.remote.models.CruiseShipResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CruiseShipService {

    @GET("cms-service/cruise-ships/{cruiseShip}")
    suspend fun getCruiseShips(@Path("cruiseShip") cruiseShip: String): Response<CruiseShipResponse>
}
