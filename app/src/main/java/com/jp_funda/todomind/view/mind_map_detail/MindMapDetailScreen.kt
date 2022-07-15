package com.jp_funda.todomind.view.mind_map_detail

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.repositories.task.entity.NodeStyle
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
import com.jp_funda.todomind.data.repositories.task.entity.getSize
import com.jp_funda.todomind.navigation.NavigationRoute
import com.jp_funda.todomind.navigation.arguments.MindMapCreateArguments
import com.jp_funda.todomind.view.MainViewModel
import com.jp_funda.todomind.view.TaskViewModel
import com.jp_funda.todomind.view.components.*
import com.jp_funda.todomind.view.components.dialog.ColorPickerDialog
import com.jp_funda.todomind.view.components.dialog.ConfirmDialog
import com.jp_funda.todomind.view.mind_map_create.MindMapCreateViewModel
import com.jp_funda.todomind.view.mind_map_detail.components.ProgressSection
import com.jp_funda.todomind.view.mind_map_detail.components.ThumbnailSection
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MindMapDetailScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    mindMapId: String?,
) {
    val context = LocalContext.current
    val mindMapDetailViewModel = hiltViewModel<MindMapDetailViewModel>()
    val mindMapThumbnailViewModel = hiltViewModel<MindMapCreateViewModel>()
    val taskViewModel = hiltViewModel<TaskViewModel>()
    val isShowConfirmDeleteDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        // Check whether to edit or create new mind map by mindMapId
        mindMapId?.let { id ->
            mindMapDetailViewModel.loadEditingMindMap(UUID.fromString(id))
        } ?: run { // Create new mind map -> set initial position to horizontal center of mapView
            val mapViewWidth = context.resources.getDimensionPixelSize(R.dimen.map_view_width)
            mindMapDetailViewModel.setX(mapViewWidth.toFloat() / 2 - NodeStyle.HEADLINE_1.getSize().width / 2)
        }

        // Refresh TaskList
        taskViewModel.refreshTaskListData()

        // Set up Thumbnail - set scale and Load task data for drawing mindMap thumbnail
        delay(1000) // todo delete
        if (mindMapDetailViewModel.isEditing) {
            mindMapDetailViewModel.mindMap.value?.let {
                mindMapThumbnailViewModel.mindMap = it
                mindMapThumbnailViewModel.setScale(0.05f)
                mindMapThumbnailViewModel.refreshView()
            }
        }
    }

    DisposableEffect(key1 = LocalLifecycleOwner.current) {
        onDispose {
            if (mindMapDetailViewModel.isAutoSaveNeeded) {
                mindMapDetailViewModel.saveMindMap()
            }
            mindMapDetailViewModel.isEditing = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = if (mindMapDetailViewModel.isEditing) "Mind Map Detail" else "New Mind Map") },
                backgroundColor = colorResource(id = R.color.deep_purple),
                contentColor = Color.White,
                navigationIcon = { BackNavigationIcon(navController) },
                actions = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Save"
                        )
                    }
                    IconButton(onClick = {
                        // show dialog
                        isShowConfirmDeleteDialog.value = true
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete"
                        )
                    }
                }
            )
        },
        backgroundColor = colorResource(id = R.color.deep_purple)
    ) {
        // Confirm Dialog for Deleting mind map
        if (isShowConfirmDeleteDialog.value) {
            ConfirmDialog(
                title = stringResource(id = R.string.question_confirm_delete),
                message = stringResource(id = R.string.notify_tasks_delete),
                isShowDialog = isShowConfirmDeleteDialog,
                isShowNegativeButton = true,
                onClickPositive = {
                    mindMapDetailViewModel.deleteMindMap()
                    navController.popBackStack()
                },
            )
        }

        MindMapDetailContent(navController, mainViewModel)
    }
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun MindMapDetailContent(
    navController: NavController,
    mainViewModel: MainViewModel,
) {
    val taskViewModel = hiltViewModel<TaskViewModel>()
    val mindMapDetailViewModel = hiltViewModel<MindMapDetailViewModel>()

    val observedTasks by taskViewModel.taskList.observeAsState()
    val selectedTabStatus by taskViewModel.selectedStatusTab.observeAsState(TaskStatus.Open)
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Show Undo snackbar if currentlyDeletedTask is not null
    LaunchedEffect(snackbarHostState) {
        mainViewModel.currentlyDeletedTask?.let {
            taskViewModel.showUndoDeleteSnackbar(
                snackbarHostState = snackbarHostState,
                deletedTask = it
            )
        }
        mainViewModel.currentlyDeletedTask = null
    }

    observedTasks?.let { tasks ->
        var showingTasks by remember { mutableStateOf(tasks) }

        showingTasks = filterTasksByStatus(
            status = TaskStatus.values().first { it == selectedTabStatus },
            tasks = tasks.filter { task ->
                (task.mindMap?.id == mindMapDetailViewModel.mindMap.value!!.id) &&
                        (task.statusEnum == selectedTabStatus)
            },
        )

        // MainUI
        Column {
            ColumnWithTaskList(
                selectedTabStatus = selectedTabStatus,
                onTabChange = { status ->
                    taskViewModel.setSelectedStatusTab(status)
                },
                showingTasks = showingTasks,
                onCheckChanged = { task ->
                    taskViewModel.updateTaskWithDelay(task)
                    scope.launch {
                        taskViewModel.showCheckBoxChangedSnackbar(
                            task,
                            snackbarHostState
                        )
                    }
                },
                onRowMove = { fromIndex, toIndex ->
                    // Replace task's reversedOrder property
                    if (Integer.max(fromIndex, toIndex) < showingTasks.size) {
                        val fromTask = showingTasks.sortedBy { task -> task.reversedOrder }
                            .reversed()[fromIndex]
                        val toTask = showingTasks.sortedBy { task -> task.reversedOrder }
                            .reversed()[toIndex]
                        taskViewModel.replaceReversedOrderOfTasks(fromTask, toTask)
                    }
                },
                onRowClick = { task ->
                    navController.navigate("${NavigationRoute.TaskDetail}?${task.id}")
                }
            ) {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    MindMapDetailTopContent(navController, mainViewModel)
                }
            }
        }

        // Snackbar
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom,
        ) {
            // Status update Snackbar
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(bottom = 10.dp),
            )
        }
    } ?: run {
        LoadingView()
    }
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun MindMapDetailTopContent(
    navController: NavController,
    mainViewModel: MainViewModel,
) {
    val context = LocalContext.current
    val mindMapDetailViewModel = hiltViewModel<MindMapDetailViewModel>()

    // Set up data
    val observedMindMap by mindMapDetailViewModel.mindMap.observeAsState()
    val ogpResult by mindMapDetailViewModel.ogpResult.observeAsState()

    // Set up TextFields color
    val colors = TextFieldDefaults.textFieldColors(
        textColor = Color.White,
        disabledTextColor = Color.White,
        backgroundColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        cursorColor = colorResource(id = R.color.teal_200),
    )

    // Set up dialog
    val colorDialogState = rememberMaterialDialogState()
    ColorPickerDialog(colorDialogState) { selectedColor ->
        mindMapDetailViewModel.setColor(selectedColor.toArgb())
    }

    observedMindMap?.let { mindMap ->

        // Launch effect
        LaunchedEffect(ogpResult) {
            if (!mindMap.description.isNullOrEmpty() && mindMapDetailViewModel.isShowOgpThumbnail) {
                mindMapDetailViewModel.extractUrlAndFetchOgp(mindMap.description!!)
            }
        }

        /** Title */
        TextField(
            colors = colors,
            modifier = Modifier.padding(bottom = 10.dp),
            value = mindMap.title ?: "",
            onValueChange = mindMapDetailViewModel::setTitle,
            textStyle = MaterialTheme.typography.h5,
            placeholder = {
                Text(
                    text = "Enter title",
                    color = Color.Gray,
                    style = MaterialTheme.typography.h5,
                )
            }
        )

        /** Thumbnail Section */
        ThumbnailSection(!mindMapDetailViewModel.isEditing) {
            navigateToMindMapCreate(
                navController = navController,
                mainViewModel = mainViewModel,
                mindMapDetailViewModel = mindMapDetailViewModel,
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Date and Edit Mind Map Button Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Date
            val dateString =
                SimpleDateFormat("EEE MM/dd", Locale.getDefault()).format(mindMap.createdDate)
            Text(
                text = "Created on: $dateString",
                style = MaterialTheme.typography.subtitle1,
                color = Color.White
            )
            // Edit Mind Map Button
            WhiteButton(
                text = "Mind Map",
                leadingIcon = ImageVector.vectorResource(id = R.drawable.ic_mind_map)
            ) {
                navigateToMindMapCreate(navController, mainViewModel, mindMapDetailViewModel)
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        /** Color Selector Section */
        TextField(
            colors = colors,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { colorDialogState.show() },
            value = mindMap.colorHex ?: "",
            onValueChange = {},
            placeholder = {
                Text(text = "Set mind map color", color = Color.Gray)
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_color_24dp),
                    tint = mindMap.color?.let { Color(it) }
                        ?: run { colorResource(id = R.color.pink_dark) },
                    contentDescription = "Color",
                )
            },
            readOnly = true,
            enabled = false,
        )

        Spacer(modifier = Modifier.height(15.dp))

        /** Description Section */
        TextField(
            colors = colors,
            modifier = Modifier.padding(bottom = 10.dp),
            value = mindMap.description ?: "",
            onValueChange = {
                mindMapDetailViewModel.setDescription(it)
                // do not check whether description contains url when isShowOgpThumbnail setting is off
                if (mindMapDetailViewModel.isShowOgpThumbnail) {
                    mindMapDetailViewModel.extractUrlAndFetchOgp(it)
                }
            },
            textStyle = MaterialTheme.typography.body1,
            placeholder = {
                Text(text = "Enter description", color = Color.Gray)
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_notes_24dp),
                    contentDescription = "Description",
                    tint = Color.White
                )
            }
        )

        /** OGP thumbnail */
        ogpResult?.image?.let {
            OgpThumbnail(ogpResult = ogpResult!!, context = context)
        }

        /** Mark as Completed */
        val clickableColors = TextFieldDefaults.textFieldColors(
            disabledTextColor = mindMap.color?.let { Color(it) }
                ?: run { colorResource(id = R.color.pink_dark) },
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = colorResource(id = R.color.teal_200),
        )
        TextField(
            colors = clickableColors,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { mindMapDetailViewModel.setIsCompleted(!(mindMap.isCompleted)) },
            value = if (!mindMap.isCompleted) "Mark ${mindMap.title ?: ""} as Completed"
            else "${mindMap.title ?: ""} Completed",
            onValueChange = {},
            leadingIcon = {
                Icon(
                    painter = painterResource(
                        id =
                        if (!mindMap.isCompleted) R.drawable.ic_checkbox_unchecked
                        else R.drawable.ic_checkbox_checked
                    ),
                    tint = mindMap.color?.let { Color(it) }
                        ?: run { colorResource(id = R.color.pink_dark) },
                    contentDescription = "mind map status"
                )
            },
            readOnly = true,
            enabled = false,
        )

        /** Progress Section */
        ProgressSection()

        Spacer(modifier = Modifier.height(50.dp))

        /** Task list Section */
        Text(
            text = "Tasks - ${mindMap.title ?: ""}",
            color = Color.White,
            style = MaterialTheme.typography.h6
        )
    }
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
private fun navigateToMindMapCreate(
    navController: NavController,
    mainViewModel: MainViewModel,
    mindMapDetailViewModel: MindMapDetailViewModel,
) {
    mainViewModel.mindMapCreateArguments = MindMapCreateArguments(
        editingMindMap = mindMapDetailViewModel.mindMap.value!!,
        initialLocation = null,
    )
    navController.navigate(NavigationRoute.MindMapCreate)
}