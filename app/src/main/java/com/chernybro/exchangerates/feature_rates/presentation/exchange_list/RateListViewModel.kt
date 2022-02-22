package com.chernybro.exchangerates.feature_rates.presentation.exchange_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chernybro.exchangerates.feature_rates.domain.use_case.sort_rates.GetRates
import com.chernybro.exchangerates.feature_rates.domain.utils.OrderType
import com.chernybro.exchangerates.feature_rates.domain.utils.RateOrder
import com.chernybro.exchangerates.feature_rates.presentation.components.ListState
import com.chernybro.exchangerates.feature_rates.presentation.components.RatesEvent
import com.chernybro.exchangerates.feature_rates.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RateListViewModel @Inject constructor(
    private val getRatesUseCase: GetRates,
) : ViewModel() {

    private val _state = mutableStateOf(ListState())
    val state: State<ListState> = _state

    init {
        getRates("EUR", RateOrder.Code(OrderType.Descending))
    }

    private fun getRates(base: String = "EUR", rateOrder: RateOrder) {
        getRatesUseCase("EUR", rateOrder).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ListState(rates = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = ListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = ListState(isLoading = true)
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
                getRates("EUR", rateOrder = event.rateOrder)
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