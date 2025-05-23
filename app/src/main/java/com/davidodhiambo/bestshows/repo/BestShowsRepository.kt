package com.davidodhiambo.bestshows.repo

import com.davidodhiambo.bestshows.data.remote.ShowsApi
import javax.inject.Inject

class BestShowsRepository @Inject constructor(private val bestShowsApi: ShowsApi) {

    suspend fun getShows() = bestShowsApi.getShows()

    suspend fun getShowById(id: Int) = bestShowsApi.getShowById(id)

}