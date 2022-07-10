package com.jp_funda.todomind.navigation.arguments

import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.view.mind_map_create.Location

data class MindMapCreateArguments(
    val editingMindMap: MindMap,
    val initialLocation: Location?,
)