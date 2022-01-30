package com.jp_funda.todomind.view.task_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jp_funda.todomind.data.repositories.task.TaskRepository
import com.jp_funda.todomind.data.repositories.task.entity.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.Realm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {
    private var _newTask = MutableLiveData(Task())
    val newTask: LiveData<Task> = _newTask

    fun setTitle(title: String) {
        _newTask.value = _newTask.value?.copy() ?: Task()
        _newTask.value!!.title = title
    }
}