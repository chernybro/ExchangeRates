package com.chernybro.exchangerates.feature_rates.presentation.favourites

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chernybro.exchangerates.feature_rates.domain.models.Symbol
import com.chernybro.exchangerates.feature_rates.domain.use_case.favourites.DeleteFavourite
import com.chernybro.exchangerates.feature_rates.domain.use_case.favourites.GetFavourites
import com.chernybro.exchangerates.feature_rates.domain.use_case.symbols.GetSymbols
import com.chernybro.exchangerates.feature_rates.domain.utils.OrderType
import com.chernybro.exchangerates.feature_rates.domain.utils.RateOrder
import com.chernybro.exchangerates.feature_rates.presentation.common.BaseViewModel
import com.chernybro.exchangerates.feature_rates.presentation.common.RatesEvent
import com.chernybro.exchangerates.feature_rates.utils.Constants
import com.chernybro.exchangerates.feature_rates.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel  @Inject constructor(
    private val getFavouritesUseCase: GetFavourites,
    private val deleteFavouriteUseCase: DeleteFavourite,
    private val getSymbolsUseCase: GetSymbols
) : BaseViewModel() {

    private val _state = mutableStateOf(FavouritesState())
    val state: State<FavouritesState> = _state

    init {
        getFavourites(Constants.BASE_RATE ,RateOrder.Code(OrderType.Ascending))
        getSymbols()
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

    override fun onEvent(event: RatesEvent) {
        when (event) {
            is RatesEvent.Order -> {
                if (state.value.rateOrder::class == event.rateOrder::class &&
                    state.value.rateOrder.orderType == event.rateOrder.orderType
                ) {
                    return
                }
                getFavourites(base = state.value.selectedSymbol.code, rateOrder = event.rateOrder)
            }
            is RatesEvent.Select -> { //TODO check internet?
                getFavourites(base = event.symbol.code, rateOrder = state.value.rateOrder)
            }
            is RatesEvent.DeleteRate -> {
                deleteFavouriteUseCase(event.rate).onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                favourites = result.data ?: emptyList(),
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
            is RatesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

}
