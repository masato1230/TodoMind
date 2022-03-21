package com.jp_funda.todomind.view.mind_map_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.NodeStyle
import com.jp_funda.todomind.data.getSize
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
import com.jp_funda.todomind.extension.getProgressRate
import com.jp_funda.todomind.view.MainViewModel
import com.jp_funda.todomind.view.TaskViewModel
import com.jp_funda.todomind.view.components.*
import com.jp_funda.todomind.view.mind_map_create.MindMapCreateViewModel
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@ExperimentalMaterialApi
@AndroidEntryPoint
class MindMapDetailFragment : Fragment() {

    // ViewModels
    private val mindMapDetailViewModel by viewModels<MindMapDetailViewModel>()
    private val mindMapThumbnailViewModel by viewModels<MindMapCreateViewModel>()
    private val taskViewModel: TaskViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Check whether to edit or create new mind map by mainViewModel editingMindMap
        mainViewModel.editingMindMap?.let { editingMindMap ->
            mindMapDetailViewModel.setEditingMindMap(editingMindMap)
        } ?: run { // Create new mind map -> set initial position to horizontal center of mapView
            val mapViewWidth = resources.getDimensionPixelSize(R.dimen.map_view_width)
            mindMapDetailViewModel.setX(mapViewWidth.toFloat() / 2 - NodeStyle.HEADLINE_1.getSize().width / 2)
        }

        // Refresh TaskList
        taskViewModel.refreshTaskListData()

        // Get Dialog Result
        setFragmentResultListener(ConfirmDeleteMindMapFragment.REQUEST_KEY) { _, bundle ->
            val isConfirmed = bundle.getBoolean(ConfirmDeleteMindMapFragment.KEY)

            if (isConfirmed) {
                mindMapDetailViewModel.deleteMindMapAndClearDisposables {
                    findNavController().popBackStack()
                }
            }
        }

        // Set up Thumbnail - set scale and Load task data for drawing mindMap thumbnail
        mainViewModel.editingMindMap?.let {
            mindMapThumbnailViewModel.mindMap = it
            mindMapThumbnailViewModel.setScale(0.05f)
            mindMapThumbnailViewModel.refreshView()
        }

