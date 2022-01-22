package com.jp_funda.todomind.view.components

import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.jp_funda.todomind.R

@Composable
fun MindMapCard() {
    AndroidView(factory = {
        View.inflate(it, R.layout.card_mind_map, null)
    })
}