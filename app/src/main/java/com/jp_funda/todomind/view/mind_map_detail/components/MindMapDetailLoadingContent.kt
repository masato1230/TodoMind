package com.jp_funda.todomind.view.mind_map_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.jp_funda.todomind.view.components.shimmer.AnimatedShimmer
import com.jp_funda.todomind.view.components.shimmer.ShimmerRowWithIcon
import com.jp_funda.todomind.view.mind_map_detail.components.thumbnailSection.ThumbnailSectionLoadingContent

@Composable
fun MindMapDetailLoadingContent() {
    Column {
        AnimatedShimmer(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        ThumbnailSectionLoadingContent()
        ShimmerRowWithIcon(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth(0.8f)
        )
        ShimmerRowWithIcon(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth(0.5f)
        )
        ShimmerRowWithIcon(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth(0.8f)
        )
    }
}