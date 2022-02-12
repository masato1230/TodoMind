package com.jp_funda.todomind.view.mind_map_detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.compose.rememberImagePainter
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
import com.jp_funda.todomind.view.MainViewModel
import com.jp_funda.todomind.view.components.ColumnWithTaskList
import com.jp_funda.todomind.view.components.filterTasksByStatus
import com.jp_funda.todomind.view.TaskViewModel
import com.jp_funda.todomind.view.components.OgpThumbnail
import com.jp_funda.todomind.view.components.WhiteButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

// TODO add delete button
@AndroidEntryPoint
class MindMapDetailFragment : Fragment() {

    companion object {
        fun newInstance() = MindMapDetailFragment()
    }

    // ViewModels
    private val mindMapDetailViewModel by viewModels<MindMapDetailViewModel>()
    private val taskViewModel: TaskViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // TODO Check whether to edit or create new task by mainViewModel editingMindMap

        // Refresh TaskList
        taskViewModel.refreshTaskListData()

        // Hide default ActionBar
        (activity as AppCompatActivity).supportActionBar?.hide()

        // return layout
        return ComposeView(requireContext()).apply {
            setContent {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Mind Map Detail") },
                            backgroundColor = colorResource(id = R.color.deep_purple),
                            contentColor = Color.White,
                            navigationIcon = {
                                IconButton(onClick = { findNavController().popBackStack() }) {
                                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                                }
                            },
                            actions = {
                                IconButton(onClick = {
                                    mindMapDetailViewModel.deleteMindMapAndClearDisposables {
                                        findNavController().popBackStack()
                                    } }) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
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
        val tasks by taskViewModel.taskList.observeAsState()
        var selectedTabStatus by remember { mutableStateOf(TaskStatus.InProgress) }
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()

        if (tasks != null) {
            var showingTasks by remember { mutableStateOf(tasks!!) }

            showingTasks = filterTasksByStatus(
                status = TaskStatus.values().first { it == selectedTabStatus },
                tasks = tasks!!.filter { task ->
                    (task.mindMap?.id == mindMapDetailViewModel.mindMap.value!!.id) &&
                            (task.statusEnum == selectedTabStatus)
                },
            )
            ColumnWithTaskList(
                selectedTabStatus = selectedTabStatus,
                onTabChange = { status ->
                    selectedTabStatus = status
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
        } else { // Loading
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

        observedMindMap?.let { mindMap ->
            // Title
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
            // Thumbnail Section
            Image(
                painter = painterResource(
                    id = R.drawable.img_mind_map_sample // TODO change image to real mind map
                ),
                contentDescription = "Mind Map description",
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.Crop,
            )

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
                    leadingIcon = ImageVector.vectorResource(id = R.drawable.ic_mind_map_24dp)
                ) {
                    // TODO onClick
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            TextField(
                colors = colors,
                modifier = Modifier.padding(bottom = 10.dp),
                value = mindMap.description ?: "",
                onValueChange = {
                    mindMapDetailViewModel.setDescription(it)
                    mindMapDetailViewModel.extractUrlAndFetchOgp(it)
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

            // OGP thumbnail
            ogpResult?.image?.let {
                OgpThumbnail(ogpResult = ogpResult!!, context = context)
            }


            Spacer(modifier = Modifier.height(10.dp))

            // Progress Section
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
                    text = "70%",
                    style = MaterialTheme.typography.body1,
                    color = Color.White
                )
            }
            // Progress bar
            RoundedProgressBar(percent = 70)

            Spacer(modifier = Modifier.height(50.dp))

            // Task list Section
            Text(
                text = "Tasks - ${mindMap.title ?: ""}",
                color = Color.White,
                style = MaterialTheme.typography.h6
            )
        }
    }

    // Mind Map Detail Components
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
                    .background(foregroundColor)
                    .width(maxWidth * percent / 100)
                    .height(height)
            )
        }
    }
}