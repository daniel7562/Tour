package com.huawei.tour.ui.tourlist

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.huawei.tour.base.BaseViewModel
import com.huawei.tour.data.Category
import com.huawei.tour.data.TourItem
import com.huawei.tour.di.IODispatcher
import com.huawei.tour.repository.TourListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TourListViewModel @Inject constructor(
    @IODispatcher ioDispatcher: CoroutineDispatcher,
    private val tourListRepository: TourListRepository,
) : BaseViewModel(ioDispatcher) {

    private val _tourListStateFlow: MutableStateFlow<PagingData<TourItem>> =
        MutableStateFlow(PagingData.empty())
    val tourListStateFlow: StateFlow<PagingData<TourItem>>
        get() = _tourListStateFlow

    private val _tourCategoryListFlow: MutableStateFlow<List<Category>> =
        MutableStateFlow(emptyList())
    val tourCategoryListFlow: StateFlow<List<Category>>
        get() = _tourCategoryListFlow

    private val _selectedCategoryFlow: MutableStateFlow<Category> =
        MutableStateFlow(Category(id = -1, name = ""))
    val selectedCategoryFlow: StateFlow<Category>
        get() = _selectedCategoryFlow

    fun getTourList(categoryIds: String? = null) {
        viewModelScope.launch(ioDispatcher) {
            tourListRepository.getTourList(categoryIds)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _tourListStateFlow.value = it.filter { item -> item.images.isNotEmpty() }
                }
        }
    }

    fun getTourCategoryList() {
        requestBackgroundTask(
            Unit,
            KEY_API_TOUR_CATEGORY_LIST,
        ) {
            val categoryListResponse = tourListRepository.getCategories()
            _tourCategoryListFlow.value = categoryListResponse.data.categoryList
            val selectedCategory = categoryListResponse.data.categoryList.first()
            _selectedCategoryFlow.value = selectedCategory
            getTourList(selectedCategory.id.toString())
        }
    }

    fun filterTourListByCategory(category: Category) {
        _selectedCategoryFlow.value = category
        getTourList(categoryIds = category.id.toString())
    }

    fun refresh() {
        val selectedCategory = _selectedCategoryFlow.value
        getTourList(categoryIds = selectedCategory.id.toString())
    }

    companion object {
        private const val KEY_API_TOUR_CATEGORY_LIST = "API_TOUR_CATEGORY_LIST"
    }
}
