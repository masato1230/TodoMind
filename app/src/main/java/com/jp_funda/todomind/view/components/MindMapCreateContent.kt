package com.jp_funda.todomind.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.view.mind_map_create.MindMapCreateViewModel
import com.jp_funda.todomind.view.mind_map_create.nodes.H1
import com.jp_funda.todomind.view.mind_map_create.nodes.MindMapNode

@Composable
fun MindMapCreateContent(
    modifier: Modifier = Modifier,
    mindMapCreateViewModel: MindMapCreateViewModel,
    onClickMindMapNode: (mindMap: MindMap) -> Unit,
    onClickTaskNode: (task: Task) -> Unit,
) {
    val observedUpdateCount = mindMapCreateViewModel.updateCount.observeAsState()

    // update views when update count is changed
    observedUpdateCount.value?.let { _ ->
        Box(modifier = modifier) {

            MindMapNode(
                mindMap = mindMapCreateViewModel.mindMap,
                viewModel = mindMapCreateViewModel,
            ) { onClickMindMapNode(mindMapCreateViewModel.mindMap) }

            // draw all tasks in mindMap
            for (task in mindMapCreateViewModel.tasks) {
                H1(task = task, viewModel = mindMapCreateViewModel) { onClickTaskNode(task) }
            }
        }
    }
}