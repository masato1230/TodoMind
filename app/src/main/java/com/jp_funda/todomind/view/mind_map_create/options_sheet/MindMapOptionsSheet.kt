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
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jp_funda.todomind.view.MainViewModel
import com.jp_funda.todomind.view.components.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@ExperimentalMaterialApi
@AndroidEntryPoint
class MindMapOptionsSheet : BottomSheetDialogFragment() {

    // ViewModels
    private val addChildViewModel by viewModels<AddChildViewModel>()
    private val editTaskViewModel by viewModels<EditTaskViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setUpChildNode()

        return ComposeView(requireContext()).apply {
            setContent {
                val observedMode = editTaskViewModel.selectedMode.observeAsState()
                observedMode.value?.let { selectedMode ->
                    Column(
                        modifier = Modifier.padding(bottom = 20.dp)
                    ) {
                        MindMapOptionsTabRow(selectedMode = selectedMode) {
                            editTaskViewModel.setMode(it)
                        }

                        // Add Child Option
                        AnimatedVisibility(
                            visible = selectedMode == MindMapOptionsMode.ADD_CHILD,
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
                                fragment = this@MindMapOptionsSheet,
                                taskEditableViewModel = addChildViewModel,
                                mainViewModel = null,
                            )
                        }

                        // Edit Task Option
                        // TODO set Editing Task
                        AnimatedVisibility(
                            visible = selectedMode == MindMapOptionsMode.EDIT_TASK,
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
                                fragment = this@MindMapOptionsSheet,
                                taskEditableViewModel = editTaskViewModel,
                                mainViewModel = null,
                            )
                        }
                    }
                }
            }
        }
    }

    fun setUpChildNode() {
        mainViewModel.selectedNode?.let { selectedTask ->
            addChildViewModel.setX(selectedTask.x ?: 0f + 100) // set child position to right side of parent
            addChildViewModel.setY(selectedTask.y ?: 0f)
        } ?: run {
            addChildViewModel.setX(mainViewModel.editingMindMap?.x ?: 0f)
            addChildViewModel.setY(mainViewModel.editingMindMap?.y ?: 0f)
        }
    }
}