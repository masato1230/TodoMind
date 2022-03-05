package com.jp_funda.todomind.view.mind_map_create.nodes

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.view.mind_map_create.MindMapCreateViewModel

@Composable
fun H1(
    modifier: Modifier = Modifier,
    task: Task,
    viewModel: MindMapCreateViewModel,
    onClick: () -> Unit,
) {
    HeadlineNodeBase(
        modifier = modifier,
        task = task,
        viewModel = viewModel,
        circleSize = 250.dp,
        fontSize = MaterialTheme.typography.h4.fontSize,
        textPadding = 30.dp,
        maxLines = 4,
        behindCircleColor = Color.Black,
    ) { onClick() }
}