package com.jp_funda.todomind.view.task

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
import com.jp_funda.todomind.navigation.RouteGenerator
import com.jp_funda.todomind.view.MainViewModel
import com.jp_funda.todomind.view.TaskViewModel
import com.jp_funda.todomind.view.components.*
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun TaskScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
) {
    val taskViewModel = hiltViewModel<TaskViewModel>()

    LaunchedEffect(Unit) {
        taskViewModel.refreshTaskListData()
    }

    NewTaskFAB(
        topBar = {
            TopAppBar(
                title = { Text(text = "Task") },
                backgroundColor = colorResource(id = R.color.deep_purple),
                contentColor = Color.White,
            )
        },
        onClick = {
            navController.navigate(RouteGenerator.TaskDetail(null)())
        }
    ) {
        TaskContent(navController, mainViewModel)
    }
}

@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun TaskContent(
    navController: NavController,
    mainViewModel: MainViewModel,
) {
    val taskViewModel = hiltViewModel<TaskViewModel>()

    val observedTasks by taskViewModel.taskList.observeAsState()
    val selectedTabStatus by taskViewModel.selectedStatusTab.observeAsState(TaskStatus.InProgress)
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
    observedTasks?.let { tasks ->
        var showingTasks by remember { mutableStateOf(tasks) }

        showingTasks = filterTasksByStatus(
            status = TaskStatus.values().first { it == selectedTabStatus },
            tasks = tasks,
        )

        Column {
            ColumnWithTaskList(
                selectedTabStatus = selectedTabStatus,
                onTabChange = { status ->
                    taskViewModel.setSelectedStatusTab(status)
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
                    navController.navigate(RouteGenerator.TaskDetail(task.id)())
                }
            ) {
                // Advertisement
                BannerAd(
                    width = LocalConfiguration.current.screenWidthDp,
                    modifier = Modifier.heightIn(min = 60.dp),
                )
            }

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
    } ?: run {
        LoadingView()
    }
}
