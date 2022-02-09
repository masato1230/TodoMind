package com.jp_funda.todomind.view.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
import com.jp_funda.todomind.view.MainViewModel
import com.jp_funda.todomind.view.TaskViewModel
import com.jp_funda.todomind.view.components.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.lang.Integer.max

@AndroidEntryPoint
class TaskFragment : Fragment() {

    // ViewModels
    private val taskViewModel: TaskViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    companion object {
        fun newInstance() = TaskFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        taskViewModel.refreshTaskListData()

        return ComposeView(requireContext()).apply {
            setContent {
                NewTaskFAB(onClick = {
                    NavHostFragment.findNavController(this@TaskFragment)
                        .navigate(R.id.action_navigation_task_to_navigation_task_detail)
                }) {
                    TaskContent()
                }
            }
        }
    }

    @Preview
    @Composable
    fun TaskContent() {
        val tasks by taskViewModel.taskList.observeAsState()
        var selectedTabStatus by remember { mutableStateOf(TaskStatus.InProgress) }
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()

        // Show Undo snackbar if currentlyDeletedTask is not null
        LaunchedEffect(snackbarHostState) {
            mainViewModel.currentlyDeletedTask?.let {
                taskViewModel.showUndoDeleteSnackbar(
                    snackbarHostState = snackbarHostState,
                    deletedTask = it
                )
            }
            mainViewModel.currentlyDeletedTask = null
        }

        // Main Contents
        if (tasks != null) {
            var showingTasks by remember { mutableStateOf(tasks!!) }

            showingTasks = filterTasksByStatus(
                status = TaskStatus.values().first { it == selectedTabStatus },
                tasks = tasks!!,
            )

            Column {
                TaskTab(
                    selectedTabStatus = selectedTabStatus,
                    onTabChange = { status ->
                        selectedTabStatus = status
                    })

                ColumnWithTaskList(
                    selectedTabStatus = null,
                    showingTasks = showingTasks,
                    onCheckChanged = { task ->
                        taskViewModel.updateTaskWithDelay(task)
                        scope.launch {
                            taskViewModel.showCheckBoxChangedSnackbar(
                                task,
                                snackbarHostState
                            )
                        }
                    },
                    onRowMove = { fromIndex, toIndex ->
                        // Replace task's reversedOrder property
                        if (max(fromIndex, toIndex) < showingTasks.size) {
                            val fromTask = showingTasks.sortedBy { task -> task.reversedOrder }
                                .reversed()[fromIndex]
                            val toTask = showingTasks.sortedBy { task -> task.reversedOrder }
                                .reversed()[toIndex]
                            taskViewModel.replaceReversedOrderOfTasks(fromTask, toTask)
                        }
                    },
                    onRowClick = { task ->
                        mainViewModel.editingTask = task
                        findNavController().navigate(R.id.action_navigation_task_to_navigation_task_detail)
                    }
                )

                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    // Status update Snackbar
                    SnackbarHost(
                        hostState = snackbarHostState,
                        modifier = Modifier.padding(bottom = 70.dp)
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(150.dp)
                        .height(150.dp),
                    color = Color(resources.getColor(R.color.teal_200)),
                    strokeWidth = 10.dp
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "Loading...",
                    style = MaterialTheme.typography.h5,
                    color = Color.White
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mainViewModel.currentlyDeletedTask = null
    }
}