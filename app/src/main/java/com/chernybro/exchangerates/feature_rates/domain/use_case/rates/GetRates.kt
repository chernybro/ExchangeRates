package com.chernybro.exchangerates.feature_rates.domain.use_case.rates

import com.chernybro.exchangerates.feature_rates.domain.models.Rate
import com.chernybro.exchangerates.feature_rates.domain.repository.RateRepository
import com.chernybro.exchangerates.feature_rates.domain.use_case.sortRates
import com.chernybro.exchangerates.feature_rates.domain.utils.RateOrder
import com.chernybro.exchangerates.feature_rates.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class GetRates @Inject constructor(
    private val repository: RateRepository
) {

    operator fun invoke(base: String, rateOrder: RateOrder): Flow<Resource<List<Rate>>> {
        return repository.getRates(base)
            .map { resource ->
                sortRates(resource, rateOrder)
                resource
            }
    }
}

