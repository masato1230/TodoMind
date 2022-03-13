package com.jp_funda.todomind.view.mind_map_create.nodes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jp_funda.todomind.data.NodeStyle
import com.jp_funda.todomind.data.getSize
import com.jp_funda.todomind.data.getTextSize
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.view.mind_map_create.MindMapCreateViewModel

@Composable
fun H6(
    modifier: Modifier = Modifier,
    task: Task,
    viewModel: MindMapCreateViewModel,
    onClick: () -> Unit,
) {
    HeadlineNodeBase(
        modifier = modifier,
        task = task,
        viewModel = viewModel,
        circleSize = NodeStyle.HEADLINE_6.getSize().width.dp,
        fontSize = NodeStyle.HEADLINE_6.getTextSize(),
        textPadding = 15.dp,
        maxLines = 3,
        behindCircleColor = Color.Black,
    ) { onClick() }
}