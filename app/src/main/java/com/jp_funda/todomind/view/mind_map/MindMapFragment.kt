package com.jp_funda.todomind.view.mind_map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.view.MainViewModel
import com.jp_funda.todomind.view.components.BannerAd
import com.jp_funda.todomind.view.components.MindMapCard
import com.jp_funda.todomind.view.components.RecentMindMapSection
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class MindMapFragment : Fragment() {

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val mindMapViewModel by viewModels<MindMapViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Get All MindMap data and store it in viewModel
        mindMapViewModel.refreshMindMapListData()

        return ComposeView(requireContext()).apply {
            setContent {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "MindMap") },
                            backgroundColor = colorResource(id = R.color.deep_purple),
                            contentColor = Color.White,
                        )
                    },
                    backgroundColor = colorResource(id = R.color.deep_purple)
                ) {
                    MindMapContent()
                }
            }
        }
    }

    @Composable
    fun MindMapContent() {
        // Set up data
        val observedMindMapList by mindMapViewModel.mindMapList.observeAsState()

        val scrollState = rememberScrollState()

        observedMindMapList?.let { mindMapList ->
            val yetCompletedMindMaps = mindMapList.filter { !it.isCompleted }
            val completedMindMaps = mindMapList.filter { it.isCompleted }

            Column(
                modifier = Modifier.verticalScroll(scrollState)
            ) {
                // Banner Advertisement
                val width =
                    (resources.displayMetrics.widthPixels / resources.displayMetrics.density).roundToInt()
                BannerAd(width = width, modifier = Modifier.heightIn(min = 60.dp))

                // Recent mind map section
                RecentMindMapSection(
                    mindMap = yetCompletedMindMaps.firstOrNull(),
                    onRecentMindMapClick = {
                        mainViewModel.editingMindMap = yetCompletedMindMaps.firstOrNull()
                        findNavController().navigate(R.id.action_navigation_mind_map_to_navigation_mind_map_detail)
                    },
                    onNewMindMapClick = {
                        findNavController().navigate(R.id.action_navigation_mind_map_to_navigation_mind_map_detail)
                    }
                )

                // Mind Maps Section
                Text(
                    text = "Mind Maps",
                    modifier = Modifier.padding(15.dp),
                    color = Color.White,
                    style = MaterialTheme.typography.h6,
                )
                MindMapsRow(yetCompletedMindMaps)

                // Completed Section
                if (completedMindMaps.isNotEmpty()) {
                    Text(
                        text = "Closed",
                        modifier = Modifier.padding(15.dp),
                        color = Color.White,
                        style = MaterialTheme.typography.h6,
                    )
                    MindMapsRow(completedMindMaps)
                }
            }
        }
    }

    // MindMapFragment's components

    @Composable
    fun MindMapsRow(mindMaps: List<MindMap>) {
        LazyRow(modifier = Modifier.padding(bottom = 20.dp)) {
            items(items = mindMaps) { mindMap ->
                MindMapCard(
                    mindMap = mindMap,
                    onClick = {
                        mainViewModel.editingMindMap = mindMap
                        findNavController().navigate(R.id.action_navigation_mind_map_to_navigation_mind_map_detail)
                    })
            }
        }
    }
}