package com.jp_funda.todomind.view.task_detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.jp_funda.todomind.view.components.AnimatedShimmer

@Composable
fun TaskEditLoadingContent() {
    Column(modifier = Modifier.padding(20.dp)) {
        AnimatedShimmer(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .height(50.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
        )
        ShimmerRowWithIcon(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth(0.8f)
        )
        ShimmerRowWithIcon(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth(0.7f)
        )
        ShimmerRowWithIcon(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth(0.6f)
        )
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
        Spacer(modifier = Modifier.height(30.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            AnimatedShimmer(
                modifier = Modifier
                    .height(50.dp)
                    .width(150.dp)
                    .clip(RoundedCornerShape(1000.dp))
                    .fillMaxWidth(0.5f)
            )
            Spacer(modifier = Modifier.width(30.dp))
            AnimatedShimmer(
                modifier = Modifier
                    .height(50.dp)
                    .width(150.dp)
                    .clip(RoundedCornerShape(1000.dp))
                    .fillMaxWidth(0.5f)
            )
        }
    }
}

@Composable
fun ShimmerRowWithIcon(modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AnimatedShimmer(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(10.dp))
        AnimatedShimmer(
            modifier = Modifier
                .height(50.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
        )
    }
}