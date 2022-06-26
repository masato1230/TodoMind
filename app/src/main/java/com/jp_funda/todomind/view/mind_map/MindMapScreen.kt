package com.jp_funda.todomind.view.mind_map

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.view.MainViewModel
import com.jp_funda.todomind.view.components.BannerAd
import com.jp_funda.todomind.view.components.MindMapCard
import com.jp_funda.todomind.view.components.RecentMindMapSection

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MindMapScreen(mainViewModel: MainViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "MindMap") },
                backgroundColor = colorResource(id = R.color.deep_purple),
                contentColor = Color.White,
            )
        },
        backgroundColor = colorResource(id = R.color.deep_purple),
    ) {
        MindMapContent(mainViewModel)
    }
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun MindMapContent(mainViewModel: MainViewModel) {
    val mindMapViewModel = hiltViewModel<MindMapViewModel>()

    LaunchedEffect(Unit) {
        mindMapViewModel.refreshMindMapListData()
    }

    val observedMindMapList by mindMapViewModel.mindMapList.observeAsState()

    val scrollState = rememberScrollState()

    observedMindMapList?.let { mindMapList ->
        val yetCompletedMindMaps = mindMapList.filter { !it.isCompleted }
        val completedMindMaps = mindMapList.filter { it.isCompleted }

        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            // Banner Advertisement
            BannerAd(
                width = LocalConfiguration.current.screenWidthDp,
                modifier = Modifier.heightIn(min = 60.dp),
            )

            // Recent mind map section
            RecentMindMapSection(
                mindMap = yetCompletedMindMaps.firstOrNull(),
                onRecentMindMapClick = {
                    mainViewModel.editingMindMap = yetCompletedMindMaps.firstOrNull()
                    // todo findNavController().navigate(R.id.action_navigation_mind_map_to_navigation_mind_map_detail)
                },
                onNewMindMapClick = {
                    // todo findNavController().navigate(R.id.action_navigation_mind_map_to_navigation_mind_map_detail)
                }
            )

            // Mind Maps Section
            Text(
                text = "Mind Maps",
                modifier = Modifier.padding(15.dp),
                color = Color.White,
                style = MaterialTheme.typography.h6,
            )
            MindMapsRow(yetCompletedMindMaps, mainViewModel)

            // Completed Section
            if (completedMindMaps.isNotEmpty()) {
                Text(
                    text = "Closed",
                    modifier = Modifier.padding(15.dp),
                    color = Color.White,
                    style = MaterialTheme.typography.h6,
                )
                MindMapsRow(completedMindMaps, mainViewModel)
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun MindMapsRow(mindMaps: List<MindMap>, mainViewModel: MainViewModel) {
    LazyRow(modifier = Modifier.padding(bottom = 20.dp)) {
        items(items = mindMaps) { mindMap ->
            MindMapCard(
                mindMap = mindMap,
                onClick = {
                    mainViewModel.editingMindMap = mindMap
                    // todo findNavController().navigate(R.id.action_navigation_mind_map_to_navigation_mind_map_detail)
                })
        }
    }
}