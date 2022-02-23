package com.chernybro.exchangerates.feature_rates.presentation.exchange_favourites

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.chernybro.exchangerates.feature_rates.domain.use_case.symbols.GetSymbols
import com.chernybro.exchangerates.feature_rates.domain.use_case.rates.GetRates
import com.chernybro.exchangerates.feature_rates.presentation.common.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel  @Inject constructor(
    private val getRatesUseCase: GetRates,
    private val getSymbolsUseCase: GetSymbols
) : ViewModel() {

    private val _state = mutableStateOf(ScreenState())
    val state: State<ScreenState> = _state

    init {

    }

}
