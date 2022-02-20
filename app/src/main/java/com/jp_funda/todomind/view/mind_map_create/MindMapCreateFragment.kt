package com.jp_funda.todomind.view.mind_map_create

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import com.jp_funda.todomind.databinding.FragmentMindMapCreateBinding
import com.jp_funda.todomind.view.mind_map.nodes.H1
import dagger.hilt.android.AndroidEntryPoint


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

        binding.mapView.composeView.apply {
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
        Box(modifier = Modifier.fillMaxSize()) {
            var offsetX by remember { mutableStateOf(0f) }
            var offsetY by remember { mutableStateOf(0f) }

            H1(initialOffsetX = 100f, initialOffsetY = 100f, text = "Headline1 Headline1 Headline1 Headline1 Headline1 Headline1", scale = 0.1f)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}