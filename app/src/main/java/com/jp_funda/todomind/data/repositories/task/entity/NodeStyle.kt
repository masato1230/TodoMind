package com.jp_funda.todomind.data.repositories.task.entity

import android.content.res.Resources
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.TextUnit

enum class NodeStyle(val title: String) {
    HEADLINE_1(title = "Headline 1"),
    HEADLINE_2(title = "Headline 2"),
    HEADLINE_3(title = "Headline 3"),
    HEADLINE_4(title = "Headline 4"),
    BODY_1(title = "Body 1"),
    BODY_2(title = "Body 2"),
    LINK(title = "Link"),
}

fun NodeStyle.getSize(): Size {
    return when (this) {
        NodeStyle.HEADLINE_1 -> Size(300f, 300f)
        NodeStyle.HEADLINE_2 -> Size(220f, 220f)
        NodeStyle.HEADLINE_3 -> Size(170f, 170f)
        NodeStyle.HEADLINE_4 -> Size(150f, 150f)
        NodeStyle.BODY_1 -> Size(500f, 30f)
        NodeStyle.BODY_2 -> Size(500f, 25f)
        NodeStyle.LINK -> Size(500f, 25f)
    }
}

fun NodeStyle.getSizeOffsetForDrawLine(resources: Resources): Size {
    val density = resources.displayMetrics.density
    return when (this) {
        NodeStyle.BODY_1,
        NodeStyle.BODY_2,
        NodeStyle.LINK -> Size(this.getSize().height, this.getSize().height) / 2f * density
        else -> this.getSize() / 2f * density
    }
}

@Composable
fun NodeStyle.getTextSize(): TextUnit {
    return when (this) {
        NodeStyle.HEADLINE_1 -> MaterialTheme.typography.h4.fontSize
        NodeStyle.HEADLINE_2 -> MaterialTheme.typography.h4.fontSize * 0.9
        NodeStyle.HEADLINE_3 -> MaterialTheme.typography.h4.fontSize * 0.6
        NodeStyle.HEADLINE_4 -> MaterialTheme.typography.h4.fontSize * 0.4
        NodeStyle.BODY_1 -> MaterialTheme.typography.h5.fontSize
        NodeStyle.BODY_2 -> MaterialTheme.typography.h6.fontSize
        NodeStyle.LINK -> MaterialTheme.typography.h5.fontSize
    }
}