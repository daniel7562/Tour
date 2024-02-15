package com.huawei.tour.ui.tourlist

import com.huawei.tour.base.BaseViewModel
import com.huawei.tour.data.TourItem
import com.huawei.tour.di.IODispatcher
import com.huawei.tour.repository.remote.TourListRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TourListViewModel @Inject constructor(
    @IODispatcher ioDispatcher: CoroutineDispatcher,
    private val tourListRepository: TourListRemoteRepository,
) : BaseViewModel(ioDispatcher) {

    private val _tourListStateFlow: MutableStateFlow<List<TourItem>> = MutableStateFlow(emptyList())
    val tourListStateFlow: StateFlow<List<TourItem>>
        get() = _tourListStateFlow

    fun getTourList(page: Int) {
        requestBackgroundTask(
            page,
            KEY_API_TOUR_LIST,
        ) { page ->
            val response = tourListRepository.getTourList(page)
            _tourListStateFlow.emit(response.data)
        }
    }

    companion object {
        private const val KEY_API_TOUR_LIST = "TOUR_LIST"
    }
}
