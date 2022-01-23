package com.jp_funda.todomind.view.components

import android.view.View
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.jp_funda.todomind.R

@Composable
fun TaskRow(modifier: Modifier = Modifier) {
    AndroidView(
        factory = {
            View.inflate(it, R.layout.row_task, null)
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
    )
}