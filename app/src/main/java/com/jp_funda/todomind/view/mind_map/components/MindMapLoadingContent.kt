package com.jp_funda.todomind.view.mind_map.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jp_funda.todomind.view.components.RecentMindMapSection

@Composable
fun MindMapLoadingContent(onNewMindMapClick: () -> Unit) {
    Column {
        RecentMindMapSection(
            mindMap = null,
            onRecentMindMapClick = { /* do nothing */ },
            onNewMindMapClick = { onNewMindMapClick() })
        Text(
            text = "Mind Maps",
            modifier = Modifier.padding(15.dp),
            color = Color.White,
            style = MaterialTheme.typography.h6,
        )
        DummyMindMapsRow()
    }
}