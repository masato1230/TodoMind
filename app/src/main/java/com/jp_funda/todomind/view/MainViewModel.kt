package com.jp_funda.todomind.view

import androidx.lifecycle.ViewModel
import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.data.repositories.task.entity.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    /** Editing task at TaskDetailView - if this field is null, user is creating new task */
    var editingTask: Task? = null

    /** Editing mind map - if this field is null, user is creating new mind map */
    var editingMindMap: MindMap? = null

    /** Creating mind map - mind map which is faze of creation at MindMapCreateFragment */
    var creatingMindMap: MindMap? = null

    /** Currently deleted task - show at task list snackbar to restore the task */
    var currentlyDeletedTask: Task? = null
}