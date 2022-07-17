package com.jp_funda.todomind.view.mind_map_create.compoents

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.view.mind_map_create.MindMapCreateViewModel
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun ZoomLevelIndicator() {
    val mindMapCreateViewModel = hiltViewModel<MindMapCreateViewModel>()
    val observedUpdateCount = mindMapCreateViewModel.updateCount.observeAsState()

    observedUpdateCount.value.run {
        // Properties for visibility animation
        var isVisible by remember { mutableStateOf(false) }
        val animatedAlpha by animateFloatAsState(
            targetValue = if (isVisible) 1f else 0f,
            animationSpec = if (isVisible) {
                keyframes { durationMillis = 0 }
            } else {
                keyframes { durationMillis = 1000 }
            }
        )

        // LaunchEffect to start visibility animation
        LaunchedEffect(this) {
            isVisible = true
            delay(4000)
            isVisible = false
        }

        Box(
            modifier = Modifier
                .size(100.dp)
                .padding(10.dp)
                .clip(RoundedCornerShape(10.dp))
                .alpha(animatedAlpha)
                .background(Color.White.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "${(mindMapCreateViewModel.getScale() * 100).roundToInt()}%",
                color = Color.LightGray,
            )
        }
    }
}