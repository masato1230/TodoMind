package com.jp_funda.todomind.view.mind_map_create.nodes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.NodeStyle
import com.jp_funda.todomind.data.getSize
import com.jp_funda.todomind.data.getTextSize
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.view.mind_map_create.MindMapCreateViewModel

@Composable
fun Body2(
    modifier: Modifier = Modifier,
    task: Task,
    viewModel: MindMapCreateViewModel,
    onClick: () -> Unit,
) {
    BodyNodeBase(
        modifier = modifier,
        task = task,
        viewModel = viewModel,
        iconImage = painterResource(id = R.drawable.ic_arrow_foward_24),
        size = NodeStyle.BODY_1.getSize(),
        fontSize = NodeStyle.BODY_1.getTextSize(),
        maxLines = 1,
    ) { onClick() }
}