package com.jp_funda.todomind.view.task

import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jp_funda.todomind.data.repositories.task.TaskRepository
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
import com.jp_funda.todomind.view.components.filterTasksByStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {
    // All Task Data
    private val _taskList = MutableLiveData(listOf<Task>())
    val taskList: LiveData<List<Task>> = _taskList

    // Showing Task Data
    private val _showingTasks = MutableLiveData(listOf<Task>())
    val showingTasks: LiveData<List<Task>> = _showingTasks

    fun refreshTaskListData() {
        repository.getAllTasks()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _taskList.value = emptyList() // Change list length to notify data change to UI
                _taskList.value = it
            }, {
                Throwable("Error at taskViewModel getAllTask")
            })
    }

    fun updateShowingTasks(status: TaskStatus) {
        _showingTasks.value = filterTasksByStatus(
            status = status,
            tasks = _taskList.value!!,
        )
    }

    fun updateTaskStatus(task: Task) {
        repository.updateTask(task)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .delay(300, TimeUnit.MILLISECONDS)
            .subscribe({
                refreshTaskListData()
            }, {
                Throwable("Error at taskViewModel updateTask")
            })
    }

    fun addDummyTask() {
        val newTask = Task(title = UUID.randomUUID().toString())
        repository.createTask(newTask)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            }, {
                Throwable("Error")
            })
    }

    // Show Snackbar
    suspend fun showSnackbar(message: String, snackbarHostState: SnackbarHostState) {
        snackbarHostState.showSnackbar(message, actionLabel = "ok")
    }
}