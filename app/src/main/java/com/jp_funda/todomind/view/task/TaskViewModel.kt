package com.jp_funda.todomind.view.task

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jp_funda.todomind.data.repositories.task.TaskRepository
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {
    var selectedTabIndex by mutableStateOf(0)

    private val _taskList = MutableLiveData(listOf<Task>())
    val taskList: LiveData<List<Task>> = _taskList

    fun getAllTasks() {
        repository.getAllTasks()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _taskList.value = it
            }, {
                Throwable("Error at taskViewModel getAllTask")
            })
    }

    fun updateTask(task: Task) {
        repository.updateTask(task)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("Update", "Updated")
                getAllTasks()
                Log.d("Task", taskList.value!!.filter { it.statusEnum == TaskStatus.Complete }.toString())
            }, {
                Throwable("Error at taskViewModel updateTask")
            })
    }

    fun addDummyTask() {
        val newTask = Task(title = "Test Test Task")
        repository.createTask(newTask)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            }, {
                Throwable("Error")
            })
    }

    fun getInProgressTasks(): Single<List<Task>> {
        return repository.getTasksFilteredByStatus(TaskStatus.InProgress)
    }

    fun getOpenTasks(): Single<List<Task>> {
        return repository.getTasksFilteredByStatus(TaskStatus.Open)
    }

    fun getClosedTasks(): Single<List<Task>> {
        return repository.getTasksFilteredByStatus(TaskStatus.Complete)
    }
}