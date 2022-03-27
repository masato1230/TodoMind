package com.jp_funda.todomind.view.task_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
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
                                    val onClick = {
                                        mainViewModel.editingMindMap = it
                                        val action =
                                            TaskDetailFragmentDirections.actionNavigationTaskDetailToNavigationMindMapCreate()
                                        action.initialLocation = Location(
                                            x = taskDetailViewModel.task.value?.x ?: 0f,
                                            y = taskDetailViewModel.task.value?.y ?: 0f,
                                        )
                                        findNavController().navigate(action)
                                    }
                                    val color = it.color?.let { color -> Color(color) }
                                        ?: run { colorResource(id = R.color.crimson) }
                                    IconButton(onClick = onClick) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_mind_map),
                                            contentDescription = "Mind Map",
                                            tint = color,
                                        )
                                    }
                                    Text(
                                        text = it.title ?: "",
                                        color = color,
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 1,
                                        modifier = Modifier
                                            .widthIn(max = 120.dp)
                                            .clickable { onClick() }
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
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

        mainViewModel.editingTask = null
    }
}