package com.davidodhiambo.bestshows.di

import com.davidodhiambo.bestshows.data.remote.ShowsApi
import com.davidodhiambo.bestshows.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient // Added
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit // Added
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BestShowsModule {
    @Provides
    fun providesBaseUrl() = BASE_URL // Corrected typo

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient { // Added method
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(baseUrl : String, okHttpClient: OkHttpClient) : ShowsApi = Retrofit.Builder() // Added okHttpClient parameter
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient) // Added client
        .baseUrl(baseUrl)
        .build()
        .create(ShowsApi::class.java)
}