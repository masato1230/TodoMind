package com.jp_funda.todomind.view.mind_map_create.tutorial

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.R
import com.jp_funda.todomind.view.MainViewModel
import com.jp_funda.todomind.view.components.BackNavigationIcon
import com.jp_funda.todomind.view.components.LoadingView
import com.jp_funda.todomind.view.mind_map_create.MapView
import com.jp_funda.todomind.view.mind_map_create.MindMapCreateViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun MindMapCreateScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
) {
    val mindMapCreateViewModel = hiltViewModel<MindMapCreateViewModel>()

    LaunchedEffect(Unit) {
        // Set mind map data
        mindMapCreateViewModel.mindMap = mainViewModel.editingMindMap!!
        // Load task data and refresh view
        mindMapCreateViewModel.refreshView()
    }
    // TODO Zoom buttons
    // TODO Update Count observer

    val isLoading = mindMapCreateViewModel.isLoading.observeAsState()

    if (isLoading.value == false) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = mindMapCreateViewModel.mindMap.title ?: "") },
                    backgroundColor = colorResource(id = R.color.deep_purple),
                    contentColor = Color.White,
                    navigationIcon = { BackNavigationIcon(navController) },
                )
            },
            backgroundColor = colorResource(id = R.color.deep_purple),
        ) {
            MindMapCreateContent()
        }
    } else {
        LoadingView()
    }
}

@Composable
fun MindMapCreateContent() {
    val context = LocalContext.current
    val mavView = MapView(context)

    AndroidView(
        factory = { MapView(context) },
    )
}