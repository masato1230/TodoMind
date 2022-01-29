package com.jp_funda.todomind.view.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.viewModels
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
import com.jp_funda.todomind.view.components.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskFragment : Fragment() {

    private val taskViewModel by viewModels<TaskViewModel>()

    companion object {
        fun newInstance() = TaskFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        for (i in 0..1) {
            taskViewModel.addDummyTask()
        }
        taskViewModel.refreshTaskListData()

        return ComposeView(requireContext()).apply {
            setContent {
                NewTaskFAB(onClick = {}) { // TODO add navigation to new task screen
                    TaskContent()
                }
            }
        }
    }

    @Preview
    @Composable
    fun TaskContent() {
        val tasks by taskViewModel.taskList.observeAsState()
        val showingTasks by taskViewModel.showingTasks.observeAsState()
        var selectedTabIndex by remember { mutableStateOf(0) }

        if (!tasks.isNullOrEmpty()) {
            taskViewModel.updateShowingTasks(
                TaskStatus.values().first { it.ordinal == selectedTabIndex })

            Column {
                TaskTab(selectedTabIndex, onTabChange = { status ->
                    taskViewModel.updateShowingTasks(status)
                    selectedTabIndex = status.ordinal
                })

                TaskList(
                    listPadding = 20,
                    tasks = showingTasks!!,
                    onCheckChanged = { task -> taskViewModel.updateTaskStatus(task) })
            }
        } else {
            CircularProgressIndicator()
        }
    }
}