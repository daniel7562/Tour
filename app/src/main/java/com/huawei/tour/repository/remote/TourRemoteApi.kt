package com.huawei.tour.repository.remote

import com.huawei.tour.data.TourCategoryListResponse
import com.huawei.tour.data.TourListResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface TourRemoteApi {
    @Headers("accept: application/json")
    @GET("zh-tw/Attractions/All")
    suspend fun tourList(@Query("page") page: Int, @Query("categoryIds") categoryIds: String?): TourListResponse

    @Headers("accept: application/json")
    @GET("zh-tw/Miscellaneous/Categories")
    suspend fun categories(@Query("type") type: String = "Attractions"): TourCategoryListResponse
}