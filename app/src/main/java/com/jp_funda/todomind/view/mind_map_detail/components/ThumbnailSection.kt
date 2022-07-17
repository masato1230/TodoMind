package com.jp_funda.todomind.view.mind_map_detail.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.R
import com.jp_funda.todomind.view.mind_map_create.MindMapCreateViewModel
import com.jp_funda.todomind.view.mind_map_create.compoents.LineView
import com.jp_funda.todomind.view.mind_map_create.compoents.NodeGraph

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun ThumbnailSection(
    isFirstTime: Boolean,
    onClick: () -> Unit,
) {
    val context = LocalContext.current
    val mindMapThumbnailViewModel = hiltViewModel<MindMapCreateViewModel>()

    if (!isFirstTime) {
        val isLoadingState = mindMapThumbnailViewModel.isLoading.observeAsState()
        isLoadingState.value?.let { isLoading ->
            if (isLoading) {
                Text(text = "Loading...", color = Color.White)
            } else {
                Box(modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.Black)
                    .height(200.dp)
                    .fillMaxWidth()
                    .onSizeChanged {
                        // Adjust mind map scale to fit it to thumbnail
                        val scale =
                            it.width.toFloat() / context.resources.getDimensionPixelSize(R.dimen.map_view_width)
                        mindMapThumbnailViewModel.setScale(scale)
                    }
                ) {
                    // Line View
                    LineView()
                    // Node Graph
                    NodeGraph(
                        modifier = Modifier.fillMaxSize(),
                        onClickMindMapNode = {},
                        onClickTaskNode = {},
                    )
                    // Overlay for restricting map create operation by thumbnail
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .clickable { onClick() })
                }
            }
        }
    } else { // Thumbnail for first time
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color.Black)
                .height(200.dp)
                .fillMaxWidth()
                .clickable { onClick() },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_think_mind_map),
                contentDescription = "Mind Map Image",
                modifier = Modifier
                    .height(130.dp)
                    .fillMaxWidth(),
            )
            Text(
                text = "Click here to start mind mapping!",
                style = MaterialTheme.typography.caption,
                color = Color.LightGray,
            )
        }
    }
}