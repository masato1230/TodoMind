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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jp_funda.todomind.R
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

    private val mindMapCreateViewModel by viewModels<MindMapCreateViewModel>()
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
        mindMapCreateViewModel.loadTaskData()

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
            val scaleText = (mindMapCreateViewModel.getScale() * 100).roundToInt().toString() + getString(R.string.percent)
            binding.textScale.text = scaleText
        }
        mindMapCreateViewModel.updateCount.observe(viewLifecycleOwner, updateCountObserver)

        // MapView
        binding.mapView.composeView.apply {
            setContent {
                // todo set up loading
                if (!mindMapCreateViewModel.isLoading.observeAsState(true).value) {
                    MindMapCreateContent()
                }
            }
        }

        // Set up Loading Observer
        val loadingObserver = Observer<Boolean> { isLoading ->
            if (!isLoading) binding.loading.visibility = View.GONE
        }
        mindMapCreateViewModel.isLoading.observe(this, loadingObserver)

        return binding.root
    }

    @Composable
    fun MindMapCreateContent() {
        val observedUpdateCount = mindMapCreateViewModel.updateCount.observeAsState()

        // update views when scale is changed
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

    override fun onPause() {
        super.onPause()
        mainViewModel.editingMindMap = mindMapCreateViewModel.mindMap
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}