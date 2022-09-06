package com.davidodhiambo.bestshows.ui.activities

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidodhiambo.bestshows.data.model.Shows
import com.davidodhiambo.bestshows.repo.BestShowsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowsViewModel @Inject constructor(private val showsRepository: BestShowsRepository) : ViewModel(){

    private val shows = MutableLiveData<Shows>()

    fun getShows() = viewModelScope.launch {
        showsRepository.getShows().let {
            if (it.isSuccessful) {
                shows.value = it.body()
            } else {
                Log.e("Error: ", it.code().toString())
            }

        }

    }

    fun observeShows(): LiveData<Shows> {
        return shows
    }
}