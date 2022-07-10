package com.jp_funda.todomind.view.mind_map_create.compoents

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.R
import com.jp_funda.todomind.view.mind_map_create.MindMapCreateViewModel
import kotlin.math.min
import kotlin.math.roundToInt

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun ZoomButtons() {
    val context = LocalContext.current
    val mindMapCreateViewModel = hiltViewModel<MindMapCreateViewModel>()

    val screenWidth = context.resources.displayMetrics.widthPixels
    val screenHeight = context.resources.displayMetrics.heightPixels
    val mapViewOriginalHeight = context.resources.getDimensionPixelSize(R.dimen.map_view_height)
    val mapViewOriginalWidth = context.resources.getDimensionPixelSize(R.dimen.map_view_width)

    val minScale = min(
        screenWidth.toFloat() / mapViewOriginalWidth.toFloat(),
        screenHeight.toFloat() / mapViewOriginalHeight.toFloat()
    )

    val observedUpdateCount = mindMapCreateViewModel.updateCount.observeAsState()

    observedUpdateCount.value.run {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd,
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color.White.copy(alpha = 0.1f)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = {
                        mindMapCreateViewModel.setScale(
                            mindMapCreateViewModel.getScale().plus(0.1f)
                        )
                    }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_zoom_24dp),
                        contentDescription = "Zoom in",
                        tint = Color.LightGray,
                    )
                }
                Box(modifier = Modifier.height(50.dp), contentAlignment = Alignment.Center) {
                    Text(
                        text = "${(mindMapCreateViewModel.getScale() * 100).roundToInt()}%",
                        color = Color.LightGray,
                    )
                }
                IconButton(onClick = {
                    if (mindMapCreateViewModel.getScale() - 0.1 <= minScale) {
                        mindMapCreateViewModel.setScale(minScale)
                    } else {
                        mindMapCreateViewModel.setScale(
                            mindMapCreateViewModel.getScale().minus(0.1f)
                        )
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_zoom_out_24dp),
                        contentDescription = "Zoom out",
                        tint = Color.LightGray,
                    )
                }
            }
        }
    }
}