package com.jp_funda.todomind.view

import androidx.lifecycle.ViewModel
import com.jp_funda.todomind.data.repositories.task.entity.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    // Editing task at TaskDetailView - if this field is null, user is creating new task
    var editingTask: Task? = null

    var currentlyDeletedTask: Task? = null
}