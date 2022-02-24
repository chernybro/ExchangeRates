package com.chernybro.exchangerates.feature_rates.data.repository

import com.chernybro.exchangerates.feature_rates.data.database.dao.FavouriteRateDao
import com.chernybro.exchangerates.feature_rates.data.remote.ExchangeRatesApi
import com.chernybro.exchangerates.feature_rates.data.remote.dto.toRates
import com.chernybro.exchangerates.feature_rates.domain.models.Rate
import com.chernybro.exchangerates.feature_rates.domain.models.toFavouriteRate
import com.chernybro.exchangerates.feature_rates.domain.repository.FavouritesRepository
import com.chernybro.exchangerates.feature_rates.utils.Resource
import com.chernybro.exchangerates.feature_rates.utils.SimpleResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class FavouritesRepositoryImpl @Inject constructor(
    private val dao: FavouriteRateDao,
    private val api: ExchangeRatesApi,
) : FavouritesRepository {

    override fun getRates(base: String): Flow<Resource<List<Rate>>> {
        return flow {
            val favourites = dao.getRates().map { it.toRate() }
            try {

                val remoteRates = api.getLatestRates(base).toRates()
                val foundRemoteFavourites = remoteRates.filter { remoteRate ->
                    favourites.contains(remoteRate)
                }
                foundRemoteFavourites.forEach { rate ->
                    dao.insertRate(rate.toFavouriteRate())
                }
            } catch (e: HttpException) {
                emit(
                    Resource.Error<List<Rate>>(
                        message = "Oops, something went wrong!",
                        data = favourites
                    )
                )
            } catch (e: IOException) {
                emit(
                    Resource.Error<List<Rate>>(
                        message = "Couldn't reach server, check your internet connection.",
                        data = favourites
                    )
                )
            }
            val updatedFavourites = dao.getRates().map { it.toRate() }
            emit(Resource.Success<List<Rate>>(updatedFavourites))
        }
    }

    override suspend fun insertRate(base: String, rate: Rate): SimpleResource {
           try {
               val favourites = dao.getRates().map { it.toRate() }.toMutableList()
               favourites.add(rate)
               val remoteRates =
                   api.getLatestRates(
                       base,
                       favourites.joinToString(separator = ",") { favouriteRate -> favouriteRate.code })
                       .toRates()
               remoteRates.forEach { remoteRate ->
                   dao.insertRate(remoteRate.toFavouriteRate())
               }
               val result = dao.getRates().map { it.toRate() }
               Timber.d("REPO $result")
               return Resource.Success(Unit)
           } catch (e: HttpException) {
               return Resource.Error(
                   message = "Oops, something went wrong!"
               )
           } catch (e: IOException) {
               return Resource.Error(
                   message = "Couldn't reach server, check your internet connection."
               )
           }
       }

    override suspend fun deleteRate(rate: Rate): SimpleResource {
        dao.deleteRate(rate.code)
        return Resource.Success(Unit)
    }

}