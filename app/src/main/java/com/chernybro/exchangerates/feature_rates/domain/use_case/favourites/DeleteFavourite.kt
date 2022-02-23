package com.chernybro.exchangerates.feature_rates.domain.use_case.favourites

import com.chernybro.exchangerates.feature_rates.domain.models.Rate
import com.chernybro.exchangerates.feature_rates.domain.repository.FavouriteRatesRepository
import com.chernybro.exchangerates.feature_rates.utils.SimpleResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteFavourite @Inject constructor(
    private val repository: FavouriteRatesRepository
) {

    operator fun invoke(rate: Rate): Flow<SimpleResource> {
        return repository.deleteRate(rate = rate)
    }

}