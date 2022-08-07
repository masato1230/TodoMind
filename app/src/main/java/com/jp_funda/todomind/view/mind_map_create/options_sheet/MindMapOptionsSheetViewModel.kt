package com.jp_funda.todomind.view.mind_map_create.options_sheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jp_funda.repositories.mind_map.entity.MindMap
import com.jp_funda.repositories.task.entity.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MindMapOptionsSheetViewModel @Inject constructor() : ViewModel() {
    private lateinit var _editingMindMap: MindMap
    val editingMindMap: MindMap
        get() {
            return _editingMindMap
        }

    private val _selectedMode = MutableLiveData(MindMapOptionsMode.EDIT_TASK)
    val selectedMode: LiveData<MindMapOptionsMode> = _selectedMode

    /** Selected Node. null means mind map is selected */
    private val _selectedNode = MutableLiveData<Task?>(null)
    val selectedNode: LiveData<Task?> = _selectedNode

    fun setEditingMindMap(mindMap: MindMap) {
        _editingMindMap = mindMap
    }

    fun setMode(mode: MindMapOptionsMode) {
        _selectedMode.value = mode
    }

    fun setNode(node: Task?) {
        _selectedNode.value = node
    }
}