package com.huawei.tour.data

import com.google.gson.annotations.SerializedName

data class TourCategoryListResponse(
    @SerializedName("total")
    val total: Int,
    @SerializedName("data")
    val data: CategoryData
)

data class CategoryData(
    @SerializedName("Category")
    val categoryList: List<Category>
)

data class Category(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
