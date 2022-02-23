package com.chernybro.exchangerates.feature_rates.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chernybro.exchangerates.feature_rates.data.database.dao.FavouriteRateDao
import com.chernybro.exchangerates.feature_rates.domain.models.Rate

@Database(
    entities = [Rate::class],
    version = 1
)
abstract class ExchangeRatesDatabase: RoomDatabase() {

    abstract val dao: FavouriteRateDao
}