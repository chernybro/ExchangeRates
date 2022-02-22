package com.chernybro.exchangerates.di

import com.chernybro.exchangerates.feature_rates.data.remote.ExchangeRatesApi
import com.chernybro.exchangerates.feature_rates.data.repository.RateRepositoryImpl
import com.chernybro.exchangerates.feature_rates.domain.repository.RateRepository
import com.chernybro.exchangerates.feature_rates.domain.use_case.sort_rates.GetRates
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

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                val url = chain.request().url.newBuilder().addQueryParameter("source", "ecb").build()
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
    fun provideGetWordInfoUseCase(repository: RateRepository): GetRates {
        return GetRates(repository)
    }

    @Provides
    @Singleton
    fun provideWordInfoRepository(
        api: ExchangeRatesApi
    ): RateRepository {
        return RateRepositoryImpl(api)
    }

}