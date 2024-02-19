package com.huawei.tour.ui.tourlist

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.huawei.tour.R
import com.huawei.tour.data.Category
import com.huawei.tour.ui.tourlist.Dimens.CATEGORY_BORDER_WIDTH
import com.huawei.tour.ui.tourlist.Dimens.CATEGORY_CORNER
import com.huawei.tour.ui.tourlist.Dimens.CATEGORY_TEXT_PADDING_HORIZONTAL
import com.huawei.tour.ui.tourlist.Dimens.CATEGORY_TEXT_PADDING_VERTICAL
import kotlinx.coroutines.flow.StateFlow

@Composable
fun TourCategoryListView(
    category: Category,
    selectedCategoryStateFlow: StateFlow<Category>,
    onClick: (category: Category) -> Unit,
) {
    val selectedCategoryState = selectedCategoryStateFlow.collectAsState()
    val isSelected = category.id == selectedCategoryState.value.id
    TourCategoryView(category, isSelected, onClick)
}

@Composable
fun TourCategoryView(
    category: Category,
    isSelected: Boolean,
    onClick: (category: Category) -> Unit,
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(size = CATEGORY_CORNER))
            .background(color = Color.White)
            .border(
                width = CATEGORY_BORDER_WIDTH,
                color = if (isSelected) {
                    colorResource(R.color.category_selected_color)
                } else {
                    colorResource(R.color.category_unselected_color)
                },
                shape = RoundedCornerShape(CATEGORY_CORNER),
            )
            .clickable {
                onClick.invoke(category)
            },
    ) {
        Text(
            modifier = Modifier.padding(
                vertical = CATEGORY_TEXT_PADDING_VERTICAL,
                horizontal = CATEGORY_TEXT_PADDING_HORIZONTAL,
            ),
            text = category.name,
            color = if (isSelected) {
                colorResource(R.color.category_selected_color)
            } else {
                colorResource(R.color.category_unselected_color)
            },
        )
    }
}
