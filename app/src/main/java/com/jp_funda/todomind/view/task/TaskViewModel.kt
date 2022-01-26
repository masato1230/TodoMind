package com.jp_funda.todomind.view.task

import android.util.Log
import androidx.lifecycle.ViewModel
import com.jp_funda.todomind.data.repositories.task.TaskRepository
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

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