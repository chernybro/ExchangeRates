package com.chernybro.exchangerates.feature_rates.data.database.dao

import androidx.room.*
import com.chernybro.exchangerates.feature_rates.data.database.entities.FavouriteRateEntity

@Dao
interface FavouriteRateDao {

    @Query("SELECT * FROM FavouriteRateEntity")
    suspend fun getRates(): List<FavouriteRateEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRate(rate: FavouriteRateEntity)

    @Query("DELETE FROM FavouriteRateEntity WHERE code = :code")
    suspend fun deleteRate(code: String)


}