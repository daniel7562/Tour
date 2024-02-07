package com.huawei.tour.repository

import com.huawei.tour.data.TourListResponse

interface TourListRepository {
    suspend fun getTourList(page: Int): TourListResponse
}