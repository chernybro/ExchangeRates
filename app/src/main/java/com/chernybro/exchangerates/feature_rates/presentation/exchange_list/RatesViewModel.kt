package com.chernybro.exchangerates.feature_rates.presentation.exchange_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chernybro.exchangerates.feature_rates.domain.models.Symbol
import com.chernybro.exchangerates.feature_rates.domain.use_case.symbols.GetSymbols
import com.chernybro.exchangerates.feature_rates.domain.use_case.rates.GetRates
import com.chernybro.exchangerates.feature_rates.domain.utils.OrderType
import com.chernybro.exchangerates.feature_rates.domain.utils.RateOrder
import com.chernybro.exchangerates.feature_rates.presentation.common.RatesEvent
import com.chernybro.exchangerates.feature_rates.presentation.common.ScreenState
import com.chernybro.exchangerates.feature_rates.utils.Constants
import com.chernybro.exchangerates.feature_rates.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RatesViewModel @Inject constructor(
    private val getRatesUseCase: GetRates,
    private val getSymbolsUseCase: GetSymbols
) : ViewModel() {

    private val _state = mutableStateOf(com.chernybro.exchangerates.feature_rates.presentation.common.ScreenState())
    val state: State<ScreenState> = _state

    init {
        getRates(Constants.BASE_RATE, RateOrder.Code(OrderType.Ascending))
        getSymbols()
    }

    private fun getRates(base: String, rateOrder: RateOrder) {
        getRatesUseCase(base, rateOrder).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        rates = result.data ?: emptyList(),
                        rateOrder = rateOrder,
                        isLoading = false,
                        selectedSymbol = Symbol(code = base) //  TODO: безобразие
                    )
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        error = result.message ?: "An unexpected error occured",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getSymbols() {
        getSymbolsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        symbols = result.data ?: emptyList(),
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        error = result.message ?: "An unexpected error occured",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: RatesEvent) {
        when (event) {
            is RatesEvent.Order -> {
                if (state.value.rateOrder::class == event.rateOrder::class &&
                    state.value.rateOrder.orderType == event.rateOrder.orderType
                ) {
                    return
                }
                getRates(base = state.value.selectedSymbol.code, rateOrder = event.rateOrder)
            }
            is RatesEvent.Select -> {
                getRates(base = event.symbol.code, rateOrder = state.value.rateOrder)
            }
            /*is RatesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }
            }*/
            is RatesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }
}