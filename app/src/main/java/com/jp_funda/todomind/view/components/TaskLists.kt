package com.jp_funda.todomind.view.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
import com.jp_funda.todomind.view.task.TaskViewModel
import com.jp_funda.todomind.view.task.rememberDragDropListState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.concurrent.Flow

@Composable
fun TaskLists(
    tasks: List<Task>,
    onCheckChanged: (task: Task) -> Unit,
    listPadding: Int = 20,
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    var showingTasks by remember {
        mutableStateOf(
            filterTasksByStatus(
                status = TaskStatus.values()[selectedTabIndex],
                tasks = tasks,
            )
        )
    }

    Column {
        TaskTab(selectedTabIndex, onTabChange = { status ->
            showingTasks = filterTasksByStatus(status, tasks)
            selectedTabIndex = status.ordinal
        })

        TaskList(
            listPadding = listPadding,
            tasks = showingTasks,
            onCheckChanged = { task -> onCheckChanged(task) },
            onMove = { _, _ -> Log.d("son", "d") })
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


@Composable
fun TaskList(
    tasks: List<Task>,
    onCheckChanged: (task: Task) -> Unit,
    listPadding: Int,
    // new
    onMove: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = Modifier
            .padding(horizontal = listPadding.dp)
    ) {
        items(items = tasks) { task ->
            TaskRow(task, onCheckChanged)
        }

        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }

    // new
    val scope = rememberCoroutineScope()
    var overScrollJob by remember { mutableStateOf<Job?>(null) }
    val dragDropListState = rememberDragDropListState(onMove = onMove)

    LazyColumn(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDrag = { change, offset ->
                        change.consumeAllChanges()
                        dragDropListState.onDrag(offset = offset)

                        if (overScrollJob?.isActive == true)
                            return@detectDragGesturesAfterLongPress

                        dragDropListState
                            .checkForOverScroll()
                            .takeIf { it != 0f }
                            ?.let {
                                overScrollJob = scope.launch {
                                    dragDropListState.lazyListState.scrollBy(it)
                                }
                            } ?: kotlin.run { overScrollJob?.cancel() }
                    },
                    onDragStart = { offset -> dragDropListState.onDragStart(offset) },
                    onDragEnd = { dragDropListState.onDragInterrupted() },
                    onDragCancel = { dragDropListState.onDragInterrupted() }
                )
            }
            .fillMaxSize()
            .padding(top = 10.dp, start = 10.dp, end = 10.dp),
        state = dragDropListState.lazyListState
    ) {
        itemsIndexed(tasks) { index, task ->
            Column(
                modifier = Modifier
                    .composed {
                        val offsetOrNull = dragDropListState.elementDisplacement.takeIf {
                            index == dragDropListState.currentIndexOfDraggedItem
                        }
                        Modifier.graphicsLayer {
                            translationY = offsetOrNull ?: 0f
                        }
                    }
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = task.title ?: "",
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Serif
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}