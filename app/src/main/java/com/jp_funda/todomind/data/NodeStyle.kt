package com.jp_funda.todomind.data

import androidx.compose.ui.geometry.Size

enum class NodeStyle {
    HEADLINE_1,
}

fun NodeStyle.getSize(): Size {
    return when (this) {
        NodeStyle.HEADLINE_1 -> Size(250f, 250f)
    }
}