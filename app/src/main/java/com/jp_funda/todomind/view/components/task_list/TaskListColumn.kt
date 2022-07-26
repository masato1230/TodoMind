package com.jp_funda.todomind.view.components.task_list

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
import com.jp_funda.todomind.view.components.AnimatedShimmer
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun TaskListColumn(
    modifier: Modifier = Modifier,
    selectedTabStatus: TaskStatus?,
    onTabChange: (TaskStatus) -> Unit = {},
    showingTasks: List<Task>?, // null means loading
    onCheckChanged: (Task) -> Unit,
    onRowMove: (Int, Int) -> Unit,
    onRowClick: (Task) -> Unit,
    isScrollToTopAtLaunch: Boolean = false,
    listPadding: Int = 20,
    content: @Composable () -> Unit = {},
) {
    val ignoreCount = if (selectedTabStatus != null) 2 else 1

    val scope = rememberCoroutineScope()
    var overScrollJob by remember { mutableStateOf<Job?>(null) }
    val dragDropListState = rememberDragDropListState(ignoreCount = ignoreCount, onMove = onRowMove)
    val haptic = LocalHapticFeedback.current
    val screenHeightDp = LocalConfiguration.current.screenHeightDp

    if (isScrollToTopAtLaunch) {
        LaunchedEffect(dragDropListState) {
            dragDropListState.lazyListState.scrollToItem(0)
        }
    }

    LazyColumn(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDrag = { change, offset ->
                        change.consume()
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
        selectedTabStatus?.let {
            item {
                TaskTab(selectedTabStatus, onTabChange)
            }
        }

        // Task List Content <- which is shown after loading
        showingTasks?.let {
            itemsIndexed(showingTasks) { index, task ->
                Column(
                    modifier = Modifier
                        .padding(horizontal = listPadding.dp)
                        .graphicsLayer {
                            val offsetOrNull = dragDropListState.elementDisplacement.takeIf {
                                index == (dragDropListState.currentIndexOfDraggedItem?.minus(
                                    ignoreCount
                                )
                                    ?: 0)
                            } // lazyColumn counts other than items, so minus 2 from index
                            translationY = offsetOrNull ?: 0f
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
                // Spacer height - if number of showing task is too small then set big spacer height to prevent forced scroll at tab change
                val spacerHeight = if (showingTasks.size >= 3) 100 else screenHeightDp / 2
                Spacer(modifier = Modifier.height(spacerHeight.dp))
            }
        } ?: run { // Alternative content during loading
            items(10) {
                AnimatedShimmer(
                    modifier = Modifier
                        .padding(vertical = 10.dp, horizontal = listPadding.dp)
                        .fillMaxWidth()
                        .height(80.dp)
                        .clip(RoundedCornerShape(20.dp))
                )
            }
        }
    }
}