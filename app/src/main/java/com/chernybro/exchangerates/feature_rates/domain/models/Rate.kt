package com.chernybro.exchangerates.feature_rates.domain.models

import com.chernybro.exchangerates.feature_rates.data.database.entities.FavouriteRateEntity


data class Rate(
    val base : String,
    val code: String,
    val cost : Double,
    val isFavourite: Boolean = false
)

fun Rate.toFavouriteRate(): FavouriteRateEntity {
    return FavouriteRateEntity(
        code = code,
        base = base,
        cost = cost,
        isFavourite = isFavourite
    )
}