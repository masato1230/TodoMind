package com.jp_funda.todomind.view.mind_map_create.options_sheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jp_funda.todomind.data.repositories.ogp.OgpRepository
import com.jp_funda.todomind.data.repositories.task.TaskRepository
import com.jp_funda.todomind.view.task_detail.TaskEditableViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    taskRepository: TaskRepository,
    ogpRepository: OgpRepository,
) : TaskEditableViewModel(taskRepository, ogpRepository) {
    private val _selectedMode = MutableLiveData(MindMapOptionsMode.ADD_CHILD)
    val selectedMode: LiveData<MindMapOptionsMode> = _selectedMode

    fun setMode(mode: MindMapOptionsMode) {
        _selectedMode.value = mode
    }
}