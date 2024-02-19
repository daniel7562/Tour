package com.huawei.tour.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.huawei.tour.data.TourCategoryListResponse
import com.huawei.tour.data.TourItem
import com.huawei.tour.repository.paging.TourPagingSource
import com.huawei.tour.repository.remote.TourRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TourListRepository @Inject constructor(
    private val tourRemoteDataSource: TourRemoteDataSource,
) {
    fun getTourList(categoryIds: String?): Flow<PagingData<TourItem>> {
        return Pager(
            config = PagingConfig(Integer.MAX_VALUE, prefetchDistance = 1),
            initialKey = 1,
            pagingSourceFactory = {
                TourPagingSource(tourRemoteDataSource, categoryIds)
            },
        ).flow
    }

    suspend fun getCategories(): TourCategoryListResponse {
        return tourRemoteDataSource.getTourCategories()
    }
}
