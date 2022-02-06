package com.jp_funda.todomind.view.components

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.findNavController
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
import com.jp_funda.todomind.view.task.DragDropListState
import com.jp_funda.todomind.view.task.getVisibleItemInfoFor
import com.jp_funda.todomind.view.task.offsetEnd
import com.jp_funda.todomind.view.task.rememberDragDropListState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun ColumnWithTaskList(
    modifier: Modifier = Modifier,
    selectedTabStatus: TaskStatus,
    onTabChange: (TaskStatus) -> Unit,
    showingTasks: List<Task>,
    onCheckChanged: (Task) -> Unit,
    onRowMove: (Int, Int) -> Unit,
    onRowClick: (Task) -> Unit,
    content: @Composable () -> Unit = {},
) {
    val scope = rememberCoroutineScope()
    var overScrollJob by remember { mutableStateOf<Job?>(null) }
    val dragDropListState = rememberDragDropListState(ignoreCount = 2, onMove = onRowMove)
    val haptic = LocalHapticFeedback.current

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
                            } ?: run { overScrollJob?.cancel() }
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
        // Content
        item {
            content()
        }

        // Tab
        item {
            TaskTab(selectedTabStatus, onTabChange)
        }

        // List
        itemsIndexed(showingTasks) { index, task ->
            Column(
                modifier = Modifier
                    .composed {
                        val offsetOrNull = dragDropListState.elementDisplacement.takeIf {
                            index == dragDropListState.currentIndexOfDraggedItem?.minus(2) ?: 0
                        } // lazyColumn counts other than items, so minus 2 from index
                        Modifier.graphicsLayer {
                            translationY = offsetOrNull ?: 0f
                        }
                    }
                    .fillMaxWidth()
            ) {
                TaskRow(
                    task = task,
                    onCheckChanged = onCheckChanged,
                    onClick = { onRowClick(task) })
            }
        }

        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}