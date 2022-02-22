package com.chernybro.exchangerates.feature_rates.domain.utils

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
