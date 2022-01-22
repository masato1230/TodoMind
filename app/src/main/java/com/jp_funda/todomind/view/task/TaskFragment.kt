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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jp_funda.todomind.view.components.TaskRow

class TaskFragment : Fragment() {

    companion object {
        fun newInstance() = TaskFragment()
    }

    private lateinit var viewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        return ComposeView(requireContext()).apply {
            setContent {
                TaskContent()
            }
        }
    }

    @Preview
    @Composable
    fun TaskContent() {
        var selectedTabIndex by remember { mutableStateOf(0) }

        Column {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                backgroundColor = Color.Black,
                contentColor = Color.White,
            ) {
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = {
                        // todo add filtering
                        selectedTabIndex = 1
                    },
                    text = { Text("In Progress") }
                )
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = {
                        // todo add filtering
                        selectedTabIndex = 0
                    },
                    text = { Text("Open") }
                )
                Tab(
                    selected = selectedTabIndex == 2,
                    onClick = {
                        // todo add filtering
                        selectedTabIndex = 2
                    },
                    text = { Text("Closed") }
                )
            }

            TaskList()
        }
    }

    @Composable
    fun TaskList() {
        LazyColumn(modifier = Modifier.padding(horizontal = 20.dp)) {
            // todo fill with data
            items(items = List(10) { "d" }) { str ->
                TaskRow()
            }
        }
    }
}