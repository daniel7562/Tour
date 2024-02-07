package com.huawei.tour.tourlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huawei.tour.repository.remote.TourListRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class TourListViewModel @Inject constructor(
    private val tourListRepository: TourListRemoteRepository
) : ViewModel() {

    fun getTourList(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = tourListRepository.getTourList(page)
            Timber.d("===daniel test response total: ${response.total}")
        }
    }
}