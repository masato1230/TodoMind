package com.jp_funda.todomind.view.mind_map_create.compoents

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.data.repositories.task.entity.NodeStyle
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.view.mind_map_create.MindMapCreateViewModel
import com.jp_funda.todomind.view.mind_map_create.nodes.*

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun NodeGraph(
    modifier: Modifier = Modifier,
    onClickMindMapNode: (mindMap: MindMap) -> Unit,
    onClickTaskNode: (task: Task) -> Unit,
) {
    val mindMapCreateViewModel = hiltViewModel<MindMapCreateViewModel>()

    val observedUpdateCount = mindMapCreateViewModel.updateCount.observeAsState()

    // Update views when update count is changed
    observedUpdateCount.value?.let {
        Box(modifier = modifier) {

            MindMapNode(
                mindMap = mindMapCreateViewModel.mindMap,
                viewModel = mindMapCreateViewModel,
            ) { onClickMindMapNode(mindMapCreateViewModel.mindMap) }

            // draw all tasks in mindMap
            for (task in mindMapCreateViewModel.tasks) {
                when (task.styleEnum) {
                    NodeStyle.HEADLINE_1 -> H1(
                        task = task,
                        viewModel = mindMapCreateViewModel
                    ) { onClickTaskNode(task) }
                    NodeStyle.HEADLINE_2 -> H2(
                        task = task,
                        viewModel = mindMapCreateViewModel
                    ) { onClickTaskNode(task) }
                    NodeStyle.HEADLINE_3 -> H3(
                        task = task,
                        viewModel = mindMapCreateViewModel
                    ) { onClickTaskNode(task) }
                    NodeStyle.HEADLINE_4 -> H4(
                        task = task,
                        viewModel = mindMapCreateViewModel
                    ) { onClickTaskNode(task) }
                    NodeStyle.BODY_1 -> Body1(
                        task = task,
                        viewModel = mindMapCreateViewModel
                    ) { onClickTaskNode(task) }
                    NodeStyle.BODY_2 -> Body2(
                        task = task,
                        viewModel = mindMapCreateViewModel
                    ) { onClickTaskNode(task) }
                    NodeStyle.LINK -> Link(
                        task = task,
                        viewModel = mindMapCreateViewModel
                    ) { onClickTaskNode(task) }
                }
            }
        }
    }
}