package com.chernybro.exchangerates.feature_rates.domain.use_case.get_symbols

import com.chernybro.exchangerates.feature_rates.domain.models.Symbol
import com.chernybro.exchangerates.feature_rates.domain.repository.SymbolRepository
import com.chernybro.exchangerates.feature_rates.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSymbols @Inject constructor(
    private val repository: SymbolRepository
) {

    operator fun invoke(): Flow<Resource<List<Symbol>>> {
        return repository.getSymbols()
    }
}