        // return layout
        return ComposeView(requireContext()).apply {
            setContent {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = if (mindMapDetailViewModel.isEditing) "Mind Map Detail" else "New Mind Map") },
                            backgroundColor = colorResource(id = R.color.deep_purple),
                            contentColor = Color.White,
                            navigationIcon = { BackNavigationIcon() },
                            actions = {
                                IconButton(onClick = {
                                    findNavController().popBackStack()
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Done,
                                        contentDescription = "Save"
                                    )
                                }
                                IconButton(onClick = {
                                    // show dialog
                                    findNavController().navigate(R.id.action_navigation_mind_map_detail_to_navigation_confirm_mind_map_delete)
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
                    MindMapDetailContent()
                }
            }
        }
    }

    @Preview
    @Composable
    fun MindMapDetailContent() {
        val observedTasks by taskViewModel.taskList.observeAsState()
        val selectedTabStatus by taskViewModel.selectedStatusTab.observeAsState(TaskStatus.Open)
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()

        observedTasks?.let { tasks ->
            var showingTasks by remember { mutableStateOf(tasks) }

            showingTasks = filterTasksByStatus(
                status = TaskStatus.values().first { it == selectedTabStatus },
                tasks = tasks.filter { task ->
                    (task.mindMap?.id == mindMapDetailViewModel.mindMap.value!!.id) &&
                            (task.statusEnum == selectedTabStatus)
                },
            )
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
                    mainViewModel.editingTask = task
                    findNavController().navigate(R.id.action_navigation_mind_map_detail_to_navigation_task_detail)
                }
            ) {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    MindMapDetailTopContent()
                }
            }
        } ?: run { // Loading
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(150.dp)
                        .height(150.dp),
                    color = colorResource(id = R.color.teal_200),
                    strokeWidth = 10.dp
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "Loading...",
                    style = MaterialTheme.typography.h5,
                    color = Color.White
                )
            }
        }
    }

    @Composable
    fun MindMapDetailTopContent() {
        val context = LocalContext.current
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
                textStyle = MaterialTheme.typography.h4,
                placeholder = {
                    Text(
                        text = "Enter title",
                        color = Color.Gray,
                        style = MaterialTheme.typography.h4,
                    )
                }
            )

            /** Thumbnail Section */
            ThumbnailSection()

            Spacer(modifier = Modifier.height(20.dp))

            // Date and Edit Mind Map Button Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Date
                val dateString = mindMap.createdDate?.let {
                    SimpleDateFormat("EEE MM/dd", Locale.getDefault()).format(it)
                } ?: run {
                    SimpleDateFormat("EEE MM/dd", Locale.getDefault()).format(Date())
                }
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
                    navigateToMindMapCreate()
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

            Spacer(modifier = Modifier.height(10.dp))

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

    // Mind Map Detail Components
    @Composable
    fun ThumbnailSection() {
        if (mainViewModel.editingMindMap != null) {
            val isLoadingState = mindMapThumbnailViewModel.isLoading.observeAsState()
            isLoadingState.value?.let { isLoading ->
                if (isLoading) {
                    Text("Loading...")
                } else {
                    Box(modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.Black)
                        .height(200.dp)
                        .fillMaxWidth()
                        .onSizeChanged {
                            // Adjust mind map scale to fit it to thumbnail
                            val scale =
                                it.width.toFloat() / resources.getDimensionPixelSize(R.dimen.map_view_width)
                            mindMapThumbnailViewModel.setScale(scale)
                        }
                        .clickable { navigateToMindMapCreate() }) {
                        LineContent(
                            mindMapCreateViewModel = mindMapThumbnailViewModel,
                            resources = resources,
                        )
                        MindMapCreateContent(
                            modifier = Modifier.fillMaxSize(),
                            mindMapCreateViewModel = mindMapThumbnailViewModel,
                            onClickMindMapNode = { navigateToMindMapCreate() },
                            onClickTaskNode = { navigateToMindMapCreate() },
                        )
                    }
                }
            }
        } else { // Thumbnail for first time
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.Black)
                    .height(200.dp)
                    .fillMaxWidth()
                    .clickable { navigateToMindMapCreate() },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_mind_map),
                    tint = Color.White,
                    contentDescription = "Mind Map Icon",
                    modifier = Modifier
                        .height(130.dp)
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                )
                Text(
                    text = "Expand mind map",
                    style = MaterialTheme.typography.caption,
                    color = Color.White,
                )
            }
        }
    }

    @Composable
    fun ProgressSection() {
        // observe task status update
        val observedUpdateCount = mindMapThumbnailViewModel.updateCount.observeAsState()
        observedUpdateCount.value?.let {
            // Progress description
            Row(
                modifier = Modifier
                    .padding(start = 10.dp, bottom = 5.dp)
                    .fillMaxWidth(),
            ) {
                Text(
                    text = "Progress: ",
                    style = MaterialTheme.typography.body1,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "${mindMapThumbnailViewModel.tasks.getProgressRate().roundToInt()}%",
                    style = MaterialTheme.typography.body1,
                    color = Color.White
                )
            }
            // Progress bar
            RoundedProgressBar(
                percent = mindMapThumbnailViewModel.tasks.getProgressRate().roundToInt()
            )
        }
    }

    @Composable
    fun RoundedProgressBar(
        modifier: Modifier = Modifier,
        percent: Int,
        height: Dp = 10.dp,
        backgroundColor: Color = colorResource(id = R.color.white_10),
        foregroundColor: Brush = Brush.horizontalGradient(
            listOf(colorResource(id = R.color.teal_200), colorResource(id = R.color.teal_200))
        ),
    ) {
        BoxWithConstraints(
            modifier = modifier
                .fillMaxWidth()
                .height(height)
                .clip(RoundedCornerShape(20.dp))
        ) {
            Box(
                modifier = modifier
                    .background(backgroundColor)
                    .fillMaxWidth()
                    .height(height)
            )
            Box(
                modifier = modifier
                    .animateContentSize(animationSpec = tween(durationMillis = 1500))
                    .background(foregroundColor)
                    .width(maxWidth * percent / 100)
                    .height(height)
            )
        }
    }

    private fun navigateToMindMapCreate() {
        mainViewModel.editingMindMap = mindMapDetailViewModel.mindMap.value
        findNavController().navigate(R.id.action_navigation_mind_map_detail_to_navigation_mind_map_create)
    }

    override fun onPause() {
        super.onPause()
        if (mindMapDetailViewModel.isAutoSaveNeeded) {
            mindMapDetailViewModel.saveMindMapAndClearDisposables()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mindMapDetailViewModel.isEditing = false
    }

    override fun onDestroy() {
        super.onDestroy()
        mainViewModel.editingMindMap = null
    }
}