package com.huawei.tour

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.huawei.tour.ui.theme.TourTheme
import com.huawei.tour.ui.tourlist.BottomLoadingIndicator
import com.huawei.tour.ui.tourlist.Dimens.CATEGORY_ITEM_GAP
import com.huawei.tour.ui.tourlist.Dimens.CATEGORY_LIST_HORIZONTAL_GAP
import com.huawei.tour.ui.tourlist.Dimens.CATEGORY_LIST_VERTICAL_GAP
import com.huawei.tour.ui.tourlist.TourCategoryListView
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
                val refreshState = rememberPullToRefreshState()
                HandlePullDownRefresh(refreshState)
                Column {
                    TourCategoryListArea()
                    TourListArea(refreshState)
                }
            }
        }

        tourListViewModel.getTourCategoryList()
    }

    @Composable
    fun TourCategoryListArea() {
        val tourCategoryList = tourListViewModel.tourCategoryListFlow.collectAsState()
        LazyRow(
            state = rememberLazyListState(),
            modifier = Modifier
                .background(color = colorResource(R.color.category_list_background))
                .padding(
                    vertical = CATEGORY_LIST_VERTICAL_GAP,
                ),
            horizontalArrangement = Arrangement.spacedBy(CATEGORY_ITEM_GAP),
        ) {
            item {
                Spacer(Modifier.width(CATEGORY_LIST_HORIZONTAL_GAP))
            }
            items(tourCategoryList.value) {
                TourCategoryListView(it, tourListViewModel.selectedCategoryFlow) { category ->
                    tourListViewModel.filterTourListByCategory(category)
                }
            }
            item {
                Spacer(Modifier.width(CATEGORY_LIST_HORIZONTAL_GAP))
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TourListArea(refreshState: PullToRefreshState) {
        val tourList = tourListViewModel.tourListStateFlow.collectAsLazyPagingItems()
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
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
                                item { BottomLoadingIndicator(modifier = Modifier) }
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
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun HandlePullDownRefresh(refreshState: PullToRefreshState) {
        if (refreshState.isRefreshing) {
            LaunchedEffect(true) {
                tourListViewModel.refresh()
            }
        }
    }
}
