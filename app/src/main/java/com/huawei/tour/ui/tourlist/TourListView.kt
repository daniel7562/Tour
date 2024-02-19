package com.huawei.tour.ui.tourlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.huawei.tour.R
import com.huawei.tour.data.TourItem
import com.huawei.tour.ui.tourlist.Dimens.BOTTOM_LOADING_INDICATOR_PADDING
import com.huawei.tour.ui.tourlist.Dimens.TOUR_ITEM_ADDRESS_PADDING_RIGHT
import com.huawei.tour.ui.tourlist.Dimens.TOUR_ITEM_HORIZONTAL_PADDING
import com.huawei.tour.ui.tourlist.Dimens.TOUR_ITEM_IMAGE_PADDING_RIGHT
import com.huawei.tour.ui.tourlist.Dimens.TOUR_ITEM_IMAGE_SIZE
import com.huawei.tour.ui.tourlist.Dimens.TOUR_ITEM_NAME_PADDING_RIGHT
import com.huawei.tour.ui.tourlist.Dimens.TOUR_ITEM_VERTICAL_PADDING

@Composable
fun TourItemView(item: TourItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = TOUR_ITEM_VERTICAL_PADDING,
                horizontal = TOUR_ITEM_HORIZONTAL_PADDING,
            ),
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.images.first().src)
                    .scale(Scale.FIT)
                    .build(),
            ),
            contentDescription = null,
            modifier = Modifier
                .size(TOUR_ITEM_IMAGE_SIZE)
                .aspectRatio(1.0f),
        )
        Spacer(modifier = Modifier.width(TOUR_ITEM_IMAGE_PADDING_RIGHT))
        DescriptionView(item)
    }
}

@Composable
fun DescriptionView(item: TourItem) {
    val resources = LocalContext.current.resources
    Column {
        Text(
            text = item.name,
        )
        Spacer(modifier = Modifier.height(TOUR_ITEM_NAME_PADDING_RIGHT))
        Text(
            text = "${resources.getString(R.string.tour_item_address)}${item.address}",
        )
        Spacer(modifier = Modifier.height(TOUR_ITEM_ADDRESS_PADDING_RIGHT))
        Text(
            text = "${resources.getString(R.string.tour_item_open_time)}${item.openTime}",
        )
    }
}

@Composable
fun BottomLoadingIndicator(modifier: Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .fillMaxWidth()
            .padding(BOTTOM_LOADING_INDICATOR_PADDING)
            .wrapContentWidth(Alignment.CenterHorizontally),
    )
}
