package com.chernybro.exchangerates.feature_rates.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chernybro.exchangerates.feature_rates.data.database.dao.FavouriteRateDao
import com.chernybro.exchangerates.feature_rates.data.database.entities.FavouriteRateEntity

@Database(
    entities = [FavouriteRateEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ExchangeRatesDatabase: RoomDatabase() {

    abstract val dao: FavouriteRateDao
}