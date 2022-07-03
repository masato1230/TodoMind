package com.jp_funda.todomind.view.mind_map_create.tutorial

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.NodeStyle
import com.jp_funda.todomind.data.getSize
import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.view.MainViewModel
import com.jp_funda.todomind.view.components.BackNavigationIcon
import com.jp_funda.todomind.view.components.LineContent
import com.jp_funda.todomind.view.components.LoadingView
import com.jp_funda.todomind.view.mind_map_create.Location
import com.jp_funda.todomind.view.mind_map_create.MapView
import com.jp_funda.todomind.view.mind_map_create.MindMapCreateViewModel
import com.jp_funda.todomind.view.mind_map_create.nodes.*
import kotlin.math.min
import kotlin.math.roundToInt

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun MindMapCreateScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    initialLocation: Location? = null,
) {
    val mindMapCreateViewModel = hiltViewModel<MindMapCreateViewModel>()

    LaunchedEffect(Unit) {
        // Set mind map data
        mindMapCreateViewModel.mindMap = mainViewModel.editingMindMap!!
        // Load task data and refresh view
        mindMapCreateViewModel.refreshView()
    }
    // TODO Show tutorial dialog at first time

    val isLoading = mindMapCreateViewModel.isLoading.observeAsState()

    if (isLoading.value == false) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = mindMapCreateViewModel.mindMap.title ?: "",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    },
                    backgroundColor = colorResource(id = R.color.deep_purple),
                    contentColor = Color.White,
                    navigationIcon = { BackNavigationIcon(navController) },
                )
            },
            backgroundColor = colorResource(id = R.color.deep_purple),
        ) {
            MindMapCreateContent(navController, mainViewModel, initialLocation)
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
    initialLocation: Location?,
) {
    val context = LocalContext.current
    val mapView = MapView(context)
    val mindMapCreateViewModel = hiltViewModel<MindMapCreateViewModel>()

    // Initial Scroll
    LaunchedEffect(Unit) {
        if (initialLocation == null) {
            scrollToMindMapNode(mapView, mindMapCreateViewModel.mindMap, mindMapCreateViewModel)
        } else {
            scrollToLocation(mapView, initialLocation, mindMapCreateViewModel)
        }
    }

    val observedUpdateCount = mindMapCreateViewModel.updateCount.observeAsState()
    observedUpdateCount.value?.let {
        mapView.onScaleChange(mindMapCreateViewModel.getScale())
    }

    AndroidView(factory = { mapView })
    ZoomButtonsOverlay(mapView)

    // Node Graph
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

    // Line View
    mapView.lineComposeView.setContent { LineView() }
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
                    NodeStyle.HEADLINE_1 -> H1(
                        task = task,
                        viewModel = mindMapCreateViewModel
                    ) { onClickTaskNode(task) }
                    NodeStyle.HEADLINE_2 -> H2(
                        task = task,
                        viewModel = mindMapCreateViewModel
                    ) { onClickTaskNode(task) }
                    NodeStyle.HEADLINE_3 -> H3(
                        task = task,
                        viewModel = mindMapCreateViewModel
                    ) { onClickTaskNode(task) }
                    NodeStyle.HEADLINE_4 -> H4(
                        task = task,
                        viewModel = mindMapCreateViewModel
                    ) { onClickTaskNode(task) }
                    NodeStyle.BODY_1 -> Body1(
                        task = task,
                        viewModel = mindMapCreateViewModel
                    ) { onClickTaskNode(task) }
                    NodeStyle.BODY_2 -> Body2(
                        task = task,
                        viewModel = mindMapCreateViewModel
                    ) { onClickTaskNode(task) }
                    NodeStyle.LINK -> Link(
                        task = task,
                        viewModel = mindMapCreateViewModel
                    ) { onClickTaskNode(task) }
                }
            }
        }
    }
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun LineView() {
    val context = LocalContext.current
    val mindMapCreateViewModel = hiltViewModel<MindMapCreateViewModel>()

    LineContent(mindMapCreateViewModel = mindMapCreateViewModel, resources = context.resources)
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun ZoomButtonsOverlay(mapView: MapView) {
    val context = LocalContext.current
    val mindMapCreateViewModel = hiltViewModel<MindMapCreateViewModel>()

    val screenWidth = context.resources.displayMetrics.widthPixels
    val screenHeight = context.resources.displayMetrics.heightPixels

    val minScale = min(
        screenWidth.toFloat() / mapView.mapViewOriginalWidth.toFloat(),
        screenHeight.toFloat() / mapView.mapViewOriginalHeight.toFloat()
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd,
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(Color.White.copy(alpha = 0.1f)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = {
                    mindMapCreateViewModel.setScale(mindMapCreateViewModel.getScale().plus(0.1f))
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_zoom_24dp),
                    contentDescription = "Zoom in",
                    tint = Color.LightGray,
                )
            }
            Box(modifier = Modifier.height(50.dp), contentAlignment = Alignment.Center) {
                Text(
                    text = "${(mindMapCreateViewModel.getScale() * 100).roundToInt()}%",
                    color = Color.LightGray,
                )
            }
            IconButton(onClick = {
                if (mindMapCreateViewModel.getScale() - 0.1 <= minScale) {
                    mindMapCreateViewModel.setScale(minScale)
                } else {
                    mindMapCreateViewModel.setScale(mindMapCreateViewModel.getScale().minus(0.1f))
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_zoom_out_24dp),
                    contentDescription = "Zoom out",
                    tint = Color.LightGray,
                )
            }
        }
    }
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
private fun scrollToMindMapNode(
    mapView: MapView,
    mindMap: MindMap,
    mindMapCreateViewModel: MindMapCreateViewModel,
) {
    val context = mapView.context

    val screenWidth = context.resources.displayMetrics.widthPixels
    val scrollX = ((mindMap.x) ?: 0f) * mindMapCreateViewModel.getScale() -
            screenWidth / 2 + NodeStyle.HEADLINE_1.getSize().width * mindMapCreateViewModel.getScale()
    val screenHeight = context.resources.displayMetrics.heightPixels
    val scrollY = ((mindMap.y) ?: 0f) * mindMapCreateViewModel.getScale() -
            screenHeight / 2 + NodeStyle.HEADLINE_1.getSize().height * mindMapCreateViewModel.getScale()
    mapView.horizontalScrollView.post {
        mapView.horizontalScrollView.smoothScrollTo(scrollX.roundToInt(), 0)
    }
    mapView.scrollView.post {
        mapView.scrollView.smoothScrollTo(0, scrollY.roundToInt())
    }
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
private fun scrollToLocation(
    mapView: MapView,
    location: Location,
    mindMapCreateViewModel: MindMapCreateViewModel,
) {
    val context = mapView.context

    val scale = mindMapCreateViewModel.getScale()
    val screenWidth = context.resources.displayMetrics.widthPixels
    val screenHeight = context.resources.displayMetrics.heightPixels

    val scrollX = location.x * scale - screenWidth / 2 + 100
    val scrollY = location.y * scale - screenHeight / 2 + 100
    mapView.horizontalScrollView.post {
        mapView.horizontalScrollView.smoothScrollTo(scrollX.roundToInt(), 0)
    }
    mapView.scrollView.post {
        mapView.scrollView.smoothScrollTo(0, scrollY.roundToInt())
    }
}