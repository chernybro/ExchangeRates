package com.chernybro.exchangerates.feature_rates.presentation.common

import com.chernybro.exchangerates.feature_rates.domain.models.Rate
import com.chernybro.exchangerates.feature_rates.domain.models.Symbol
import com.chernybro.exchangerates.feature_rates.domain.utils.RateOrder

sealed class RatesEvent {
    data class Order(val rateOrder: RateOrder): RatesEvent()
    data class Select(val symbol: Symbol): RatesEvent()
    data class AddRate(val base: String, val rate: Rate): RatesEvent()
    data class DeleteRate(val rate: Rate): RatesEvent()
    object ToggleOrderSection: RatesEvent()
}
