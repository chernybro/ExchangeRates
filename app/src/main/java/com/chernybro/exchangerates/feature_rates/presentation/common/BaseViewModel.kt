package com.chernybro.exchangerates.feature_rates.presentation.common

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    abstract fun onEvent(event: RatesEvent)
}