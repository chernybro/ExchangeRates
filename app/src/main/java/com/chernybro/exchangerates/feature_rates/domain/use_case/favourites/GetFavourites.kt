package com.chernybro.exchangerates.feature_rates.domain.use_case.favourites

import com.chernybro.exchangerates.feature_rates.domain.models.Rate
import com.chernybro.exchangerates.feature_rates.domain.repository.FavouriteRatesRepository
import com.chernybro.exchangerates.feature_rates.domain.utils.OrderType
import com.chernybro.exchangerates.feature_rates.domain.utils.RateOrder
import com.chernybro.exchangerates.feature_rates.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavourites @Inject constructor(
    private val repository: FavouriteRatesRepository
) {

    operator fun invoke(base: String, rateOrder: RateOrder): Flow<Resource<List<Rate>>> {
        return repository.getRates(base)
            .map { resource ->
                when (rateOrder.orderType) {
                    is OrderType.Ascending -> {
                        when (rateOrder) {
                            is RateOrder.Code -> resource.data = resource.data?.sortedBy { it.code }
                            is RateOrder.Cost -> resource.data = resource.data?.sortedBy{ it.cost }
                        }
                    }
                    is OrderType.Descending -> {
                        when (rateOrder) {
                            is RateOrder.Code -> resource.data = resource.data?.sortedByDescending { it.code }
                            is RateOrder.Cost -> resource.data = resource.data?.sortedByDescending { it.cost }
                        }
                    }
                }
                resource
            }
    }
}
