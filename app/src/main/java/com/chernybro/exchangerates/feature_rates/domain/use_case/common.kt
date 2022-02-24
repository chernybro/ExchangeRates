package com.chernybro.exchangerates.feature_rates.domain.use_case

import com.chernybro.exchangerates.feature_rates.domain.models.Rate
import com.chernybro.exchangerates.feature_rates.domain.utils.OrderType
import com.chernybro.exchangerates.feature_rates.domain.utils.RateOrder
import com.chernybro.exchangerates.feature_rates.utils.Resource

fun sortRates(resource: Resource<List<Rate>>, rateOrder: RateOrder) {
    when (rateOrder.orderType) {
        is OrderType.Ascending -> {
            when (rateOrder) {
                is RateOrder.Code -> resource.data = resource.data?.sortedBy { it.code }
                is RateOrder.Cost -> resource.data = resource.data?.sortedBy { it.cost }
            }
        }
        is OrderType.Descending -> {
            when (rateOrder) {
                is RateOrder.Code -> resource.data = resource.data?.sortedByDescending { it.code }
                is RateOrder.Cost -> resource.data = resource.data?.sortedByDescending { it.cost }
            }
        }
    }
}