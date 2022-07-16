package com.jp_funda.todomind.view.mind_map

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.R
import com.jp_funda.todomind.navigation.RouteGenerator
import com.jp_funda.todomind.view.MainViewModel
import com.jp_funda.todomind.view.components.BannerAd
import com.jp_funda.todomind.view.components.LoadingView
import com.jp_funda.todomind.view.components.RecentMindMapSection
import com.jp_funda.todomind.view.mind_map.components.MindMapsRow
import kotlinx.coroutines.delay

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MindMapScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
) {
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
        MindMapContent(navController, mainViewModel)
    }
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun MindMapContent(
    navController: NavController,
    mainViewModel: MainViewModel,
) {
    val mindMapViewModel = hiltViewModel<MindMapViewModel>()

    LaunchedEffect(Unit) {
        delay(1000) // todo delete
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
                    navController.navigate(
                        RouteGenerator.MindMapDetail(yetCompletedMindMaps.firstOrNull()?.id)()
                    )
                },
                onNewMindMapClick = {
                    navController.navigate(RouteGenerator.MindMapDetail(null)())
                }
            )

            // Mind Maps Section
            Text(
                text = "Mind Maps",
                modifier = Modifier.padding(15.dp),
                color = Color.White,
                style = MaterialTheme.typography.h6,
            )
            MindMapsRow(mindMaps = yetCompletedMindMaps) { mindMap ->
                navController.navigate(RouteGenerator.MindMapDetail(mindMap.id)())
            }

            // Completed Section
            if (completedMindMaps.isNotEmpty()) {
                Text(
                    text = "Closed",
                    modifier = Modifier.padding(15.dp),
                    color = Color.White,
                    style = MaterialTheme.typography.h6,
                )
                MindMapsRow(mindMaps = completedMindMaps) { mindMap ->
                    navController.navigate(RouteGenerator.MindMapDetail(mindMap.id)())
                }
            }
        }
    } ?: LoadingView()
}