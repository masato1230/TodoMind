package com.jp_funda.todomind.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
import com.jp_funda.todomind.view.task.TaskViewModel

@Composable
fun TaskLists(
    tasks: List<Task>,
    listPadding: Int = 20,
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    var showingTask by remember { mutableStateOf(tasks) }

    Column {
        TaskTab(selectedTabIndex, onTabChange = {}) // TODO implement onTabChange

        TaskList(listPadding = listPadding, tasks = tasks)
    }

    fun filterTasksByStatus(status: TaskStatus) {
        showingTask = when (status) {
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
}


@Composable
fun TaskList(tasks: List<Task>, listPadding: Int) {
    LazyColumn(modifier = Modifier.padding(horizontal = listPadding.dp)) {
        // todo fill with data
        items(items = tasks) { task ->
            TaskRow(task)
        }

        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}