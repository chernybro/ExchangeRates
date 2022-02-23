package com.chernybro.exchangerates.feature_rates.domain.repository

import com.chernybro.exchangerates.feature_rates.domain.models.Rate
import com.chernybro.exchangerates.feature_rates.utils.Resource
import com.chernybro.exchangerates.feature_rates.utils.SimpleResource
import kotlinx.coroutines.flow.Flow

interface FavouriteRatesRepository {

    fun getRates(base: String): Flow<Resource<List<Rate>>>

    fun insertRate(base: String, rate: Rate): Flow<Resource<List<Rate>>>

    fun deleteRate(rate: Rate): Flow<Resource<List<Rate>>>
}