package com.jp_funda.todomind.view.mind_map_create

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.DecelerateInterpolator
import android.widget.RelativeLayout
import com.jp_funda.todomind.R
import com.jp_funda.todomind.view.custom_view.DiagonalHorizontalScrollView
import com.jp_funda.todomind.view.custom_view.DiagonalScrollView

class MapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RelativeLayout(context, attrs, defStyle) {
    private val scrollView: DiagonalScrollView
    private val horizontalScrollView: DiagonalHorizontalScrollView

    private var scrollAnimator: ObjectAnimator? = null
    private var horizontalScrollAnimator: ObjectAnimator? = null
    private var startX = 0f
    private var startY = 0f
    private var twoBeforeX = 0f
    private var twoBeforeY = 0f
    private var beforeX = 0f
    private var beforeY = 0f
    private var currentX = 0f
    private var currentY = 0f

    init {
        inflate(context, R.layout.view_map, this)
        scrollView = findViewById(R.id.map_view_scroll_view)
        horizontalScrollView = findViewById(R.id.map_view_horizontal_scroll_view)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        currentX = event.x
        currentY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                scrollAnimator?.pause()
                horizontalScrollAnimator?.pause()
                scrollView.performClick()
                startX = event.x
                startY = event.y
                beforeX = event.x
                beforeY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                horizontalScrollView.scrollBy((beforeX - currentX).toInt(), 0)
                scrollView.scrollBy(0, (beforeY - currentY).toInt())
                twoBeforeX = beforeX
                twoBeforeY = beforeY
                beforeX = currentX
                beforeY = currentY
            }
            MotionEvent.ACTION_UP -> {
                scrollAnimator = ObjectAnimator.ofInt(
                    scrollView,
                    "ScrollY",
                    scrollView.scrollY,
                    scrollView.scrollY - (currentY - twoBeforeY).toInt() * 40,
                ).setDuration(700)
                scrollAnimator?.let {
                    it.apply {
                        interpolator = DecelerateInterpolator(2f)
                        start()
                    }
                }
                horizontalScrollAnimator = ObjectAnimator.ofInt(
                    horizontalScrollView,
                    "ScrollX",
                    horizontalScrollView.scrollX,
                    horizontalScrollView.scrollX - (currentX - twoBeforeX).toInt() * 40
                ).setDuration(700)
                horizontalScrollAnimator?.let {
                    it.apply {
                        interpolator = DecelerateInterpolator(2f)
                        start()
                    }
                }
            }
        }
        return true
    }
}