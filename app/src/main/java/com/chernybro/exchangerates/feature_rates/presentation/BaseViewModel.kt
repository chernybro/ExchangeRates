package com.chernybro.exchangerates.feature_rates.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.chernybro.exchangerates.feature_rates.domain.models.Symbol
import com.chernybro.exchangerates.feature_rates.domain.use_case.favourites.AddFavourite
import com.chernybro.exchangerates.feature_rates.domain.use_case.favourites.DeleteFavourite
import com.chernybro.exchangerates.feature_rates.domain.use_case.favourites.GetFavourites
import com.chernybro.exchangerates.feature_rates.domain.use_case.rates.GetRates
import com.chernybro.exchangerates.feature_rates.domain.use_case.symbols.GetSymbols
import com.chernybro.exchangerates.feature_rates.domain.utils.OrderType
import com.chernybro.exchangerates.feature_rates.domain.utils.RateOrder
import com.chernybro.exchangerates.feature_rates.presentation.common.BaseState
import com.chernybro.exchangerates.feature_rates.presentation.common.Event
import com.chernybro.exchangerates.feature_rates.utils.Constants
import com.chernybro.exchangerates.feature_rates.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(
    private val getRatesUseCase: GetRates,
    private val getFavouritesUseCase: GetFavourites,
    private val deleteFavouriteUseCase: DeleteFavourite,
    private val getSymbolsUseCase: GetSymbols,
    private val addFavouriteUseCase: AddFavourite
) : ViewModel() {

    private val _state = mutableStateOf(BaseState())
    val state: State<BaseState> = _state

    init {
        getRates(Constants.BASE_RATE, RateOrder.Code(OrderType.Ascending))
        getFavourites(Constants.BASE_RATE, RateOrder.Code(OrderType.Ascending))
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
                        selectedSymbol = Symbol(code = base)
                    )
                }
                is Resource.Error -> {
                   // _eventFlow.emit(UiEvent.ShowSnackbar(errorUiText)) // TODO: Забирать в compose
                }
                is Resource.Loading -> {
                    _state.value = state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getFavourites(base: String, rateOrder: RateOrder) {
        getFavouritesUseCase(base, rateOrder).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        favourites = result.data ?: emptyList(),
                        rateOrder = rateOrder,
                        isLoading = false,
                        selectedSymbol = Symbol(code = base)
                    )
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        // _eventFlow.emit(UiEvent.ShowSnackbar(errorUiText)) // TODO: Забирать в compose
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
                        // _eventFlow.emit(UiEvent.ShowSnackbar(errorUiText)) // TODO: Забирать в compose
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.Order -> {
                if (state.value.rateOrder::class == event.rateOrder::class &&
                    state.value.rateOrder.orderType == event.rateOrder.orderType
                ) {
                    return
                }
                getRates(base = state.value.selectedSymbol.code, rateOrder = event.rateOrder)
            }
            is Event.SelectRates -> {
                getRates(base = event.symbol.code, rateOrder = state.value.rateOrder)
            }
            is Event.SelectFavourites -> {
                getFavourites(base = event.symbol.code, rateOrder = state.value.rateOrder)
            }
            is Event.AddRate -> {
                viewModelScope.launch {
                    addFavouriteUseCase(event.base, event.rate)
                }.invokeOnCompletion {
                    getRates(event.base, state.value.rateOrder)
                }
            }
            is Event.DeleteRate -> {
                viewModelScope.launch {
                    deleteFavouriteUseCase(event.rate)
                }.invokeOnCompletion {
                    getRates(event.rate.base, state.value.rateOrder)
                }
            }
            is Event.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }
}
