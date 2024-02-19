package com.huawei.tour.ui.tourlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.testing.asPagingSourceFactory
import androidx.paging.testing.asSnapshot
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.huawei.tour.TestDispatcherRule
import com.huawei.tour.data.Image
import com.huawei.tour.data.TourItem
import com.huawei.tour.repository.TourListRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TourListViewModelTest {

    @get:Rule
    val testDispatcherRule = TestDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var tourListRepository: TourListRepository

    private lateinit var viewModel: TourListViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = TourListViewModel(
            ioDispatcher = testDispatcherRule.testDispatcher,
            tourListRepository = tourListRepository,
        )
    }

    @Test
    fun getTourList_hasTourItems_tourListStateFlowIsCorrect() = runBlocking {
        val mockItems = listOf(
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
        val testData = flow {
            emit(mockItems)
        }
        every {
            tourListRepository.getTourList(any())
        } returns Pager(
            config = PagingConfig(Integer.MAX_VALUE, prefetchDistance = 1),
            initialKey = null,
            pagingSourceFactory = testData.asPagingSourceFactory(this),
        ).flow

        viewModel.getTourList()

        assertEquals(mockItems, viewModel.tourListStateFlow.asSnapshot())
    }

    @Test
    fun getTourList_hasNoTourItems_tourListStateFlowIsCorrect() = runBlocking {
        val mockItems = emptyList<TourItem>()
        val testData = flow {
            emit(mockItems)
        }
        every {
            tourListRepository.getTourList(any())
        } returns Pager(
            config = PagingConfig(Integer.MAX_VALUE, prefetchDistance = 1),
            initialKey = null,
            pagingSourceFactory = testData.asPagingSourceFactory(this),
        ).flow

        viewModel.getTourList()

        assertEquals(mockItems, viewModel.tourListStateFlow.asSnapshot())
    }
}
