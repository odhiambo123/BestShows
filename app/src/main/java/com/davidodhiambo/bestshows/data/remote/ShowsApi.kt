package com.davidodhiambo.bestshows.data.remote

import com.davidodhiambo.bestshows.data.model.Show // Added import for single Show
import com.davidodhiambo.bestshows.data.model.Shows
import com.davidodhiambo.bestshows.util.Constants.Companion.SHOWS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path // Added import for @Path

interface ShowsApi {

    @GET(SHOWS)
    suspend fun getShows(): Response<Shows>

    @GET("shows/{id}") // New endpoint to get a single show by ID
    suspend fun getShowById(@Path("id") id: Int): Response<Show>
}