package com.chernybro.exchangerates.feature_rates.domain.models


data class Rate(
    val base : String,
    val code: String,
    val cost : Double
)