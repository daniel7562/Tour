package com.huawei.tour.repository.remote

import com.huawei.tour.data.TourListResponse
import com.huawei.tour.repository.TourListRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TourListRemoteRepository @Inject constructor(
    private val tourRemoteApi: TourRemoteApi
) : TourListRepository {

    override suspend fun getTourList(page: Int): TourListResponse {
        return tourRemoteApi.tourList(page)
    }
}