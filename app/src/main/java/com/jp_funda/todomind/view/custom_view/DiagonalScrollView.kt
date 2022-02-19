package com.jp_funda.todomind.view.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView

class DiagonalScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ScrollView(context, attrs, defStyle) {
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }
}