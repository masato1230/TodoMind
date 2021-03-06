package com.jp_funda.todomind.view.mind_map_create

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.compose.ui.platform.ComposeView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.jp_funda.todomind.R
import com.jp_funda.todomind.view.custom_view.DiagonalHorizontalScrollView
import com.jp_funda.todomind.view.custom_view.DiagonalScrollView
import kotlin.math.min
import kotlin.math.roundToInt

class MapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RelativeLayout(context, attrs, defStyle) {
    private val mapViewOriginalHeight = resources.getDimensionPixelSize(R.dimen.map_view_height)
    private val mapViewOriginalWidth = resources.getDimensionPixelSize(R.dimen.map_view_width)
    private val screenHeight = resources.displayMetrics.heightPixels
    private val screenWidth = resources.displayMetrics.widthPixels

    /** Child Views */
    val scrollView: DiagonalScrollView
    val horizontalScrollView: DiagonalHorizontalScrollView
    private val contentWrapper: LinearLayout
    private val content: ConstraintLayout
    val composeView: ComposeView
    val lineComposeView: ComposeView
    private val verticalIndicator: View
    private val horizontalIndicator: View

    /** Properties for diagonalScroll */
    private var scrollAnimator: ObjectAnimator? = null
    private var horizontalScrollAnimator: ObjectAnimator? = null
    private var twoBeforeX: Float? = null
    private var twoBeforeY: Float? = null
    private var beforeX: Float? = null
    private var beforeY: Float? = null

    /** Properties for scale */
    private var scale: Float = 1f

    val minScale = min(
        screenWidth.toFloat() / mapViewOriginalWidth.toFloat(),
        screenHeight.toFloat() / mapViewOriginalHeight.toFloat()
    )

    init {
        inflate(context, R.layout.view_map, this)
        scrollView = findViewById(R.id.map_view_scroll_view)
        horizontalScrollView = findViewById(R.id.map_view_horizontal_scroll_view)
        composeView = findViewById(R.id.map_view_compose_view)
        lineComposeView = findViewById(R.id.map_view_line_compose_view)
        contentWrapper = findViewById(R.id.map_view_content_wrapper)
        content = findViewById(R.id.map_view_content)
        verticalIndicator = findViewById(R.id.map_view_vertical_indicator)
        horizontalIndicator = findViewById(R.id.map_view_horizontal_indicator)

        // Set up indicators
        scrollView.setOnScrollChangeListener { _, _, y, _, _ ->
            val scrollPercentage = y / composeView.height.toFloat()
            verticalIndicator.y = scrollPercentage * scrollView.height
        }
        horizontalScrollView.setOnScrollChangeListener { _, x, _, _, _ ->
            val scrollPercentage = x / composeView.width.toFloat()
            horizontalIndicator.x = scrollPercentage * horizontalScrollView.width
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                scrollAnimator?.pause()
                horizontalScrollAnimator?.pause()
                scrollView.performClick()
                beforeX = event.x
                beforeY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                beforeX?.let { horizontalScrollView.scrollBy((it - event.x).toInt(), 0) }
                beforeY?.let { scrollView.scrollBy(0, (it - event.y).toInt()) }
                twoBeforeX = beforeX
                twoBeforeY = beforeY
                beforeX = event.x
                beforeY = event.y
            }
            MotionEvent.ACTION_UP -> {
                if (twoBeforeX == null || twoBeforeY == null) return true
                scrollAnimator = ObjectAnimator.ofInt(
                    scrollView,
                    "ScrollY",
                    scrollView.scrollY,
                    scrollView.scrollY - (event.y - twoBeforeY!!).toInt() * 40,
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
                    horizontalScrollView.scrollX - (event.x - twoBeforeX!!).toInt() * 40
                ).setDuration(700)
                horizontalScrollAnimator?.let {
                    it.apply {
                        interpolator = DecelerateInterpolator(2f)
                        start()
                    }
                }

                twoBeforeX = null
                twoBeforeY = null
                beforeX = null
                beforeY = null
            }
        }
        return true
    }

    /**
     * Adjust scrollPosition and indicator size corresponding with scale change
     */
    fun onScaleChange(newScale: Float) {
        Log.d("onScaleChange", newScale.toString())
        // Auto scrolling
        horizontalScrollView.scrollTo(
            ((horizontalScrollView.scrollX.toFloat() + screenWidth.toFloat() / 2) / scale * newScale - screenWidth.toFloat() / 2).roundToInt(),
            0
        )
        scrollView.scrollTo(
            0,
            ((scrollView.scrollY.toFloat() + screenHeight.toFloat() / 2) / scale * newScale - screenHeight.toFloat() / 2).roundToInt()
        )
        // Adjust views width & height
        val newMapViewHeight = (mapViewOriginalHeight * newScale).roundToInt()
        val newMapViewWidth = (mapViewOriginalWidth * newScale).roundToInt()
        contentWrapper.updateLayoutParams { height = newMapViewHeight }
        content.updateLayoutParams {
            width = newMapViewWidth
            height = newMapViewHeight
        }
        // indicators
        val newVerticalIndicatorHeight = screenHeight * (screenHeight.toFloat() / newMapViewHeight)
        val newHorizontalIndicatorWidth = screenWidth * (screenWidth.toFloat() / newMapViewWidth)
        verticalIndicator.updateLayoutParams { height = newVerticalIndicatorHeight.roundToInt() }
        horizontalIndicator.updateLayoutParams { width = newHorizontalIndicatorWidth.roundToInt() }

        scale = newScale
    }
}