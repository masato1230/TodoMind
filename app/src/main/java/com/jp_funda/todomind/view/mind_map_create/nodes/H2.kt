package com.jp_funda.todomind.view.mind_map_create.nodes

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.repositories.task.entity.NodeStyle
import com.jp_funda.repositories.task.entity.Task
import com.jp_funda.todomind.extension.getSize
import com.jp_funda.todomind.extension.getTextSize
import com.jp_funda.todomind.view.mind_map_create.MindMapCreateViewModel

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun H2(
    modifier: Modifier = Modifier,
    task: Task,
    viewModel: MindMapCreateViewModel,
    onClick: () -> Unit,
) {
    HeadlineNodeBase(
        modifier = modifier,
        task = task,
        viewModel = viewModel,
        circleSize = NodeStyle.HEADLINE_2.getSize().width.dp,
        fontSize = NodeStyle.HEADLINE_2.getTextSize(),
        textPadding = 30.dp,
        maxLines = 4,
        behindCircleColor = Color.Black,
    ) { onClick() }
}