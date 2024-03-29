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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.R
import com.jp_funda.repositories.mind_map.entity.MindMap
import com.jp_funda.repositories.task.entity.NodeStyle
import com.jp_funda.todomind.extension.getLuminance
import com.jp_funda.todomind.extension.getSize
import com.jp_funda.todomind.extension.getTextSize
import com.jp_funda.todomind.view.mind_map_create.MindMapCreateViewModel
import kotlin.math.roundToInt

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun MindMapNode(
    mindMap: MindMap,
    viewModel: MindMapCreateViewModel,
    onClick: () -> Unit,
) {
    val haptic = LocalHapticFeedback.current

    var offsetX by remember { mutableStateOf(mindMap.x ?: 0f) }
    var offsetY by remember { mutableStateOf(mindMap.y ?: 0f) }
    offsetX = mindMap.x ?: 0f
    offsetY = mindMap.y ?: 0f

    val scale = viewModel.getScale()

    val backgroundColor = mindMap.color?.let { Color(it) } ?: run { colorResource(id = R.color.pink_dark) }
    val fontColor = if (backgroundColor.getLuminance() > 0.6) Color.Black else Color.White

    Box(
        modifier = Modifier
            .offset { IntOffset((offsetX * scale).roundToInt(), (offsetY * scale).roundToInt()) }
            .clip(CircleShape)
            .background(backgroundColor)
            .size(NodeStyle.HEADLINE_1.getSize().width.dp * scale)
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDragStart = { haptic.performHapticFeedback(HapticFeedbackType.LongPress) },
                    onDragEnd = {
                        mindMap.x = offsetX
                        mindMap.y = offsetY
                        viewModel.updateMindMap(mindMap)
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
            text = mindMap.title ?: "",
            modifier = Modifier
                .clip(CircleShape)
                .padding(30.dp * scale),
            maxLines = 4,
            fontSize = NodeStyle.HEADLINE_1.getTextSize() * scale,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            color = fontColor,
        )
    }
}