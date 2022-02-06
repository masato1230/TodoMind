package com.jp_funda.todomind.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.fragment.findNavController
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
import kotlinx.coroutines.launch

@Composable
fun ColumnWithTaskList(
    selectedTabStatus: TaskStatus,
    onTabChange: (TaskStatus) -> Unit,
    showingTasks: List<Task>,
    onCheckChanged: (Task) -> Unit,
    onRowMove: (Int, Int) -> Unit,
    onRowClick: (Task) -> Unit,
) {
    Column {

        TaskTab(selectedTabStatus, onTabChange)

        TaskList(
            listPadding = 20,
            tasks = showingTasks,
            onCheckChanged = onCheckChanged,
            onMove = onRowMove,
            onRowClick = onRowClick
        )
    }
}