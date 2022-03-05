package com.jp_funda.todomind.extension

import androidx.compose.ui.graphics.Color

/** Calculate luminance in range of 0 to 1 */
fun Color.getLuminance(): Float {
    return (0.299 * red + 0.587 * green + 0.114 * blue).toFloat()
}