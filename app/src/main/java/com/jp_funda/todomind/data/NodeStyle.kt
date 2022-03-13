package com.jp_funda.todomind.data

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.TextUnit

enum class NodeStyle {
    HEADLINE_1,
    HEADLINE_2,
    HEADLINE_3,
    HEADLINE_4,
    HEADLINE_5,
    HEADLINE_6,
}

fun NodeStyle.getSize(): Size {
    return when (this) {
        NodeStyle.HEADLINE_1 -> Size(250f, 250f)
        NodeStyle.HEADLINE_2 -> Size(220f, 220f)
        NodeStyle.HEADLINE_3 -> Size(190f, 190f)
        NodeStyle.HEADLINE_4 -> Size(160f, 160f)
        NodeStyle.HEADLINE_5 -> Size(130f, 130f)
        NodeStyle.HEADLINE_6 -> Size(100f, 100f)
    }
}

@Composable
fun NodeStyle.getTextSize(): TextUnit {
    return when (this) {
        NodeStyle.HEADLINE_1 -> MaterialTheme.typography.h4.fontSize
        NodeStyle.HEADLINE_2 -> MaterialTheme.typography.h4.fontSize * 0.9
        NodeStyle.HEADLINE_3 -> MaterialTheme.typography.h4.fontSize * 0.8
        NodeStyle.HEADLINE_4 -> MaterialTheme.typography.h4.fontSize * 0.7
        NodeStyle.HEADLINE_5 -> MaterialTheme.typography.h4.fontSize * 0.4
        NodeStyle.HEADLINE_6 -> MaterialTheme.typography.h4.fontSize * 0.3
    }
}