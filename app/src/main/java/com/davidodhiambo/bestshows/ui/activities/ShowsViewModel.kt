package com.davidodhiambo.bestshows.ui.activities

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidodhiambo.bestshows.data.model.Shows
import com.davidodhiambo.bestshows.repo.BestShowsRepository
import com.davidodhiambo.bestshows.util.Resource // Added import
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowsViewModel @Inject constructor(private val showsRepository: BestShowsRepository) : ViewModel(){

    private val _shows = MutableLiveData<Resource<Shows>>()
    val shows: LiveData<Resource<Shows>> = _shows

    fun getShows() = viewModelScope.launch {
        _shows.postValue(Resource.Loading())
        showsRepository.getShows().let { response ->
            if (response.isSuccessful) {
                response.body()?.let { responseBody ->
                    _shows.postValue(Resource.Success(responseBody))
                } ?: _shows.postValue(Resource.Error("Empty response body"))
            } else {
                _shows.postValue(Resource.Error("Error: ${response.code()} - ${response.message()}"))
            }
        }
    }
}