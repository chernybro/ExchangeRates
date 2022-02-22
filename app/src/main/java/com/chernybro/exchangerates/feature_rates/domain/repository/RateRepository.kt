package com.chernybro.exchangerates.feature_rates.domain.repository

import com.chernybro.exchangerates.feature_rates.domain.models.Rate
import com.chernybro.exchangerates.feature_rates.utils.Resource
import kotlinx.coroutines.flow.Flow

interface RateRepository {

    fun getRates(base: String): Flow<Resource<List<Rate>>>

}