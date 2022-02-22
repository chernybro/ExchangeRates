package com.chernybro.exchangerates.feature_rates.presentation.components

import com.chernybro.exchangerates.feature_rates.domain.models.Rate
import com.chernybro.exchangerates.feature_rates.domain.models.Symbol
import com.chernybro.exchangerates.feature_rates.domain.utils.OrderType
import com.chernybro.exchangerates.feature_rates.domain.utils.RateOrder

data class ListState(
    val isLoading: Boolean = false,
    val rates: List<Rate> = emptyList(),
    val error: String = "",
    val rateOrder: RateOrder = RateOrder.Code(OrderType.Ascending),
    val isOrderSectionVisible: Boolean = false,
    val symbols: List<Symbol> = emptyList()
)
