package com.jp_funda.todomind.view.mind_map.nodes

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun H1(
    initialOffsetX: Float,
    initialOffsetY: Float,
    scale: Float = 1f,
    text: String,
) {
    val haptic = LocalHapticFeedback.current

    var offsetX by remember { mutableStateOf(initialOffsetX) }
    var offsetY by remember { mutableStateOf(initialOffsetY) }

    Box(
        modifier = Modifier
            .offset { IntOffset((offsetX * scale).roundToInt(), (offsetY * scale).roundToInt()) }
            .clip(CircleShape)
            .background(Color.White)
            .size(250.dp * scale)
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDragStart = { haptic.performHapticFeedback(HapticFeedbackType.LongPress) }
                ) { change, dragAmount ->
                    change.consumeAllChanges()
                    offsetX += dragAmount.x / scale
                    offsetY += dragAmount.y / scale
                }
            }
            .drawBehind {
                drawCircle(
                    color = Color.Black,
                    radius = ((size.minDimension - 20 * scale) / 2f),
                    style = Stroke(10f)
                )
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            modifier = Modifier
                .clip(CircleShape)
                .padding(30.dp * scale),
            maxLines = 4,
            fontSize = MaterialTheme.typography.h4.fontSize * scale,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun H1Preview() {
    H1(
        initialOffsetX = 100f,
        initialOffsetY = 100f,
        text = "Headline1",
    )
}