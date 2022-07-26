package com.jp_funda.todomind.view.top

import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.android.play.core.review.ReviewManagerFactory
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.SampleData
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
import com.jp_funda.todomind.extension.getActivity
import com.jp_funda.todomind.navigation.RouteGenerator
import com.jp_funda.todomind.view.MainViewModel
import com.jp_funda.todomind.view.TaskViewModel
import com.jp_funda.todomind.view.components.BannerAd
import com.jp_funda.todomind.view.components.NewTaskFAB
import com.jp_funda.todomind.view.components.RecentMindMapSection
import com.jp_funda.todomind.view.components.task_list.ColumnWithTaskList
import com.jp_funda.todomind.view.components.task_list.filterTasksByStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun TopScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
) {
    val taskViewModel = hiltViewModel<TaskViewModel>()
    val topViewModel = hiltViewModel<TopViewModel>()

    LaunchedEffect(Unit) {
        // Load data
        taskViewModel.refreshTaskListData()
        delay(1000) // todo delete
        topViewModel.getMostRecentlyUpdatedMindMap()
    }

    NewTaskFAB(
        topBar = {
            TopAppBar(
                title = { Text(text = "TodoMind") },
                backgroundColor = colorResource(id = R.color.deep_purple),
                contentColor = Color.White,
                navigationIcon = {
                    Spacer(modifier = Modifier.width(20.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_mind_map),
                        contentDescription = "App Icon",
                    )
                }
            )
        },
        onClick = {
            navController.navigate(RouteGenerator.TaskDetail(null)())
        }) {
        TopContent(navController, mainViewModel)
    }
}

@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun TopContent(
    navController: NavController,
    mainViewModel: MainViewModel,
) {
    val context = LocalContext.current
    val topViewModel = hiltViewModel<TopViewModel>()
    val taskViewModel = hiltViewModel<TaskViewModel>()

    // mind map
    val mostRecentlyUpdatedMindMap by topViewModel.mostRecentlyUpdatedMindMap.observeAsState()
    // task
    val observedTasks by taskViewModel.taskList.observeAsState()
    val selectedTabStatus by taskViewModel.selectedStatusTab.observeAsState(TaskStatus.InProgress)
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Show Undo snackbar if currentlyDeletedTask is not null
    LaunchedEffect(snackbarHostState) {
        mainViewModel.currentlyDeletedTask?.let {
            mainViewModel.currentlyDeletedTask = null
            taskViewModel.showUndoDeleteSnackbar(
                snackbarHostState = snackbarHostState,
                deletedTask = it,
            )
        }
    }

    LaunchedEffect(observedTasks) {
        if (observedTasks == null) return@LaunchedEffect
        // Check whether to request review is needed
        if (!topViewModel.isReviewRequested && observedTasks!!.size > 10 + SampleData.sampleTasks.size) {
            requestAppReview(context)
            topViewModel.setIsReviewRequested(true)
        }
    }

    // Main Contents
    val showingTasks = observedTasks?.let {
        filterTasksByStatus(
            status = TaskStatus.values().first { it == selectedTabStatus },
            tasks = it
        )
    }
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
                    snackbarHostState,
                )
            }
        },
        onRowMove = { fromIndex, toIndex ->
            if (showingTasks != null) {
                // Replace task's reversedOrder property
                if (Integer.max(fromIndex, toIndex) < showingTasks.size) {
                    val fromTask = showingTasks.sortedBy { task -> task.reversedOrder }
                        .reversed()[fromIndex]
                    val toTask = showingTasks.sortedBy { task -> task.reversedOrder }
                        .reversed()[toIndex]
                    taskViewModel.replaceReversedOrderOfTasks(fromTask, toTask)
                }
            }
        },
        onRowClick = { task ->
            navController.navigate(RouteGenerator.TaskDetail(task.id)())
        },
        isScrollToTopAtLaunch = true,
    ) {
        // TOP ORIGINAL CONTENT
        // Section Recent Mind Map
        RecentMindMapSection(
            mindMap = mostRecentlyUpdatedMindMap,
            onRecentMindMapClick = {
                navController.navigate(
                    RouteGenerator.MindMapDetail(mostRecentlyUpdatedMindMap?.id)()
                )
            },
            onNewMindMapClick = {
                navController.navigate(RouteGenerator.MindMapDetail(null)())
            },
        )

        // Advertisement
        BannerAd(
            width = LocalConfiguration.current.screenWidthDp,
            modifier = Modifier.heightIn(min = 60.dp),
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Section Tasks
        Text(
            text = "Tasks",
            modifier = Modifier.padding(start = 20.dp),
            color = Color.White,
            style = MaterialTheme.typography.h6,
        )
    }

    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Bottom
    ) {
        // Status update Snackbar
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.padding(bottom = 70.dp),
        )
    }
}

private fun requestAppReview(context: Context) {
    val manager = ReviewManagerFactory.create(context)
    val request = manager.requestReviewFlow()
    request.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            // We got the ReviewInfo object
            val reviewInfo = task.result
            context.getActivity()?.let {
                manager.launchReviewFlow(it, reviewInfo)
            }
        } else {
            // There was some problem, log or handle the error code.
            task.exception?.printStackTrace()
        }
    }
}