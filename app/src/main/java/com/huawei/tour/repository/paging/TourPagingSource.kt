package com.huawei.tour.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.huawei.tour.data.TourItem
import com.huawei.tour.repository.remote.TourRemoteDataSource
import retrofit2.HttpException
import java.io.IOException

class TourPagingSource(
    private val remoteDataSource: TourRemoteDataSource,
) : PagingSource<Int, TourItem>() {
    override fun getRefreshKey(state: PagingState<Int, TourItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TourItem> {
        return try {
            val currentPage = params.key ?: 1
            val tourList = remoteDataSource.getTourList(currentPage).data
            LoadResult.Page(
                data = tourList,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (tourList.isEmpty()) null else currentPage + 1,
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}
