package com.chernybro.exchangerates.data.remote

import com.chernybro.exchangerates.data.remote.dto.LatestRatesDTO
import retrofit2.http.GET
import retrofit2.http.Query


interface ExchangeRatesApi {

    @GET("/latest")
    fun getLatestRates(
        @Query("base") base: String?
    ): LatestRatesDTO?
}