package com.huawei.tour.repository

import androidx.paging.testing.asSnapshot
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.huawei.tour.data.Category
import com.huawei.tour.data.CategoryData
import com.huawei.tour.data.Image
import com.huawei.tour.data.TourCategoryListResponse
import com.huawei.tour.data.TourItem
import com.huawei.tour.data.TourListResponse
import com.huawei.tour.repository.remote.TourRemoteDataSource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TourListRepositoryTest {

    @MockK
    lateinit var tourRemoteDataSource: TourRemoteDataSource

    private lateinit var tourListRepository: TourListRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        coEvery {
            tourRemoteDataSource.getTourList(any(), any())
        } returns TourListResponse(
            total = mockTourItems.size,
            data = mockTourItems,
        )

        coEvery {
            tourRemoteDataSource.getTourCategories()
        } returns mockTourCategoryListResponse

        tourListRepository = TourListRepository(tourRemoteDataSource)
    }

    @Test
    fun getTourList_hasTourItems_responseDataIsCorrect() = runBlocking {
        val tourListFlow = tourListRepository.getTourList("12")

        assertEquals(mockTourItems, tourListFlow.asSnapshot())
    }

    @Test
    fun getCategories_hasCategoryItems_responseDataIsCorrect() = runBlocking {
        val categories = tourListRepository.getCategories()

        assertEquals(mockTourCategoryListResponse, categories)
    }

    companion object {
        private val mockTourItems = listOf(
            TourItem(
                id = 1794,
                name = "MAJI集食行樂_圓山花博爭豔館",
                introduction = "「MAJI2集食行樂」鄰近台北市立美術館，隱身在台北花博公園圓山園區一角",
                openTime = "星期一～星期五",
                distric = "中山區",
                address = "臺北市中山區玉門街1號",
                url = "https://www.travel.taipei/zh-tw/attraction/details/1794",
                images = listOf(
                    Image(
                        src = "https://www.travel.taipei/image/184759",
                        ext = ".jpg",
                    ),
                ),
            ),
            TourItem(
                id = 23,
                name = "國立中正紀念堂",
                introduction = "中正紀念堂是為了紀念中華民國第一任總統蔣介石",
                openTime = "紀念堂開放時間：am09:00-pm06:00（星期一不休館)",
                distric = "中正區",
                address = "臺北市中正區中山南路21號",
                url = "https://www.travel.taipei/zh-tw/attraction/details/23",
                images = listOf(
                    Image(
                        src = "https://www.travel.taipei/image/222951",
                        ext = ".jpg",
                    ),
                ),
            ),
        )
        private val mockTourCategoryListResponse = TourCategoryListResponse(
            total = 2,
            data = CategoryData(
                categoryList = listOf(
                    Category(
                        id = 11,
                        name = "養生溫泉",
                    ),
                    Category(
                        id = 12,
                        name = "單車遊蹤",
                    ),
                ),
            ),
        )
    }
}
