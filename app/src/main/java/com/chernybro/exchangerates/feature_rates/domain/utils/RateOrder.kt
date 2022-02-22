package com.chernybro.exchangerates.feature_rates.domain.utils

sealed class RateOrder(val orderType: OrderType) {
    class Code(orderType: OrderType): RateOrder(orderType)
    class Cost(orderType: OrderType): RateOrder(orderType)

    fun copy(orderType: OrderType): RateOrder {
        return when(this) {
            is Code -> Code(orderType)
            is Cost -> Cost(orderType)
        }
    }
}
