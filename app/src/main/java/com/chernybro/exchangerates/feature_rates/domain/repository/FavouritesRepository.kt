package com.chernybro.exchangerates.feature_rates.domain.repository

import com.chernybro.exchangerates.feature_rates.domain.models.Rate
import com.chernybro.exchangerates.feature_rates.utils.Resource
import com.chernybro.exchangerates.feature_rates.utils.SimpleResource
import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {

    fun getRates(base: String): Flow<Resource<List<Rate>>>

    suspend fun insertRate(base: String, rate: Rate): SimpleResource

    suspend fun deleteRate(rate: Rate): SimpleResource
}