package com.davidodhiambo.bestshows.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidodhiambo.bestshows.data.model.Show
import com.davidodhiambo.bestshows.repo.BestShowsRepository
import com.davidodhiambo.bestshows.util.Resource // Added import
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel@Inject constructor(private val bestShowsRepository: BestShowsRepository): ViewModel() {

    private val _show = MutableLiveData<Resource<Show>>()
    val show: LiveData<Resource<Show>> = _show

    fun getShowById(id: Int) = viewModelScope.launch {
        _show.postValue(Resource.Loading())
        bestShowsRepository.getShowById(id).let { response ->
            if (response.isSuccessful) {
                response.body()?.let { responseBody ->
                    _show.postValue(Resource.Success(responseBody))
                } ?: _show.postValue(Resource.Error("Show not found or empty response"))
            } else {
                _show.postValue(Resource.Error("Error: ${response.code()} - ${response.message()}"))
            }
        }
    }
}