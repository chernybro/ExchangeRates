package com.chernybro.exchangerates.feature_rates.domain.use_case.favourites

import com.chernybro.exchangerates.feature_rates.domain.models.Rate
import com.chernybro.exchangerates.feature_rates.domain.repository.FavouriteRatesRepository
import com.chernybro.exchangerates.feature_rates.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavourites @Inject constructor(
    private val repository: FavouriteRatesRepository
) {

    operator fun invoke(): Flow<Resource<List<Rate>>> {
        return repository.getRates()
    }
}
