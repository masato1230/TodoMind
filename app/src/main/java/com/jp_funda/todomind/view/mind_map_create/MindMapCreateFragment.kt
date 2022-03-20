package com.jp_funda.todomind.view.mind_map_create

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.NodeStyle
import com.jp_funda.todomind.data.getSize
import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.databinding.FragmentMindMapCreateBinding
import com.jp_funda.todomind.view.MainViewModel
import com.jp_funda.todomind.view.components.LineContent
import com.jp_funda.todomind.view.components.MindMapCreateContent
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.min
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
        // Reset cached data
        mindMapCreateViewModel.clearData()

        // Set MindMap data
        mindMapCreateViewModel.mindMap = mainViewModel.editingMindMap!!
        // Load task data and refresh view
        mindMapCreateViewModel.refreshView()

        _binding = FragmentMindMapCreateBinding.inflate(inflater)

        // Initialize header
        initializeHeader()

        // Scale buttons
        initializeZoomButtons()

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
                    MindMapCreateContent(
                        modifier = Modifier.fillMaxSize(),
                        mindMapCreateViewModel = mindMapCreateViewModel,
                        onClickMindMapNode = {
                            // Reset Selected Node
                            mainViewModel.selectedNode = null
                            findNavController().navigate(R.id.navigation_mind_map_options_dialog)
                        },
                        onClickTaskNode = { task ->
                            // Set selected Node
                            mainViewModel.selectedNode = task
                            findNavController().navigate(R.id.navigation_mind_map_options_dialog)
                        }
                    )
                }
            }
        }
        scrollToMindMapNode(mindMapCreateViewModel.mindMap)

        // LineView
        binding.mapView.lineComposeView.apply {
            setContent {
                if (!mindMapCreateViewModel.isLoading.observeAsState(true).value) {
                    LineContent(
                        mindMapCreateViewModel = mindMapCreateViewModel,
                        resources = resources,
                    )
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

    private fun initializeHeader() {
        binding.headerBack.setOnClickListener { findNavController().popBackStack() }
        binding.headerTitle.text = mindMapCreateViewModel.mindMap.title ?: ""
    }

    private fun initializeZoomButtons() {
        val screenWidth = resources.displayMetrics.widthPixels
        val screenHeight = resources.displayMetrics.heightPixels

        val minScale = min(
            screenWidth.toFloat() / binding.mapView.mapViewOriginalWidth.toFloat(),
            screenHeight.toFloat() / binding.mapView.mapViewOriginalHeight.toFloat()
        )

        binding.buttonZoomIn.setOnClickListener {
            mindMapCreateViewModel.setScale(mindMapCreateViewModel.getScale().plus(0.1f))
        }
        binding.buttonZoomOut.setOnClickListener {
            if (mindMapCreateViewModel.getScale() - 0.1 <= minScale) {
                mindMapCreateViewModel.setScale(minScale)
            } else {
                mindMapCreateViewModel.setScale(mindMapCreateViewModel.getScale().minus(0.1f))
            }
        }
    }

    private fun scrollToMindMapNode(mindMap: MindMap) {
        val screenWidth = resources.displayMetrics.widthPixels
        val scrollX = ((mindMap.x) ?: 0f) * mindMapCreateViewModel.getScale() -
                screenWidth / 2 + NodeStyle.HEADLINE_1.getSize().width * mindMapCreateViewModel.getScale()
        val screenHeight = resources.displayMetrics.heightPixels
        val scrollY = ((mindMap.y) ?: 0f) * mindMapCreateViewModel.getScale() -
                screenHeight / 2 + NodeStyle.HEADLINE_1.getSize().height * mindMapCreateViewModel.getScale()
        binding.mapView.horizontalScrollView.post {
            binding.mapView.horizontalScrollView.smoothScrollTo(scrollX.roundToInt(), 0)
        }
        binding.mapView.scrollView.post {
            binding.mapView.scrollView.smoothScrollTo(0, scrollY.roundToInt())
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