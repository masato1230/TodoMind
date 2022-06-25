package com.jp_funda.todomind.view.mind_map_create.nodes

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
import com.jp_funda.todomind.extension.getLuminance
import com.jp_funda.todomind.view.mind_map_create.MindMapCreateViewModel
import kotlin.math.roundToInt

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun HeadlineNodeBase(
    modifier: Modifier = Modifier,
    task: Task,
    viewModel: MindMapCreateViewModel,
    circleSize: Dp,
    // text parameters
    fontSize: TextUnit,
    textPadding: Dp,
    maxLines: Int,
    behindCircleColor: Color? = null,
    behindCircleWidth: Float = 5f,
    behindCircleRadiusOffset: Float = 15f,
    onClick: () -> Unit,
) {
    val haptic = LocalHapticFeedback.current

    var offsetX by remember { mutableStateOf(task.x ?: 0f) }
    var offsetY by remember { mutableStateOf(task.y ?: 0f) }
    // Make task state to identify task in callbacks update
    var rememberedTask by remember { mutableStateOf(task) }

    // Update states before ui update
    offsetX = task.x ?: 0f
    offsetY = task.y ?: 0f
    rememberedTask = task

    val scale = viewModel.getScale()

    val backgroundColor = if (rememberedTask.statusEnum != TaskStatus.Complete) {
        rememberedTask.color?.let { Color(it) } ?: run { colorResource(id = R.color.teal_200) }
    } else {
        Color.DarkGray
    }
    val fontColor = if (rememberedTask.statusEnum != TaskStatus.Complete) {
        if (backgroundColor.getLuminance() > 0.6) Color.Black else Color.White
    } else Color.LightGray

    Box(
        modifier = modifier
            .offset { IntOffset((offsetX * scale).roundToInt(), (offsetY * scale).roundToInt()) }
            .clip(CircleShape)
            .background(backgroundColor)
            .size(circleSize * scale)
            .drawBehind {
                behindCircleColor?.let {
                    drawCircle(
                        color = it,
                        radius = ((size.minDimension - behindCircleRadiusOffset * density * viewModel.getScale()) / 2f),
                        style = Stroke(behindCircleWidth * density * scale)
                    )
                }
            }
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDragStart = { haptic.performHapticFeedback(HapticFeedbackType.LongPress) },
                    onDragEnd = {
                        rememberedTask.x = offsetX
                        rememberedTask.y = offsetY
                        viewModel.updateTask(rememberedTask)
                        viewModel.refreshView()
                    }
                ) { change, dragAmount ->
                    change.consumeAllChanges()
                    offsetX += dragAmount.x / (viewModel.getScale()) // Note: Referencing viewModel directory is needed
                    offsetY += dragAmount.y / (viewModel.getScale())
                }
            }
            .clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = rememberedTask.title ?: "",
            modifier = Modifier
                .clip(CircleShape)
                .padding(textPadding * scale),
            maxLines = maxLines,
            fontSize = fontSize * scale,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            color = fontColor,
        )
    }
}
