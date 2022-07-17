package com.jp_funda.todomind.view.mind_map_create

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.shared_preferences.PreferenceKeys
import com.jp_funda.todomind.data.shared_preferences.SettingsPreferences
import com.jp_funda.todomind.view.components.BackNavigationIcon
import com.jp_funda.todomind.view.components.LoadingView
import com.jp_funda.todomind.view.mind_map_create.compoents.LineView
import com.jp_funda.todomind.view.mind_map_create.compoents.NodeGraph
import com.jp_funda.todomind.view.mind_map_create.compoents.ZoomLevelIndicator
import com.jp_funda.todomind.view.mind_map_create.options_sheet.MindMapOptionsSheet
import com.jp_funda.todomind.view.mind_map_create.options_sheet.MindMapOptionsSheetViewModel
import com.jp_funda.todomind.view.mind_map_create.tutorial.TutorialDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.roundToInt

@ExperimentalComposeUiApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun MindMapCreateScreen(
    navController: NavController,
    mindMapId: UUID,
    location: Location,
) {
    val context = LocalContext.current
    val mindMapCreateViewModel = hiltViewModel<MindMapCreateViewModel>()
    val sheetViewModel = hiltViewModel<MindMapOptionsSheetViewModel>()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(initialValue = BottomSheetValue.Collapsed),
    )
    val isShowTutorialDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        mindMapCreateViewModel.run {
            // Set mind map id
            setMindMapId(mindMapId)
            // Set mind map data
            mindMapCreateViewModel.initializeScale()
            // Load data and refresh view
            delay(1000) // delay for wait db update at previous screen
            mindMapCreateViewModel.refreshView()
        }

        // Show tutorial dialog for first time
        val settingsPreferences = SettingsPreferences(context)
        if (!settingsPreferences.getBoolean(PreferenceKeys.IS_NOT_FIRST_TIME_CREATE_SCREEN)) {
            isShowTutorialDialog.value = true
            settingsPreferences.setBoolean(PreferenceKeys.IS_NOT_FIRST_TIME_CREATE_SCREEN, true)
        }
    }

    val isLoading = mindMapCreateViewModel.isLoading.observeAsState()

    if (isLoading.value == false) {
        BottomSheetScaffold(
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
            scaffoldState = bottomSheetScaffoldState,
            sheetContent = {
                LaunchedEffect(Unit) {
                    sheetViewModel.setEditingMindMap(mindMapCreateViewModel.mindMap)
                }
                MindMapOptionsSheet(bottomSheetState = bottomSheetScaffoldState.bottomSheetState)
            },
            sheetPeekHeight = 0.dp,
            sheetBackgroundColor = colorResource(id = R.color.deep_purple),
            floatingActionButton = {
                ZoomLevelIndicator()
            },
            backgroundColor = colorResource(id = R.color.deep_purple),
        ) {
            // Tutorial Dialog
            TutorialDialog(isShowDialog = isShowTutorialDialog)

            // Main Content
            MindMapCreateContent(
                initialLocation = location,
                bottomSheetState = bottomSheetScaffoldState.bottomSheetState,
            )
        }
    } else {
        LoadingView()
    }
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun MindMapCreateContent(
    initialLocation: Location?,
    bottomSheetState: BottomSheetState,
) {
    val context = LocalContext.current
    val mindMapCreateViewModel = hiltViewModel<MindMapCreateViewModel>()
    val mapView = MapView(context).apply { onScaleChange(mindMapCreateViewModel.getScale()) }
    val sheetViewModel = hiltViewModel<MindMapOptionsSheetViewModel>()
    val coroutineScope = rememberCoroutineScope()

    // Initial Scroll
    LaunchedEffect(Unit) {
        initialLocation?.let { scrollToLocation(mapView, it, mindMapCreateViewModel) }
    }

    val state = rememberTransformableState { scaleChange, _, _ ->
        val currentScale = mindMapCreateViewModel.getScale()
        val scaleAfter = currentScale * scaleChange
        if (scaleAfter > mapView.minScale && scaleAfter < 2f) {
            mindMapCreateViewModel.setScale(scaleAfter)
            mapView.onScaleChange(scaleAfter)
        }
    }

    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .transformable(state = state)
            .pointerInteropFilter {
                mapView.onTouchEvent(it)
                return@pointerInteropFilter false
            },
        factory = { mapView },
    )


    // Node Graph
    mapView.composeView.setContent {
        NodeGraph(
            modifier = Modifier.fillMaxSize(),
            onClickMindMapNode = {
                // Reset Selected Node
                sheetViewModel.setNode(null)
                coroutineScope.launch {
                    bottomSheetState.expand()
                }
            },
            onClickTaskNode = { task ->
                // Set selected Node
                sheetViewModel.setNode(task)
                coroutineScope.launch {
                    bottomSheetState.expand()
                }
            }
        )
    }

    // Line View
    mapView.lineComposeView.setContent { LineView() }
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