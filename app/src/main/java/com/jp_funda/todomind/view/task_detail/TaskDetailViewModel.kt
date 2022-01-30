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
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {
    private var _task = MutableLiveData(Task(createdDate = Date()))
    val task: LiveData<Task> = _task

    fun setEditingTask(editingTask: Task) {
        _task.value = editingTask
    }

    fun setTitle(title: String) {
        _task.value!!.title = title
        notifyChangeToView()
    }

    fun setDescription(description: String) {
        _task.value!!.description = description
        notifyChangeToView()
    }

    fun setDate(localDate: LocalDate) {
        _task.value!!.dueDate =
            Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
        notifyChangeToView()
    }


    fun setColor(argb: Int) {
        _task.value!!.color = argb
        notifyChangeToView()
    }

    fun notifyChangeToView() {
        _task.value = task.value?.copy() ?: Task()
    }
}
