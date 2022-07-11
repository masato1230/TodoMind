package com.jp_funda.todomind.view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.data.repositories.task.TaskRepository
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
import com.jp_funda.todomind.domain.use_cases.task.GetAllTasksUseCase
import com.jp_funda.todomind.domain.use_cases.task.RestoreTaskUseCase
import com.jp_funda.todomind.domain.use_cases.task.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository,
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val restoreTaskUseCase: RestoreTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
) : ViewModel() {
    // All Task Data
    private val _taskList = MutableLiveData<List<Task>>(null) // do not set null in other place
    val taskList: LiveData<List<Task>> = _taskList

    // selected tab
    private val _selectedStatusTab = MutableLiveData(TaskStatus.InProgress)
    val selectedStatusTab: LiveData<TaskStatus> = _selectedStatusTab

    // Dispose
    private val disposables = CompositeDisposable()

    fun refreshTaskListData() {
        viewModelScope.launch(Dispatchers.IO) {
            // TODO move sort logic to use case
            val sortedList = getAllTasksUseCase().sortedByDescending { task -> task.reversedOrder }
            _taskList.postValue(sortedList)
        }
    }

    fun setSelectedStatusTab(status: TaskStatus) {
        _selectedStatusTab.value = status
    }

    private fun updateDbWithTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            updateTaskUseCase(task)
        }
    }

    fun updateTaskWithDelay(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(500)
            updateTaskUseCase(task)
            refreshTaskListData()
        }
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
                else -> {
                    return
                }
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
            viewModelScope.launch(Dispatchers.IO) {
                restoreTaskUseCase(deletedTask)
                refreshTaskListData()
            }
        }
    }

    override fun onCleared() {
        disposables.clear()
    }
}