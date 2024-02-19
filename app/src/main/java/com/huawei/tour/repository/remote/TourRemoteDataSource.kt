package com.huawei.tour.repository.remote

import com.huawei.tour.data.TourListResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TourRemoteDataSource @Inject constructor(
    private val tourRemoteApi: TourRemoteApi
) {
    suspend fun getTourList(page: Int): TourListResponse {
        return tourRemoteApi.tourList(page)
    }
}