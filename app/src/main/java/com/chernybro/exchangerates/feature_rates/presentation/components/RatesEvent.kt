package com.chernybro.exchangerates.feature_rates.presentation.components

import com.chernybro.exchangerates.feature_rates.domain.utils.RateOrder

sealed class RatesEvent {
    data class Order(val rateOrder: RateOrder): RatesEvent()
    //data class DeleteNote(val note: Rate): RatesEvent()
    object ToggleOrderSection: RatesEvent()
}
