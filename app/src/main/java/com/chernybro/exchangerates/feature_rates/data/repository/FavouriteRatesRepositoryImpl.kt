package com.chernybro.exchangerates.feature_rates.data.repository

import com.chernybro.exchangerates.feature_rates.data.database.dao.FavouriteRateDao
import com.chernybro.exchangerates.feature_rates.data.remote.ExchangeRatesApi
import com.chernybro.exchangerates.feature_rates.data.remote.dto.toRates
import com.chernybro.exchangerates.feature_rates.domain.models.Rate
import com.chernybro.exchangerates.feature_rates.domain.models.toFavouriteRate
import com.chernybro.exchangerates.feature_rates.domain.repository.FavouriteRatesRepository
import com.chernybro.exchangerates.feature_rates.utils.Resource
import com.chernybro.exchangerates.feature_rates.utils.SimpleResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FavouriteRatesRepositoryImpl @Inject constructor(
    private val dao: FavouriteRateDao,
    private val api: ExchangeRatesApi
) : FavouriteRatesRepository {

    override fun getRates(base: String): Flow<Resource<List<Rate>>> {
        return flow {
            emit(Resource.Loading())

            val favourites = dao.getRates().map { it.toRate() }
            emit(Resource.Success(favourites))

            try {
                val remoteRates = api.getLatestRates(base).toRates()
                favourites.map { dbRate ->
                    remoteRates.forEach { remoteRate ->
                        if (dbRate == remoteRate) {
                            Rate(
                                code = remoteRate.code,
                                base = remoteRate.base,
                                cost = remoteRate.cost,
                                isFavourite = dbRate.isFavourite
                            )
                        }
                    }
                }
                favourites.forEach { rate ->
                    dao.insertRate(rate.toFavouriteRate())
                }
            } catch (e: HttpException) {
                emit(
                    Resource.Error(
                        message = "Oops, something went wrong!", // TODO :
                        data = favourites
                    )
                )
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        message = "Couldn't reach server, check your internet connection.",
                        data = favourites
                    )
                )
            }

            val updatedFavourites = dao.getRates().map { it.toRate() }
            emit(Resource.Success(updatedFavourites))

        }
    }

    override fun insertRate(base: String, rate: Rate): Flow<Resource<List<Rate>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val rateToInsert =
                    api.getLatestRates(base, listOf(rate.code)).toRates()
                rateToInsert.forEach { rate ->
                    dao.insertRate(rate.toFavouriteRate())
                }
                val remoteRates = api.getLatestRates(base).toRates()
                val dbRates = dao.getRates().map { it.toRate() }
                remoteRates.map { remoteRate ->
                    dbRates.forEach { dbRate ->
                        if (dbRate == remoteRate) {
                            Rate(
                                code = remoteRate.code,
                                base = remoteRate.base,
                                cost = remoteRate.cost,
                                isFavourite = dbRate.isFavourite
                            )
                        }
                    }
                }

                emit(Resource.Success(remoteRates))
            } catch (e: HttpException) {
                emit(
                    Resource.Error(
                        message = "Oops, something went wrong!"
                    )
                )
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        message = "Couldn't reach server, check your internet connection."
                    )
                )
            }

        }
    }

    override fun deleteRate(rate: Rate): Flow<Resource<List<Rate>>> {
        return flow {
            emit(Resource.Loading())
            dao.deleteRate(rate.code)
            val resultRates = dao.getRates().map { it.toRate() }
            emit(Resource.Success(data = resultRates))
        }
    }

}