package com.jp_funda.todomind.view.task

import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jp_funda.todomind.data.repositories.task.TaskRepository
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import javax.annotation.meta.When
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {
    // All Task Data
    private val _taskList = MutableLiveData(listOf<Task>())
    val taskList: LiveData<List<Task>> = _taskList

    private val _selectedTabIndex = MutableLiveData(0)
    val selectedTabIndex: LiveData<Int> = _selectedTabIndex

    private val disposables = CompositeDisposable()

    fun refreshTaskListData() {
        disposables.add(
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
        )
    }

    fun setTaskListEmpty() {
        _taskList.value = emptyList()
    }

    private fun updateDbWithTask(task: Task) {
        disposables.add(
            repository.updateTask(task)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, {
                    Throwable("Error at taskViewModel updateTask")
                })
        )
    }

    fun updateTaskWithDelay(task: Task) {
        disposables.add(
            repository.updateTask(task)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .delay(300, TimeUnit.MILLISECONDS)
                .subscribe({
                    refreshTaskListData()
                }, {
                    Throwable("Error at taskViewModel updateTask")
                })
        )
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
        disposables.add(
            repository.createTask(newTask)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                }, {
                    Throwable("Error")
                })
        )
    }

    // Show Undo Status Snackbar
    suspend fun showCheckBoxChangedSnackbar(
        beforeUndoTask: Task,
        snackbarHostState: SnackbarHostState
    ) {
        val snackbarResult = snackbarHostState.showSnackbar(
            "Move ${beforeUndoTask.title ?: ""} to ${beforeUndoTask.statusEnum.name}",
            actionLabel = "Undo"
        )

        // Undo button is Clicked - Restore data before checkbox click
        if (snackbarResult == SnackbarResult.ActionPerformed) {
            when (beforeUndoTask.statusEnum) {
                TaskStatus.InProgress -> beforeUndoTask.statusEnum = TaskStatus.Complete
                TaskStatus.Complete -> beforeUndoTask.statusEnum = TaskStatus.InProgress
                else -> { return }
            }
            updateDbWithTask(beforeUndoTask)
            refreshTaskListData()
        }
    }

    // Show Undo delete Snackbar
    suspend fun showUndoDeleteSnackbar(
        deletedTask: Task,
        snackbarHostState: SnackbarHostState
    ) {
        val snackbarResult = snackbarHostState.showSnackbar(
            message = "${deletedTask.title ?: ""} Deleted",
            actionLabel = "Undo"
        )

        // When Undo button is Clicked - Restore deleted data
        if (snackbarResult == SnackbarResult.ActionPerformed) {
            repository.restoreTask(deletedTask)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    refreshTaskListData()
                }, {})
        }
    }

    // Tab
    fun setSelectedTabIndex(selectedIndex: Int) {
        _selectedTabIndex.value = selectedIndex
    }

    override fun onCleared() {
        disposables.clear()
    }
}