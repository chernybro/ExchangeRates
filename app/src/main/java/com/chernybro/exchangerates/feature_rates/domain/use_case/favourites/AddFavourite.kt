package com.chernybro.exchangerates.feature_rates.domain.use_case.favourites

import com.chernybro.exchangerates.feature_rates.domain.models.Rate
import com.chernybro.exchangerates.feature_rates.domain.repository.FavouritesRepository
import com.chernybro.exchangerates.feature_rates.domain.use_case.sortRates
import com.chernybro.exchangerates.feature_rates.domain.utils.RateOrder
import com.chernybro.exchangerates.feature_rates.utils.Resource
import com.chernybro.exchangerates.feature_rates.utils.SimpleResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AddFavourite @Inject constructor(
    private val repository: FavouritesRepository
) {

    suspend operator fun invoke(base: String, rate: Rate): SimpleResource {
        return repository.insertRate(base = base, rate = rate)
    }

}