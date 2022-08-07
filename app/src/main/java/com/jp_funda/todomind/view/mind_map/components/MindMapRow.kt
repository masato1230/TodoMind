package com.jp_funda.todomind.view.mind_map.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jp_funda.todomind.database.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.view.components.MindMapCard

@Composable
fun MindMapsRow(
    mindMaps: List<MindMap>,
    onClickMindMap: (mindMap: MindMap) -> Unit,
) {
    LazyRow(modifier = Modifier.padding(bottom = 20.dp)) {
        items(items = mindMaps) { mindMap ->
            MindMapCard(
                mindMap = mindMap,
                onClick = { onClickMindMap(mindMap) },
            )
        }
    }
}