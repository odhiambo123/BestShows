package com.davidodhiambo.bestshows.data.remote

import com.davidodhiambo.bestshows.data.model.Shows
import com.davidodhiambo.bestshows.util.Constants.Companion.SHOWS
import retrofit2.Response
import retrofit2.http.GET

interface ShowsApi {

    @GET(SHOWS)
    suspend fun getShows(): Response<Shows>
}