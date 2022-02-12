package com.jp_funda.todomind.view.components

import android.view.View
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap

@Composable
fun MindMapCard(
    mindMap: MindMap,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    AndroidView(
        factory = {
            View.inflate(it, R.layout.card_mind_map, null)
        },
        update = { view ->
            // Initialize view
        },
        modifier = modifier.clickable {
            onClick()
        }
    )
}