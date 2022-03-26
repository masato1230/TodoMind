package com.jp_funda.todomind.view.task_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.jp_funda.todomind.R
import com.jp_funda.todomind.view.MainViewModel
import com.jp_funda.todomind.view.components.BackNavigationIcon
import com.jp_funda.todomind.view.components.TaskEditContent
import com.jp_funda.todomind.view.mind_map_create.Location
import dagger.hilt.android.AndroidEntryPoint

@androidx.compose.material.ExperimentalMaterialApi
@AndroidEntryPoint
class TaskDetailFragment : Fragment() {

    companion object {
        fun newInstance() = TaskDetailFragment()
    }

    // ViewModels
    private val taskDetailViewModel by viewModels<TaskDetailViewModel>()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Check whether to edit or create new task by mainViewModel editingTask
        mainViewModel.editingTask?.let { editingTask ->
            taskDetailViewModel.setEditingTask(editingTask)
        }

        return ComposeView(requireContext()).apply {
            setContent {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = if (taskDetailViewModel.isEditing) "Task Detail" else "New Task") },
                            backgroundColor = colorResource(id = R.color.deep_purple),
                            contentColor = Color.White,
                            navigationIcon = { BackNavigationIcon() },
                            actions = {
                                taskDetailViewModel.task.value?.mindMap?.let {
                                    IconButton(onClick = {
                                        mainViewModel.editingMindMap = it
                                        val action =
                                            TaskDetailFragmentDirections.actionNavigationTaskDetailToNavigationMindMapCreate()
                                        action.initialLocation = Location(
                                            x = taskDetailViewModel.task.value?.x ?: 0f,
                                            y = taskDetailViewModel.task.value?.y ?: 0f,
                                        )
                                        findNavController().navigate(R.id.action_navigation_task_detail_to_navigation_mind_map_create)
                                    }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_mind_map),
                                            contentDescription = "Mind Map"
                                        )
                                    }
                                }
                            }
                        )
                    },
                    backgroundColor = colorResource(id = R.color.deep_purple),
                ) {
                    TaskEditContent(
                        taskEditableViewModel = taskDetailViewModel,
                        mainViewModel = mainViewModel,
                    ) { findNavController().popBackStack() }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        taskDetailViewModel.isEditing = false
        mainViewModel.editingTask = null
    }
}