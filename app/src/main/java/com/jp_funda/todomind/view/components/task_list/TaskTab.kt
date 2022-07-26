package com.jp_funda.todomind.view.components.task_list

import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.jp_funda.todomind.R
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
            text = { Text("In Progress") }
        )
        Tab(
            selected = selectedTabStatus == TaskStatus.Open,
            onClick = { onTabChange(TaskStatus.Open) },
            text = { Text("Open") }
        )
        Tab(
            selected = selectedTabStatus == TaskStatus.Complete,
            onClick = { onTabChange(TaskStatus.Complete) },
            text = { Text("Complete") }
        )
    }
}
