package com.chernybro.exchangerates.feature_rates.utils

typealias SimpleResource = Resource<Unit>

sealed class Resource<T>(var data: T? = null, val uiText: UiText? = null) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(uiText: UiText, data: T? = null) : Resource<T>(data, uiText)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}
