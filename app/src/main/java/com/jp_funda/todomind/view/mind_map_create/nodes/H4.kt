package com.jp_funda.todomind.view.mind_map_create.nodes

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.R
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
fun H4(
    modifier: Modifier = Modifier,
    task: Task,
    viewModel: MindMapCreateViewModel,
    onClick: () -> Unit,
) {
    HeadlineNodeBase(
        modifier = modifier,
        task = task,
        viewModel = viewModel,
        circleSize = NodeStyle.HEADLINE_4.getSize().width.dp,
        fontSize = NodeStyle.HEADLINE_4.getTextSize(),
        textPadding = 30.dp,
        maxLines = 3,
        behindCircleColor = colorResource(id = R.color.dark),
        behindCircleRadiusOffset = 0f,
        behindCircleWidth = 20f,
    ) { onClick() }
}