package com.chernybro.exchangerates.feature_rates.data.repository

import com.chernybro.exchangerates.feature_rates.data.remote.ExchangeRatesApi
import com.chernybro.exchangerates.feature_rates.data.remote.dto.toRates
import com.chernybro.exchangerates.feature_rates.domain.models.Rate
import com.chernybro.exchangerates.feature_rates.domain.repository.RateRepository
import com.chernybro.exchangerates.feature_rates.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RateRepositoryImpl @Inject constructor(
    private val api: ExchangeRatesApi
) : RateRepository {

    override fun getRates(base: String): Flow<Resource<List<Rate>>> = flow {
        try {
            val rates = api.getLatestRates(base).toRates()
            emit(Resource.Loading(data = rates))
            emit(Resource.Success<List<Rate>>(rates))
        } catch(e: HttpException) {
            emit(Resource.Error<List<Rate>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<List<Rate>>("Couldn't reach server. Check your internet connection."))
        }
    }
}