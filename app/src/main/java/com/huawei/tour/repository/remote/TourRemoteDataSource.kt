package com.huawei.tour.repository.remote

import com.huawei.tour.data.TourCategoryListResponse
import com.huawei.tour.data.TourListResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TourRemoteDataSource @Inject constructor(
    private val tourRemoteApi: TourRemoteApi
) {
    suspend fun getTourList(page: Int, categoryIds: String?): TourListResponse {
        return tourRemoteApi.tourList(page, categoryIds)
    }

    suspend fun getTourCategories(): TourCategoryListResponse {
        return tourRemoteApi.categories()
    }
}