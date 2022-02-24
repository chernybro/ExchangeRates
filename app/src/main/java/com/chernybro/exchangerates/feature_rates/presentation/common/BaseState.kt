package com.chernybro.exchangerates.feature_rates.presentation.common

import com.chernybro.exchangerates.feature_rates.domain.models.Rate
import com.chernybro.exchangerates.feature_rates.domain.models.Symbol
import com.chernybro.exchangerates.feature_rates.domain.utils.OrderType
import com.chernybro.exchangerates.feature_rates.domain.utils.RateOrder
import com.chernybro.exchangerates.feature_rates.utils.Constants

data class BaseState(
    val isLoading: Boolean = false,
    val rates: List<Rate> = emptyList(),
    val favourites: List<Rate> = emptyList(),
    val error: String = "",
    val symbols: List<Symbol> = emptyList(),
    val rateOrder: RateOrder = RateOrder.Code(OrderType.Ascending),
    val isOrderSectionVisible: Boolean = false,
    val selectedSymbol: Symbol = Symbol(code = Constants.BASE_RATE),
    val isRefresging: Boolean = false
)