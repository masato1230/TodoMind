package com.jp_funda.todomind.view.mind_map_create.options_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
import com.jp_funda.todomind.view.MainViewModel
import com.jp_funda.todomind.view.components.MindMapOptionsTabRow
import com.jp_funda.todomind.view.components.TaskEditContent
import com.jp_funda.todomind.view.mind_map_create.MindMapCreateViewModel
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@AndroidEntryPoint
class MindMapOptionsSheet : BottomSheetDialogFragment() {

    // ViewModels
    private val sheetViewModel by viewModels<MindMapOptionsViewModel>()
    private val addChildViewModel by viewModels<AddChildViewModel>()
    private val editTaskViewModel by viewModels<EditTaskViewModel>()
    private val mindMapCreateViewModel by activityViewModels<MindMapCreateViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setUpAddingChildNode()

        // Set mind map to addChildViewModel
        mainViewModel.editingMindMap?.let { addChildViewModel.setMindMap(it) }
        // Set parentTask to addChildViewModel
        mainViewModel.selectedNode?.let { addChildViewModel.initializeParentTask(it) }
        // Set addChildViewModel's new task status as open
        addChildViewModel.setStatus(TaskStatus.Open)

        return ComposeView(requireContext()).apply {
            setContent {
                val observedMode = sheetViewModel.selectedMode.observeAsState()
                observedMode.value?.let { selectedMode ->
                    Column(
                        modifier = Modifier.padding(bottom = 20.dp)
                    ) {
                        // When taskNode is Selected
                        if (mainViewModel.selectedNode != null) {
                            MindMapOptionsTabRow(selectedMode = selectedMode) {
                                sheetViewModel.setMode(it)
                            }

                            // Edit Task Option
                            // set Editing Task
                            mainViewModel.selectedNode?.let { editTaskViewModel.setEditingTask(it) }
                            AnimatedVisibility(
                                visible = selectedMode == MindMapOptionsMode.EDIT_TASK,
                                enter = slideInHorizontally(
                                    initialOffsetX = { -width }, // small slide 300px
                                    animationSpec = tween(
                                        durationMillis = 200,
                                        easing = LinearEasing // interpolator
                                    )
                                ),
                                exit = ExitTransition.None
                                ) {
                                TaskEditContent(
                                    taskEditableViewModel = editTaskViewModel,
                                    mainViewModel = null,
                                ) { dismiss() }
                            }
                        } else { // When mindMap Node is selected
                            TabRow(
                                modifier = Modifier.height(50.dp),
                                selectedTabIndex = selectedMode.ordinal,
                                backgroundColor = colorResource(id = R.color.transparent),
                                contentColor = Color.LightGray,
                            ) {
                                Tab(
                                    selected = true,
                                    onClick = {},
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_mind_map),
                                            contentDescription = "Edit"
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(
                                            "Add Child",
                                            style = MaterialTheme.typography.subtitle1
                                        )
                                    }
                                }
                            }
                            TaskEditContent(
                                taskEditableViewModel = addChildViewModel,
                                mainViewModel = null,
                            ) { dismiss() }
                        }

                        // Add Child Option
                        AnimatedVisibility(
                            visible = selectedMode == MindMapOptionsMode.ADD_CHILD,
                            enter = slideInHorizontally(
                                initialOffsetX = { width }, // small slide 300px
                                animationSpec = tween(
                                    durationMillis = 200,
                                    easing = LinearEasing // interpolator
                                )
                            ),
                            exit = ExitTransition.None
                        ) {
                            TaskEditContent(
                                taskEditableViewModel = addChildViewModel,
                                mainViewModel = null,
                            ) { dismiss() }
                        }
                    }
                }
            }
        }
    }

    /** Set position for adding child node */
    private fun setUpAddingChildNode() {
        mainViewModel.selectedNode?.let { selectedTask ->
            addChildViewModel.setX(
                (selectedTask.x ?: 0f) + 300
            ) // set child position to right side of parent
            addChildViewModel.setY(selectedTask.y ?: 0f)
        } ?: run {
            addChildViewModel.setX((mainViewModel.editingMindMap?.x ?: 0f) + 300f)
            addChildViewModel.setY(mainViewModel.editingMindMap?.y ?: 0f)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // update mapView with updated data
        mindMapCreateViewModel.refreshView()
    }
}