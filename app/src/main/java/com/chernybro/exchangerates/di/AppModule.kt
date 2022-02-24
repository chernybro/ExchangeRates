package com.chernybro.exchangerates.di

import android.app.Application
import androidx.room.Room
import com.chernybro.exchangerates.feature_rates.data.database.ExchangeRatesDatabase
import com.chernybro.exchangerates.feature_rates.data.remote.ExchangeRatesApi
import com.chernybro.exchangerates.feature_rates.data.repository.FavouritesRepositoryImpl
import com.chernybro.exchangerates.feature_rates.data.repository.RateRepositoryImpl
import com.chernybro.exchangerates.feature_rates.data.repository.SymbolRepositoryImpl
import com.chernybro.exchangerates.feature_rates.domain.repository.FavouritesRepository
import com.chernybro.exchangerates.feature_rates.domain.repository.RateRepository
import com.chernybro.exchangerates.feature_rates.domain.repository.SymbolRepository
import com.chernybro.exchangerates.feature_rates.domain.use_case.favourites.AddFavourite
import com.chernybro.exchangerates.feature_rates.domain.use_case.favourites.DeleteFavourite
import com.chernybro.exchangerates.feature_rates.domain.use_case.favourites.GetFavourites
import com.chernybro.exchangerates.feature_rates.domain.use_case.rates.GetRates
import com.chernybro.exchangerates.feature_rates.domain.use_case.symbols.GetSymbols
import com.chernybro.exchangerates.feature_rates.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Main
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                val url = chain.request().url.newBuilder().addQueryParameter("source", Constants.RATES_SOURCE).build()
                request.url(url)
                chain.proceed(request.build())
            }
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideExchangeRatesApi(client: OkHttpClient): ExchangeRatesApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ExchangeRatesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideExchangeRatesDatabase(app: Application): ExchangeRatesDatabase {
        return Room.databaseBuilder(
            app,
            ExchangeRatesDatabase::class.java,
            "word_db"
        ).build()
    }

    /**
     * Repositories
     */

    @Provides
    @Singleton
    fun provideWordInfoRepository(
        db: ExchangeRatesDatabase,
        api: ExchangeRatesApi
    ): FavouritesRepository {
        return FavouritesRepositoryImpl(db.dao, api)
    }

    @Provides
    @Singleton
    fun provideRatesRepository(
        db: ExchangeRatesDatabase,
        api: ExchangeRatesApi
    ): RateRepository {
        return RateRepositoryImpl(api, db.dao)
    }

    @Provides
    @Singleton
    fun provideSymbolsRepository(
        api: ExchangeRatesApi
    ): SymbolRepository {
        return SymbolRepositoryImpl(api)
    }

    /**
     * Use cases
     */

    @Provides
    @Singleton
    fun provideGetRateUseCase(repository: RateRepository): GetRates {
        return GetRates(repository)
    }

    @Provides
    @Singleton
    fun provideGetFavouritesUseCase(repository: FavouritesRepository): GetFavourites {
        return GetFavourites(repository)
    }

    @Provides
    @Singleton
    fun provideAddRateUseCase(repository: FavouritesRepository): AddFavourite {
        return AddFavourite(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteRateUseCase(repository: FavouritesRepository): DeleteFavourite {
        return DeleteFavourite(repository)
    }

    @Provides
    @Singleton
    fun provideSymbolsInfoUseCase(repository: SymbolRepository): GetSymbols {
        return GetSymbols(repository)
    }
}