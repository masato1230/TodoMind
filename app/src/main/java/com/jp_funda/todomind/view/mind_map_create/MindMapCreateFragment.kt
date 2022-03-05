package com.jp_funda.todomind.view.mind_map_create

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.NodeStyle
import com.jp_funda.todomind.data.getSize
import com.jp_funda.todomind.databinding.FragmentMindMapCreateBinding
import com.jp_funda.todomind.view.MainViewModel
import com.jp_funda.todomind.view.mind_map_create.nodes.H1
import com.jp_funda.todomind.view.mind_map_create.nodes.MindMapNode
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt


@AndroidEntryPoint
class MindMapCreateFragment : Fragment() {

    private var _binding: FragmentMindMapCreateBinding? = null
    private val binding get() = _binding!!

    private val mindMapCreateViewModel by activityViewModels<MindMapCreateViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Set MindMap data
        mindMapCreateViewModel.mindMap = mainViewModel.editingMindMap!!
        // Load task data
        mindMapCreateViewModel.refreshView()

        _binding = FragmentMindMapCreateBinding.inflate(inflater)

        // Scale buttons
        binding.buttonZoomIn.setOnClickListener {
            mindMapCreateViewModel.setScale(mindMapCreateViewModel.getScale().plus(0.1f))
        }
        binding.buttonZoomOut.setOnClickListener {
            if (mindMapCreateViewModel.getScale() <= 0.1) return@setOnClickListener
            mindMapCreateViewModel.setScale(mindMapCreateViewModel.getScale().minus(0.1f))
        }

        // UpdateCount Observer
        val updateCountObserver = Observer<Int> {
            binding.mapView.onScaleChange(mindMapCreateViewModel.getScale())
            val scaleText = (mindMapCreateViewModel.getScale() * 100).roundToInt()
                .toString() + getString(R.string.percent)
            binding.textScale.text = scaleText
        }
        mindMapCreateViewModel.updateCount.observe(viewLifecycleOwner, updateCountObserver)

        // MapView
        binding.mapView.composeView.apply {
            setContent {
                if (!mindMapCreateViewModel.isLoading.observeAsState(true).value) {
                    MindMapCreateContent()
                }
            }
        }

        // LineView
        binding.mapView.lineComposeView.apply {
            setContent {
                if (!mindMapCreateViewModel.isLoading.observeAsState(true).value) {
                    LineContent()
                }
            }
        }

        // Set up Loading Observer
        val loadingObserver = Observer<Boolean> { isLoading ->
            if (!isLoading) binding.loading.visibility = View.GONE
        }
        mindMapCreateViewModel.isLoading.observe(viewLifecycleOwner, loadingObserver)

        return binding.root
    }

    @Composable
    fun MindMapCreateContent() {
        val observedUpdateCount = mindMapCreateViewModel.updateCount.observeAsState()

        // update views when update count is changed
        observedUpdateCount.value?.let { _ ->
            Box(modifier = Modifier.fillMaxSize()) {

                MindMapNode(
                    mindMap = mindMapCreateViewModel.mindMap,
                    viewModel = mindMapCreateViewModel,
                ) {
                    // Reset Selected Node
                    mainViewModel.selectedNode = null
                    findNavController().navigate(R.id.navigation_mind_map_options_dialog)
                }

                // draw all tasks in mindMap
                for (task in mindMapCreateViewModel.tasks) {
                    H1(task = task, viewModel = mindMapCreateViewModel) {
                        // Set selected Node
                        mainViewModel.selectedNode = task
                        findNavController().navigate(R.id.navigation_mind_map_options_dialog)
                    }
                }
            }
        }
    }

    @Composable
    fun LineContent() {
        val observedUpdateCount = mindMapCreateViewModel.updateCount.observeAsState()

        // Update when updateCount is countUp
        observedUpdateCount.value?.let { _ ->
            Box(modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    for (task in mindMapCreateViewModel.tasks) {
                        val startSizeOffsetX = (task.parentTask?.styleEnum
                            ?: NodeStyle.HEADLINE_1).getSize().width / 2 * resources.displayMetrics.density
                        val startSizeOffsetY = (task.parentTask?.styleEnum
                            ?: NodeStyle.HEADLINE_1).getSize().height / 2 * resources.displayMetrics.density
                        val endSizeOffsetX =
                            task.styleEnum.getSize().width / 2 * resources.displayMetrics.density
                        val endSizeOffsetY =
                            task.styleEnum.getSize().height / 2 * resources.displayMetrics.density

                        val startOffsetX = task.parentTask?.x ?: mainViewModel.editingMindMap?.x
                        val startOffsetY = task.parentTask?.y ?: mainViewModel.editingMindMap?.y
                        val endOffsetX = task.x
                        val endOffsetY = task.y

                        Log.d(
                            "Positions",
                            "$startOffsetX, $startOffsetY, $endOffsetX, $endOffsetY"
                        )
                        Log.d(
                            "dpi",
                            "${resources.displayMetrics.density}, ${resources.displayMetrics.ydpi}"
                        )

                        if (
                            startOffsetX == null ||
                            startOffsetY == null ||
                            endOffsetX == null ||
                            endOffsetY == null
                        ) return@drawBehind

                        drawLine(
                            color = Color.White,
                            start = Offset(
                                startOffsetX + startSizeOffsetX,
                                startOffsetY + startSizeOffsetY
                            ) * mindMapCreateViewModel.getScale(),
                            end = Offset(
                                endOffsetX + endSizeOffsetX,
                                endOffsetY + endSizeOffsetY
                            ) * mindMapCreateViewModel.getScale(),
                            strokeWidth = Stroke.DefaultMiter
                        )
                    }
                })
        }
    }

    override fun onPause() {
        super.onPause()
        mainViewModel.editingMindMap = mindMapCreateViewModel.mindMap
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}