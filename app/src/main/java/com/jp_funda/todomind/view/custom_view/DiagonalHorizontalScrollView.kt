package com.jp_funda.todomind.view.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.HorizontalScrollView

class DiagonalHorizontalScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : HorizontalScrollView(context, attrs, defStyle) {
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }
}