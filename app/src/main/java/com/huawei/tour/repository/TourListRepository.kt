package com.huawei.tour.repository

import com.huawei.tour.data.TourListResponse
import com.huawei.tour.repository.remote.TourRemoteApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TourListRepository @Inject constructor(
    private val tourRemoteApi: TourRemoteApi
) {
    suspend fun getTourList(page: Int): TourListResponse {
        return tourRemoteApi.tourList(page)
    }
}