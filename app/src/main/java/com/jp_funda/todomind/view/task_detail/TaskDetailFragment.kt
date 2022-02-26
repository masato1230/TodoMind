package com.jp_funda.todomind.view.task_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.jp_funda.todomind.view.MainViewModel
import com.jp_funda.todomind.view.components.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

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

        // Set ActionBar title
        if (!taskDetailViewModel.isEditing) {
            (requireActivity() as AppCompatActivity).supportActionBar?.title = "New Task"
        } else {
            (requireActivity() as AppCompatActivity).supportActionBar?.title = "Editing"
        }

        return ComposeView(requireContext()).apply {
            setContent {
                TaskEditContent(
                    fragment = this@TaskDetailFragment,
                    taskEditableViewModel = taskDetailViewModel,
                    mainViewModel = mainViewModel,
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        taskDetailViewModel.isEditing = false
        mainViewModel.editingTask = null
    }
}