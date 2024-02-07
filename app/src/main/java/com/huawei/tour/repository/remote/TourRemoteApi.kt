package com.huawei.tour.repository.remote

import com.huawei.tour.data.TourListResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface TourRemoteApi {
    @Headers("accept: application/json")
    @GET("zh-tw/Attractions/All")
    suspend fun tourList(@Query("page") page: Int): TourListResponse
}