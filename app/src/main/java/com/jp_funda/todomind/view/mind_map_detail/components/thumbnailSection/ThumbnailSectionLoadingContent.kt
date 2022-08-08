package com.jp_funda.todomind.view.mind_map_detail.components.thumbnailSection

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jp_funda.todomind.R
import com.jp_funda.todomind.view.components.AnimatedShimmer

@Composable
fun ThumbnailSectionLoadingContent() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .height(200.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        AnimatedShimmer(modifier = Modifier.fillMaxSize())
        Text(
            text = stringResource(id = R.string.loading),
            color = Color.LightGray,
            style = MaterialTheme.typography.h5,
        )
    }
}