package com.chernybro.exchangerates.feature_rates.domain.repository

import com.chernybro.exchangerates.feature_rates.domain.models.Symbol
import com.chernybro.exchangerates.feature_rates.utils.Resource
import kotlinx.coroutines.flow.Flow

interface SymbolRepository {

    fun getSymbols(): Flow<Resource<List<Symbol>>>
}