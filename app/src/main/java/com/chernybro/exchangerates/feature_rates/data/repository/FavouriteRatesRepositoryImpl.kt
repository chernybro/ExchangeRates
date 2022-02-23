package com.chernybro.exchangerates.feature_rates.data.repository

import com.chernybro.exchangerates.feature_rates.data.database.dao.FavouriteRateDao
import com.chernybro.exchangerates.feature_rates.data.database.entities.FavouriteRateEntity
import com.chernybro.exchangerates.feature_rates.domain.models.Rate
import com.chernybro.exchangerates.feature_rates.domain.models.toFavouriteRate
import com.chernybro.exchangerates.feature_rates.domain.repository.FavouriteRatesRepository
import com.chernybro.exchangerates.feature_rates.utils.Resource
import com.chernybro.exchangerates.feature_rates.utils.SimpleResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavouriteRatesRepositoryImpl  @Inject constructor(
    private val dao: FavouriteRateDao
) : FavouriteRatesRepository {

    override fun getRates(): Flow<Resource<List<Rate>>> {
        return flow {
            emit(Resource.Loading())

            val favourites = dao.getRates().map { it.toRate() }
            emit(Resource.Success(favourites))

        }
    }

    override fun insertRate(rate: Rate): Flow<SimpleResource> {
        return flow {
            dao.insertRate(rate.toFavouriteRate())
        }
    }

    override fun deleteRate(rate: Rate): Flow<SimpleResource> {
        return flow {
            dao.deleteRate(rate.code)
        }
    }

}