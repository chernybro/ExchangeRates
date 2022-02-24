package com.chernybro.exchangerates.feature_rates.presentation.common

import com.chernybro.exchangerates.feature_rates.domain.models.Rate
import com.chernybro.exchangerates.feature_rates.domain.models.Symbol
import com.chernybro.exchangerates.feature_rates.domain.utils.RateOrder

sealed class Event {
    data class Order(val rateOrder: RateOrder) : Event()
    object ToggleOrderSection : Event()
    data class AddRate(val base: String, val rate: Rate) : Event()
    data class DeleteRate(val rate: Rate) : Event()
    data class SelectRates(val symbol: Symbol) : Event()
    data class SelectFavourites(val symbol: Symbol) : Event()
}
