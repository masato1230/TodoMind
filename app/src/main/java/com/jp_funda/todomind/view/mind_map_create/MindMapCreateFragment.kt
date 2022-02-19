package com.jp_funda.todomind.view.mind_map_create

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import com.jp_funda.todomind.databinding.FragmentMindMapCreateBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MindMapCreateFragment : Fragment() {

    private var _binding: FragmentMindMapCreateBinding? = null
    private val binding get() = _binding!!

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

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMindMapCreateBinding.inflate(inflater)

        binding.root.setOnTouchListener { v, event ->
            currentX = event.x
            currentY = event.y
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    scrollAnimator?.pause()
                    horizontalScrollAnimator?.pause()
                    binding.scrollView.performClick()
                    startX = event.x
                    startY = event.y
                    beforeX = event.x
                    beforeY = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    binding.horizontalScrollView.scrollBy((beforeX - currentX).toInt(), 0)
                    binding.scrollView.scrollBy(0, (beforeY - currentY).toInt())
                    twoBeforeX = beforeX
                    twoBeforeY = beforeY
                    beforeX = currentX
                    beforeY = currentY
                }
                MotionEvent.ACTION_UP -> {
                    scrollAnimator = ObjectAnimator.ofInt(
                        binding.scrollView,
                        "ScrollY",
                        binding.scrollView.scrollY,
                        binding.scrollView.scrollY - (currentY - twoBeforeY).toInt() * 40,
                    ).setDuration(700)
                    scrollAnimator?.let {
                        it.apply {
                            interpolator = DecelerateInterpolator(2f)
                            start()
                        }
                    }
                    horizontalScrollAnimator = ObjectAnimator.ofInt(
                        binding.horizontalScrollView,
                        "ScrollX",
                        binding.horizontalScrollView.scrollX,
                        binding.horizontalScrollView.scrollX - (currentX - twoBeforeX).toInt() * 40
                    ).setDuration(700)
                    horizontalScrollAnimator?.let {
                        it.apply {
                            interpolator = DecelerateInterpolator(2f)
                            start()
                        }
                    }
                }
            }
            return@setOnTouchListener true
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}