package com.chernybro.exchangerates.data.remote.dto

import com.chernybro.exchangerates.domain.models.Rates
import com.google.gson.annotations.SerializedName

data class LatestRatesDTO(
    @SerializedName("success") val success : Boolean,
    @SerializedName("base") val base : String,
    @SerializedName("date") val date : String,
    @SerializedName("rates") val rates : Rates
)