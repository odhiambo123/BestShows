package com.davidodhiambo.bestshows.di

import com.davidodhiambo.bestshows.data.remote.ShowsApi
import com.davidodhiambo.bestshows.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BestShowsModule {
    @Provides
    fun providesBaseUre() = BASE_URL

    @Provides
    @Singleton
    fun provideRetrofitInstance(baseUrl : String) : ShowsApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()
        .create(ShowsApi::class.java)
}