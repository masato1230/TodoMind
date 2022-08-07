package com.jp_funda.todomind.view.mind_map_create.nodes

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.R
import com.jp_funda.repositories.task.entity.Task
import com.jp_funda.repositories.task.entity.TaskStatus
import com.jp_funda.todomind.view.mind_map_create.MindMapCreateViewModel
import kotlin.math.roundToInt

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun BodyNodeBase(
    modifier: Modifier = Modifier,
    task: Task,
    viewModel: MindMapCreateViewModel,
    iconImage: Painter,
    size: Size,
    // text parameters
    fontSize: TextUnit,
    maxLines: Int,
    iconPadding: Float = 0f,
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

    val circleColor = if (rememberedTask.statusEnum != TaskStatus.Complete) {
        rememberedTask.color?.let { Color(it) } ?: run { colorResource(id = R.color.teal_200) }
    } else {
        Color.DarkGray
    }
    val fontColor = if (rememberedTask.statusEnum != TaskStatus.Complete) {
        Color.White
    } else Color.Gray

    Row(
        modifier = modifier
            .offset { IntOffset((offsetX * scale).roundToInt(), (offsetY * scale).roundToInt()) }
            .clip(RoundedCornerShape(1000.dp))
            .background(colorResource(id = R.color.deep_purple))
            .wrapContentHeight()
            .wrapContentWidth()
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
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = iconImage,
            contentDescription = "Circle",
            tint = circleColor,
            modifier = Modifier
                .height(((size.height - iconPadding * 2) * scale).dp)
                .width(((size.height - iconPadding * 2) * scale).dp)
        )
        Spacer(modifier = Modifier.width(10.dp * scale))
        Text(
            text = rememberedTask.title ?: "",
            maxLines = maxLines,
            fontSize = fontSize * scale,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            color = fontColor,
            modifier = Modifier.widthIn(max = ((size.width - size.height - 30) * scale).dp)
        )
        Spacer(modifier = Modifier.width(20.dp * scale))
    }
}