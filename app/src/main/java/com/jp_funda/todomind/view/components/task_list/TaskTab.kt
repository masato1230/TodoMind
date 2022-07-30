package com.jp_funda.todomind.view.components.task_list

import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus

@Composable
fun TaskTab(selectedTabStatus: TaskStatus, onTabChange: (clickedTabIndex: TaskStatus) -> Unit) {
    TabRow(
        selectedTabIndex = selectedTabStatus.ordinal,
        backgroundColor = colorResource(id = R.color.deep_purple),
        contentColor = Color.White,
    ) {
        Tab(
            selected = selectedTabStatus == TaskStatus.InProgress,
            onClick = { onTabChange(TaskStatus.InProgress) },
            text = { Text(stringResource(id = R.string.task_in_progress)) }
        )
        Tab(
            selected = selectedTabStatus == TaskStatus.Open,
            onClick = { onTabChange(TaskStatus.Open) },
            text = { Text(stringResource(id = R.string.task_open)) }
        )
        Tab(
            selected = selectedTabStatus == TaskStatus.Complete,
            onClick = { onTabChange(TaskStatus.Complete) },
            text = { Text(stringResource(id = R.string.task_complete)) }
        )
    }
}

fun filterTasksByStatus(status: TaskStatus, tasks: List<Task>): List<Task> {
    return when (status) {
        TaskStatus.Open -> {
            tasks.filter { it.statusEnum == TaskStatus.Open }
        }
        TaskStatus.InProgress -> {
            tasks.filter { it.statusEnum == TaskStatus.InProgress }
        }
        TaskStatus.Complete -> {
            tasks.filter { it.statusEnum == TaskStatus.Complete }
        }
    }
}
