package com.huawei.tour.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.huawei.tour.data.TourItem
import com.huawei.tour.repository.remote.TourRemoteDataSource

class TourPagingSource(
    private val remoteDataSource: TourRemoteDataSource,
    private val categoryIds: String?
) : PagingSource<Int, TourItem>() {
    override fun getRefreshKey(state: PagingState<Int, TourItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TourItem> {
        return try {
            val currentPage = params.key ?: 1
            val tourList = remoteDataSource.getTourList(currentPage, categoryIds).data
            LoadResult.Page(
                data = tourList,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (tourList.isEmpty()) null else currentPage + 1,
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}
