package com.jp_funda.todomind.view.top

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
import com.jp_funda.todomind.view.MainViewModel
import com.jp_funda.todomind.view.components.*
import com.jp_funda.todomind.view.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopFragment : Fragment() {

    companion object {
        fun newInstance() = TopFragment()
    }

    private val topViewModel by viewModels<TopViewModel>()
    private val taskViewModel: TaskViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Set up data
        taskViewModel.refreshTaskListData()
        topViewModel.getMostRecentlyUpdatedMindMapWithDelay()
//        topViewModel.getMostRecentlyUpdatedMindMap()

        return ComposeView(requireContext()).apply {
            setContent {
                NewTaskFAB(onClick = {
                    NavHostFragment.findNavController(this@TopFragment)
                        .navigate(R.id.action_navigation_top_to_navigation_task_detail)
                }) {
                    TopContent()
                }
            }
        }
    }

    @Composable
    fun TopContent() {
        // mind map
        val mostRecentlyUpdatedMindMap by topViewModel.mostRecentlyUpdatedMindMap.observeAsState()
        // task
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

            ColumnWithTaskList(
                selectedTabStatus = selectedTabStatus,
                onTabChange = { status ->
                    selectedTabStatus = status
                },
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
                    if (Integer.max(fromIndex, toIndex) < showingTasks.size) {
                        val fromTask = showingTasks.sortedBy { task -> task.reversedOrder }
                            .reversed()[fromIndex]
                        val toTask = showingTasks.sortedBy { task -> task.reversedOrder }
                            .reversed()[toIndex]
                        taskViewModel.replaceReversedOrderOfTasks(fromTask, toTask)
                    }
                },
                onRowClick = { task ->
                    mainViewModel.editingTask = task
                    findNavController().navigate(R.id.action_navigation_top_to_navigation_task_detail)
                },
                isScrollToTopAtLaunch = true,
            ) {
                // TOP ORIGINAL CONTENT
                // Section Recent Mind Map
                RecentMindMapSection(
                    mindMap = mostRecentlyUpdatedMindMap,
                    fragment = this@TopFragment,
                    onNewMindMapClick = {
                    findNavController().navigate(R.id.action_navigation_top_to_navigation_mind_map_detail)
                })

                // Section Tasks
                Text(
                    text = "Tasks",
                    modifier = Modifier.padding(start = 20.dp),
                    color = Color.White,
                    style = MaterialTheme.typography.h6,
                )
            }
            // Snackbar
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

    // Top components
    @Composable
    fun AddButton(text: String, onClick: () -> Unit) {
        Button(
            onClick = onClick,
            modifier = Modifier.clip(RoundedCornerShape(1000.dp)),
            colors = ButtonDefaults.buttonColors(Color.White)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = text)
                Image(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add",
                )
            }
        }
    }
}