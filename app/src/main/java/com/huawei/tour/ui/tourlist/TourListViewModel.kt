package com.huawei.tour.ui.tourlist

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.huawei.tour.base.BaseViewModel
import com.huawei.tour.data.TourItem
import com.huawei.tour.di.IODispatcher
import com.huawei.tour.repository.TourListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class TourListViewModel @Inject constructor(
    @IODispatcher ioDispatcher: CoroutineDispatcher,
    private val tourListRepository: TourListRepository,
) : BaseViewModel(ioDispatcher) {

    private val _tourListStateFlow: MutableStateFlow<PagingData<TourItem>> =
        MutableStateFlow(PagingData.empty())
    val tourListStateFlow: StateFlow<PagingData<TourItem>>
        get() = _tourListStateFlow

    fun getTourList() {
        viewModelScope.launch(ioDispatcher) {
            tourListRepository.getTourList()
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _tourListStateFlow.value = it.filter { item -> item.images.isNotEmpty() }
                }
        }
    }
}
