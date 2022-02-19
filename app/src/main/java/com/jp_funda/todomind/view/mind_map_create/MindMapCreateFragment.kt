package com.jp_funda.todomind.view.mind_map_create

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jp_funda.todomind.databinding.FragmentMindMapCreateBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MindMapCreateFragment : Fragment() {

    private var _binding: FragmentMindMapCreateBinding? = null
    private val binding get() = _binding!!

    private var moveX = 0f
    private var moveY = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMindMapCreateBinding.inflate(inflater)

        binding.root.setOnTouchListener { v, event ->
            val currentX: Float
            val currentY: Float
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    moveX = event.x
                    moveY = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    currentX = event.x
                    currentY = event.y
                    binding.horizontalScrollView.scrollBy((moveX - currentX).toInt(), 0)
                    binding.scrollView.scrollBy(0, (moveY - currentY).toInt())
                    moveX = currentX
                    moveY = currentY
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