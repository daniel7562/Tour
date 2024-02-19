package com.huawei.tour

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.huawei.tour.ui.theme.TourTheme
import com.huawei.tour.ui.tourlist.LoadingNextPageItem
import com.huawei.tour.ui.tourlist.TourItemView
import com.huawei.tour.ui.tourlist.TourListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val tourListViewModel: TourListViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TourTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val refreshState = rememberPullToRefreshState()
                    HandlePullDownRefresh(refreshState)
                    TourListArea(refreshState)
                }
            }
        }

        tourListViewModel.getTourList()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TourListArea(refreshState: PullToRefreshState) {
        val tourList = tourListViewModel.tourListStateFlow.collectAsLazyPagingItems()

        Box(Modifier.nestedScroll(refreshState.nestedScrollConnection)) {
            LazyColumn(state = rememberLazyListState()) {
                refreshState.endRefresh()
                items(tourList.itemCount) { index ->
                    tourList[index]?.let {
                        TourItemView(it)
                    }
                }

                tourList.apply {
                    when {
                        loadState.append is LoadState.Loading -> {
                            item { LoadingNextPageItem(modifier = Modifier) }
                        }
                    }
                }
            }

            PullToRefreshContainer(
                modifier = Modifier.align(Alignment.TopCenter),
                state = refreshState,
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun HandlePullDownRefresh(refreshState: PullToRefreshState) {
        if (refreshState.isRefreshing) {
            LaunchedEffect(true) {
                tourListViewModel.getTourList()
            }
        }
    }
}
