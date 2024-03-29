package com.jp_funda.todomind.view.mind_map_create.nodes

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
fun Body1(
    modifier: Modifier = Modifier,
    task: Task,
    viewModel: MindMapCreateViewModel,
    onClick: () -> Unit,
) {
    BodyNodeBase(
        modifier = modifier,
        task = task,
        viewModel = viewModel,
        iconImage = painterResource(id = R.drawable.ic_checkbox_unchecked),
        size = NodeStyle.BODY_1.getSize(),
        fontSize = NodeStyle.BODY_1.getTextSize(),
        maxLines = 1,
    ) { onClick() }
}