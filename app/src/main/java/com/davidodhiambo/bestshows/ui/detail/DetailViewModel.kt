package com.davidodhiambo.bestshows.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidodhiambo.bestshows.data.model.Show
import com.davidodhiambo.bestshows.repo.BestShowsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel@Inject constructor(private val bestShowsRepository: BestShowsRepository): ViewModel() {

    private val show = MutableLiveData<Show>()

    fun getShowById(id: Int) = viewModelScope.launch {

        bestShowsRepository.getShows().let {

            if (it.isSuccessful) {

                for (showResponse in it.body()!!) {

                    if (showResponse.id == id) {

                        show.value = showResponse

                    }

                }

            } else {

                Log.e("Error: ", it.code().toString())

            }

        }

    }

    fun getShow(): LiveData<Show> = show


    fun observeShow(): LiveData<Show> {
        return show
    }


}