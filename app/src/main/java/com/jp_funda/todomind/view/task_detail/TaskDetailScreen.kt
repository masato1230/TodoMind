package com.jp_funda.todomind.view.task_detail

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.R
import com.jp_funda.todomind.navigation.RouteGenerator
import com.jp_funda.todomind.view.MainViewModel
import com.jp_funda.todomind.view.components.BackNavigationIcon
import com.jp_funda.todomind.view.components.BannerAd
import com.jp_funda.todomind.view.components.TaskEditContent
import java.util.*

@ExperimentalComposeUiApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun TaskDetailScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    taskId: String?,
) {
    val taskDetailViewModel = hiltViewModel<TaskDetailViewModel>()

    LaunchedEffect(Unit) {
        Log.d("TaskID", taskId.toString())
        taskId?.let { taskDetailViewModel.loadEditingTask(UUID.fromString(it)) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = if (taskDetailViewModel.isEditing) "Task Detail" else "New Task") },
                backgroundColor = colorResource(id = R.color.deep_purple),
                contentColor = Color.White,
                navigationIcon = { BackNavigationIcon(navController) },
                actions = {
                    taskDetailViewModel.task.value?.mindMap?.let {
                        val onClick = {
                            navController.navigate(
                                RouteGenerator.MindMapCreate(
                                    mindMapId = it.id,
                                    locationX = taskDetailViewModel.task.value?.x ?: 0f,
                                    locationY = taskDetailViewModel.task.value?.y ?: 0f,
                                )()
                            )
                        }
                        val color = it.color?.let { color -> Color(color) }
                            ?: run { colorResource(id = R.color.crimson) }
                        IconButton(onClick = onClick) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_mind_map),
                                contentDescription = "Mind Map",
                                tint = color,
                            )
                        }
                        Text(
                            text = it.title ?: "",
                            color = color,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            modifier = Modifier
                                .widthIn(max = 120.dp)
                                .clickable { onClick() }
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
            )
        },
        backgroundColor = colorResource(id = R.color.deep_purple),
    ) {
        TaskDetailContent(navController, mainViewModel)
    }
}

@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun TaskDetailContent(
    navController: NavController,
    mainViewModel: MainViewModel,
) {
    val taskDetailViewModel = hiltViewModel<TaskDetailViewModel>()

    Column {
        TaskEditContent(
            taskEditableViewModel = taskDetailViewModel,
            mainViewModel = mainViewModel,
        ) { navController.popBackStack() }

        Spacer(modifier = Modifier.weight(1f))

        BannerAd(
            width = LocalConfiguration.current.screenWidthDp,
            modifier = Modifier.heightIn(min = 60.dp),
        )
    }
}