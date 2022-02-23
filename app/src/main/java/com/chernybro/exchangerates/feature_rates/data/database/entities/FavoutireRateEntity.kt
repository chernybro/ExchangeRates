package com.chernybro.exchangerates.feature_rates.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chernybro.exchangerates.feature_rates.domain.models.Rate

@Entity
data class FavouriteRateEntity(
    val code: String,
    val base: String,
    val cost: Double,
    val isFavourite: Boolean = false,
    @PrimaryKey val id: Int? = null
) {
    fun toRate(): Rate {
        return Rate(
            code = code,
            base = base,
            cost = cost,
            isFavourite = isFavourite
        )
    }
}