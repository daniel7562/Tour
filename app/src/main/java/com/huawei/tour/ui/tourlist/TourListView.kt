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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.huawei.tour.data.TourItem

@Composable
fun TourItemView(item: TourItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 8.dp,
                horizontal = 16.dp,
            ),
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.images[0].src)
                    .scale(Scale.FIT)
                    .build(),
            ),
            contentDescription = null,
            modifier = Modifier
                .size(104.dp)
                .aspectRatio(1.0f),
        )
        Spacer(modifier = Modifier.width(4.dp))
        DescriptionView(item)
    }
}

@Composable
fun DescriptionView(item: TourItem) {
    Column {
        Text(
            text = item.name,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "地址：${item.address}",
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "開放時間：${item.openTime}",
        )
    }
}
