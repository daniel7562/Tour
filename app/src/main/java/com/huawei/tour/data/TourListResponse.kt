package com.huawei.tour.data

import com.google.gson.annotations.SerializedName

data class TourListResponse(
    @SerializedName("total")
    val total: Int,
    @SerializedName("data")
    val data: List<TourItem>
)

data class TourItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("introduction")
    val introduction: String,
    @SerializedName("open_time")
    val openTime: String,
    @SerializedName("distric")
    val distric: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("images")
    val images: List<Image>
)

data class Image(
    @SerializedName("src")
    val src: String,
    @SerializedName("ext")
    val ext: String
)
