package com.chernybro.exchangerates.feature_rates.data.remote.dto

import com.chernybro.exchangerates.feature_rates.domain.models.Rate
import com.google.gson.annotations.SerializedName

data class LatestRatesDTO(
    @SerializedName("base") val base : String,
    @SerializedName("date") val date : String,
    @SerializedName("rates") val rates : Map<String, Double>
)

fun LatestRatesDTO.toRates() : List<Rate> {
    return rates.entries
        .map {
            Rate(
                base = base,
                code = it.key,
                cost = it.value
            )
        }
}