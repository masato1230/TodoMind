package com.jp_funda.todomind.view.mind_map_detail.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.TestTag
import com.jp_funda.todomind.extension.getProgressRate
import com.jp_funda.todomind.view.components.RoundedProgressBar
import com.jp_funda.todomind.view.mind_map_create.MindMapCreateViewModel
import kotlin.math.roundToInt

@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun ProgressSection() {
    val mindMapThumbnailViewModel = hiltViewModel<MindMapCreateViewModel>()

    // observe task status update
    val observedUpdateCount = mindMapThumbnailViewModel.updateCount.observeAsState()
    observedUpdateCount.value?.let {
        // Progress description
        Row(
            modifier = Modifier
                .padding(start = 10.dp, bottom = 5.dp)
                .fillMaxWidth()
                .testTag(TestTag.MIND_MAP_DETAIL_PROGRESS_SECTION),
        ) {
            Text(
                text = "Progress: ",
                style = MaterialTheme.typography.body1,
                color = Color.White
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "${mindMapThumbnailViewModel.tasks.getProgressRate().roundToInt()}%",
                style = MaterialTheme.typography.body1,
                color = Color.White
            )
        }
        // Progress bar
        RoundedProgressBar(
            percent = mindMapThumbnailViewModel.tasks.getProgressRate().roundToInt()
        )
    }
}