package com.jp_funda.todomind.view.mind_map_create

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.jp_funda.todomind.databinding.FragmentMindMapCreateBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt


@AndroidEntryPoint
class MindMapCreateFragment : Fragment() {

    private var _binding: FragmentMindMapCreateBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMindMapCreateBinding.inflate(inflater)

        binding.root.composeView.apply {
            setContent {
                    MindMapCreateContent()
//                Scaffold(backgroundColor = colorResource(id = R.color.deep_purple)) {
//                }
            }
        }
        return binding.root
    }

    @Composable
    fun MindMapCreateContent() {
        val haptic = LocalHapticFeedback.current

        Text(text = "kdfjalkfjas", color = Color.White)
        Box(modifier = Modifier.fillMaxSize()) {
            var offsetX by remember { mutableStateOf(0f) }
            var offsetY by remember { mutableStateOf(0f) }

            Box(
                Modifier
                    .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                    .background(Color.Blue)
                    .size(50.dp)
                    .pointerInput(Unit) {
                        detectDragGesturesAfterLongPress(
                            onDragStart = { haptic.performHapticFeedback(HapticFeedbackType.LongPress) }
                        ) { change, dragAmount ->
                            change.consumeAllChanges()
                            offsetX += dragAmount.x
                            offsetY += dragAmount.y
                        }
                    }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}