package com.jp_funda.todomind.view.task

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.jp_funda.todomind.view.components.TaskRow
import com.jp_funda.todomind.R
import com.jp_funda.todomind.view.components.NewTaskFAB
import com.jp_funda.todomind.view.components.TaskLists
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TaskFragment : Fragment() {

    private val viewModel by viewModels<TaskViewModel>()

    companion object {
        fun newInstance() = TaskFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel.logging()

        return ComposeView(requireContext()).apply {
            setContent {
                NewTaskFAB(onClick = {}) { // TODO add navigation to new task screen
                    TaskContent()
                }
            }
        }
    }

    @Preview
    @Composable
    fun TaskContent() {
        TaskLists()
    }
}