package com.chernybro.exchangerates.feature_rates.presentation.exchange_favourites

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.chernybro.exchangerates.feature_rates.domain.use_case.get_symbols.GetSymbols
import com.chernybro.exchangerates.feature_rates.domain.use_case.sort_rates.GetRates
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel  @Inject constructor(
    private val getRatesUseCase: GetRates,
    private val getSymbolsUseCase: GetSymbols
) : ViewModel() {

    private val _state = mutableStateOf(com.chernybro.exchangerates.feature_rates.presentation.common.State())
    val state: State<com.chernybro.exchangerates.feature_rates.presentation.common.State> = _state

    init {

    }

}
