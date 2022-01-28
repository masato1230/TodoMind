package com.jp_funda.todomind.view.task

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.jp_funda.todomind.view.components.TaskRow
import com.jp_funda.todomind.R
import com.jp_funda.todomind.view.components.NewTaskFAB
import com.jp_funda.todomind.view.components.TaskLists
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@AndroidEntryPoint
class TaskFragment : Fragment() {

    private val taskViewModel by viewModels<TaskViewModel>()

    companion object {
        fun newInstance() = TaskFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindViewModel()

        return ComposeView(requireContext()).apply {
            setContent {
                NewTaskFAB(onClick = {}) { // TODO add navigation to new task screen
                    TaskContent()
                }
            }
        }
    }

    private fun bindViewModel() {
        taskViewModel.addDummyTask()
        taskViewModel.getAllTasks()
    }

    @Preview
    @Composable
    fun TaskContent() {
        val tasks by taskViewModel.taskList.observeAsState()

        if (taskViewModel.taskList.value != null) {
            TaskLists(tasks!!)
        } else {
            CircularProgressIndicator()
        }
    }
}