package com.jp_funda.todomind.view.mind_map_create

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.jp_funda.todomind.R
import com.jp_funda.todomind.databinding.FragmentMindMapCreateBinding
import com.jp_funda.todomind.view.mind_map.nodes.H1
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt


@AndroidEntryPoint
class MindMapCreateFragment : Fragment() {

    private var _binding: FragmentMindMapCreateBinding? = null
    private val binding get() = _binding!!

    private val mindMapCreateViewModel by viewModels<MindMapCreateViewModel>()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMindMapCreateBinding.inflate(inflater)

        // Scale buttons
        binding.buttonZoomIn.setOnClickListener {
            mindMapCreateViewModel.setScale(mindMapCreateViewModel.scale.value?.plus(0.1f) ?: 1f)
        }
        binding.buttonZoomOut.setOnClickListener {
            if (mindMapCreateViewModel.scale.value ?: 0f <= 0.1) return@setOnClickListener
            mindMapCreateViewModel.setScale(mindMapCreateViewModel.scale.value?.minus(0.1f) ?: 1f)
        }

        // Scale changeListener
        val scaleObserver = Observer<Float> { scale ->
            binding.mapView.onScaleChange(scale)
            val scaleText = (scale * 100).roundToInt().toString() + getString(R.string.percent)
            binding.textScale.text = scaleText
        }
        mindMapCreateViewModel.scale.observe(this, scaleObserver)

        // MapView
        binding.mapView.composeView.apply {
            setContent {
                MindMapCreateContent()
            }
        }
        return binding.root
    }

    @Composable
    fun MindMapCreateContent() {
        val observedScale = mindMapCreateViewModel.scale.observeAsState()

        observedScale.value?.let { scale ->
            Box(modifier = Modifier.fillMaxSize()) {
//                var offsetX by remember { mutableStateOf(0f) }
//                var offsetY by remember { mutableStateOf(0f) }

                H1(
                    initialOffsetX = 100f,
                    initialOffsetY = 100f,
                    text = "Headline1 Headline1 Headline1 Headline1 Headline1 Headline1",
                    viewModel = mindMapCreateViewModel,
                )
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}