package com.jp_funda.todomind.view.mind_map_create.nodes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
import com.jp_funda.todomind.extension.getLuminance
import com.jp_funda.todomind.view.mind_map_create.MindMapCreateViewModel
import kotlin.math.roundToInt

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
    behindCircleWidth: Float = 10f,
    onClick: () -> Unit,
) {
    val haptic = LocalHapticFeedback.current

    var offsetX by remember { mutableStateOf(task.x ?: 0f) }
    var offsetY by remember { mutableStateOf(task.y ?: 0f) }

    val scale = viewModel.getScale()

    val backgroundColor = if (task.statusEnum != TaskStatus.Complete) {
        task.color?.let { Color(it) } ?: run { colorResource(id = R.color.teal_200) }
    } else {
        Color.DarkGray
    }
    val fontColor = if (backgroundColor.getLuminance() > 0.6) Color.Black else Color.White

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
                        radius = ((size.minDimension - 20 * viewModel.getScale()) / 2f),
                        style = Stroke(behindCircleWidth)
                    )
                }
            }
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDragStart = { haptic.performHapticFeedback(HapticFeedbackType.LongPress) },
                    onDragEnd = {
                        task.x = offsetX
                        task.y = offsetY
                        viewModel.updateTask(task)
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
            text = task.title ?: "",
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
