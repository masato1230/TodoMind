package com.jp_funda.todomind.view.mind_map_create.options_sheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jp_funda.todomind.data.repositories.task.entity.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MindMapOptionsViewModel @Inject constructor() : ViewModel() {
    private val _selectedMode = MutableLiveData(MindMapOptionsMode.EDIT_TASK)
    val selectedMode: LiveData<MindMapOptionsMode> = _selectedMode

    /** Selected Node. null means mind map is selected */
    private val _selectedNode = MutableLiveData<Task?>(null)
    val selectedNode: LiveData<Task?> = _selectedNode

    fun setMode(mode: MindMapOptionsMode) {
        _selectedMode.value = mode
    }

    fun setNode(node: Task?) {
        _selectedNode.value = node
    }
}