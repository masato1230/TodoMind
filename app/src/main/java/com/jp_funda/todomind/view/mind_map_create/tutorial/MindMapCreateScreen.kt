package com.jp_funda.todomind.view.mind_map_create.tutorial

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.NodeStyle
import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.view.MainViewModel
import com.jp_funda.todomind.view.components.BackNavigationIcon
import com.jp_funda.todomind.view.components.LoadingView
import com.jp_funda.todomind.view.mind_map_create.MapView
import com.jp_funda.todomind.view.mind_map_create.MindMapCreateViewModel
import com.jp_funda.todomind.view.mind_map_create.nodes.*

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
            MindMapCreateContent(navController, mainViewModel)
        }
    } else {
        LoadingView()
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun MindMapCreateContent(
    navController: NavController,
    mainViewModel: MainViewModel,
) {
    val context = LocalContext.current
    val mapView = MapView(context)

    AndroidView(
        factory = { mapView },
    )

    mapView.composeView.setContent {
        NodeGraph(
            modifier = Modifier.fillMaxSize(),
            onClickMindMapNode = {
                // Reset Selected Node
                mainViewModel.selectedNode = null
                // todo findNavController().navigate(R.id.navigation_mind_map_options_dialog)
            },
            onClickTaskNode = { task ->
                // Set selected Node
                mainViewModel.selectedNode = task
                // todo findNavController().navigate(R.id.navigation_mind_map_options_dialog)
            }
        )
    }
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun NodeGraph(
    modifier: Modifier = Modifier,
    onClickMindMapNode: (mindMap: MindMap) -> Unit,
    onClickTaskNode: (task: Task) -> Unit,
) {
    val mindMapCreateViewModel = hiltViewModel<MindMapCreateViewModel>()

    val observedUpdateCount = mindMapCreateViewModel.updateCount.observeAsState()

    // Update views when update count is changed
    observedUpdateCount.value?.let {
        Box(modifier = modifier) {

            MindMapNode(
                mindMap = mindMapCreateViewModel.mindMap,
                viewModel = mindMapCreateViewModel,
            ) { onClickMindMapNode(mindMapCreateViewModel.mindMap) }

            // draw all tasks in mindMap
            for (task in mindMapCreateViewModel.tasks) {
                when (task.styleEnum) {
                    NodeStyle.HEADLINE_1 -> H1(task = task, viewModel = mindMapCreateViewModel) { onClickTaskNode(task) }
                    NodeStyle.HEADLINE_2 -> H2(task = task, viewModel = mindMapCreateViewModel) { onClickTaskNode(task) }
                    NodeStyle.HEADLINE_3 -> H3(task = task, viewModel = mindMapCreateViewModel) { onClickTaskNode(task) }
                    NodeStyle.HEADLINE_4 -> H4(task = task, viewModel = mindMapCreateViewModel) { onClickTaskNode(task) }
                    NodeStyle.BODY_1 -> Body1(task = task, viewModel = mindMapCreateViewModel) { onClickTaskNode(task) }
                    NodeStyle.BODY_2 -> Body2(task = task, viewModel = mindMapCreateViewModel) { onClickTaskNode(task) }
                    NodeStyle.LINK -> Link(task = task, viewModel = mindMapCreateViewModel) { onClickTaskNode(task) }
                }
            }
        }
    }
}