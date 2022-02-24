package com.chernybro.exchangerates.feature_rates.domain.models

import com.chernybro.exchangerates.feature_rates.data.database.entities.FavouriteRateEntity


data class Rate(
    val base : String,
    val code: String,
    val cost : Double,
    var isFavourite: Boolean = false
) {
    override fun equals(other: Any?): Boolean = (other is Rate) && (code == other.code)

    override fun hashCode(): Int {
        val prime = 31
        return prime +
                prime * base.hashCode() +
                prime * code.hashCode() +
                prime * cost.hashCode()
    }
}

fun Rate.toFavouriteRate(): FavouriteRateEntity {
    return FavouriteRateEntity(
        code = code,
        base = base,
        cost = cost,
        isFavourite = true
    )
}