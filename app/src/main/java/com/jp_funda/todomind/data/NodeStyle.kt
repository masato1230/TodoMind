package com.jp_funda.todomind.data

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.TextUnit

enum class NodeStyle {
    HEADLINE_1,
    HEADLINE_2,
    HEADLINE_3,
    BODY_1,
    BODY_2,
}

fun NodeStyle.getSize(): Size {
    return when (this) {
        NodeStyle.HEADLINE_1 -> Size(250f, 250f)
        NodeStyle.HEADLINE_2 -> Size(220f, 220f)
        NodeStyle.HEADLINE_3 -> Size(170f, 170f)
        NodeStyle.BODY_1 -> Size(800f, 30f)
        NodeStyle.BODY_2 -> Size(800f, 25f)
    }
}

@Composable
fun NodeStyle.getTextSize(): TextUnit {
    return when (this) {
        NodeStyle.HEADLINE_1 -> MaterialTheme.typography.h4.fontSize
        NodeStyle.HEADLINE_2 -> MaterialTheme.typography.h4.fontSize * 0.9
        NodeStyle.HEADLINE_3 -> MaterialTheme.typography.h4.fontSize * 0.6
        NodeStyle.BODY_1 -> MaterialTheme.typography.h5.fontSize
        NodeStyle.BODY_2 -> MaterialTheme.typography.h6.fontSize
    }
}