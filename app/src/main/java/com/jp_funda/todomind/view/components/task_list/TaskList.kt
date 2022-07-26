package com.jp_funda.todomind.view.components.task_list

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
import com.jp_funda.todomind.view.task.rememberDragDropListState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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
    modifier: Modifier = Modifier,
    onRowClick: (Task) -> Unit,
) {
    val scope = rememberCoroutineScope()
    var overScrollJob by remember { mutableStateOf<Job?>(null) }
    val dragDropListState = rememberDragDropListState(onMove = onMove)
    val haptic = LocalHapticFeedback.current

    LazyColumn(
        modifier = modifier
            .padding(horizontal = listPadding.dp)
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
                    onDragStart = { offset ->
                        dragDropListState.onDragStart(offset)
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    },
                    onDragEnd = { dragDropListState.onDragInterrupted() },
                    onDragCancel = { dragDropListState.onDragInterrupted() }
                )
            },
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
                    .fillMaxWidth()
            ) {
                TaskRow(task = task, onCheckChanged = onCheckChanged, onClick = { onRowClick(task) })
            }
        }

        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}