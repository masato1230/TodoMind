package com.jp_funda.todomind.view.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jp_funda.todomind.R

@Composable
fun RoundedProgressBar(
    modifier: Modifier = Modifier,
    percent: Int,
    height: Dp = 10.dp,
    backgroundColor: Color = colorResource(id = R.color.white_10),
    foregroundColor: Brush = Brush.horizontalGradient(
        listOf(colorResource(id = R.color.teal_200), colorResource(id = R.color.teal_200))
    ),
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(20.dp))
    ) {
        Box(
            modifier = modifier
                .background(backgroundColor)
                .fillMaxWidth()
                .height(height)
        )
        Box(
            modifier = modifier
                .animateContentSize(animationSpec = tween(durationMillis = 1500))
                .background(foregroundColor)
                .width(maxWidth * percent / 100)
                .height(height)
        )
    }
}