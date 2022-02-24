package com.chernybro.exchangerates.feature_rates.data.repository

import com.chernybro.exchangerates.R
import com.chernybro.exchangerates.feature_rates.data.database.dao.FavouriteRateDao
import com.chernybro.exchangerates.feature_rates.data.remote.ExchangeRatesApi
import com.chernybro.exchangerates.feature_rates.data.remote.dto.toRates
import com.chernybro.exchangerates.feature_rates.domain.models.Rate
import com.chernybro.exchangerates.feature_rates.domain.models.Symbol
import com.chernybro.exchangerates.feature_rates.domain.repository.RateRepository
import com.chernybro.exchangerates.feature_rates.utils.Resource
import com.chernybro.exchangerates.feature_rates.utils.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class RateRepositoryImpl @Inject constructor(
    private val api: ExchangeRatesApi,
    private val dao: FavouriteRateDao
) : RateRepository {

    override fun getRates(base: String): Flow<Resource<List<Rate>>> = flow {
        emit(Resource.Loading<List<Rate>>())
        val favourites = dao.getRates().map { it.toRate() }
        Timber.d("db ${favourites}")
        try {
            val remoteRates = api.getLatestRates(base).toRates()
            val newRemote = remoteRates.map { remoteRate ->
                if (favourites.contains(remoteRate)){
                    remoteRate.copy(isFavourite = true)
                } else {
                    remoteRate
                }
            }
            Timber.d("remote ${newRemote}")
            emit(Resource.Success<List<Rate>>(newRemote))
        } catch(e: HttpException) {
            emit(Resource.Error<List<Rate>>(UiText.unknownError()))
        } catch(e: IOException) {
            emit(Resource.Error<List<Rate>>(UiText.StringResource(R.string.error_connection)))
        }
    }
}