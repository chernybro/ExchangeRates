package com.chernybro.exchangerates.feature_rates.data.repository

import com.chernybro.exchangerates.R
import com.chernybro.exchangerates.feature_rates.data.remote.ExchangeRatesApi
import com.chernybro.exchangerates.feature_rates.data.remote.dto.toSymbols
import com.chernybro.exchangerates.feature_rates.domain.models.Symbol
import com.chernybro.exchangerates.feature_rates.domain.repository.SymbolRepository
import com.chernybro.exchangerates.feature_rates.utils.Resource
import com.chernybro.exchangerates.feature_rates.utils.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SymbolRepositoryImpl @Inject constructor(
    private val api: ExchangeRatesApi
) : SymbolRepository {

    override fun getSymbols(): Flow<Resource<List<Symbol>>> = flow {
        try {
            val symbols = api.getSymbols().toSymbols()
            emit(Resource.Loading(data = symbols))
            emit(Resource.Success<List<Symbol>>(symbols))
        } catch(e: HttpException) {
            emit(Resource.Error<List<Symbol>>(UiText.unknownError()))
        } catch(e: IOException) {
            emit(Resource.Error<List<Symbol>>(UiText.StringResource(R.string.error_connection)))
        }
    }
}