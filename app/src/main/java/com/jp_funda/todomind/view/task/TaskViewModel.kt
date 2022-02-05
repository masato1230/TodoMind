package com.jp_funda.todomind.view.task

import androidx.compose.material.SnackbarHostState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jp_funda.todomind.data.repositories.task.TaskRepository
import com.jp_funda.todomind.data.repositories.task.entity.Task
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

    fun refreshTaskListData() {
        repository.getAllTasks()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ it ->
                // sort taskList by order column
                val sortedList = it.sortedBy { task -> task.reversedOrder }.reversed()
                _taskList.value = emptyList() // Change list length to notify data change to UI
                _taskList.value = sortedList
            }, {
                Throwable("Error at taskViewModel getAllTask")
            })
    }

    private fun updateDbWithTask(task: Task) {
        repository.updateTask(task)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {
                Throwable("Error at taskViewModel updateTask")
            })
    }

    fun updateTaskWithDelay(task: Task) {
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

    fun replaceReversedOrderOfTasks(task1: Task, task2: Task) {
        val updatedReversedOrder1 = task2.reversedOrder
        val updatedReversedOrder2 = task1.reversedOrder

        // Update Showing Task before db task
        val tempTasks = taskList.value!!.toList()
        tempTasks.firstOrNull { it.id == task1.id }!!.reversedOrder = updatedReversedOrder1
        tempTasks.firstOrNull { it.id == task2.id }!!.reversedOrder = updatedReversedOrder2
        _taskList.value = emptyList()
        _taskList.value = tempTasks.sortedBy { task -> task.reversedOrder }.reversed()
        
        // Update DB
        updateDbWithTask(task1)
        updateDbWithTask(task2)
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