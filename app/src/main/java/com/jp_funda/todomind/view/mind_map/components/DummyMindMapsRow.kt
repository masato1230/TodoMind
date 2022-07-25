package com.jp_funda.todomind.view.mind_map.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.jp_funda.todomind.view.components.AnimatedShimmer

@Composable
fun DummyMindMapsRow() {
    Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
        (1..2).forEach { _ ->
            val dummyMindMapCardModifier =
                Modifier
                    .padding(start = 20.dp)
                    .height(200.dp)
                    .width(150.dp)
                    .clip(RoundedCornerShape(20.dp))
            AnimatedShimmer(modifier = dummyMindMapCardModifier)
        }
    }
}