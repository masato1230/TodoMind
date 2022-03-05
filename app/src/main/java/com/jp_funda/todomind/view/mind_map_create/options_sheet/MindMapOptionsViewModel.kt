package com.jp_funda.todomind.view.mind_map_create.options_sheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MindMapOptionsViewModel @Inject constructor() : ViewModel() {
    private val _selectedMode = MutableLiveData(MindMapOptionsMode.ADD_CHILD)
    val selectedMode: LiveData<MindMapOptionsMode> = _selectedMode

    fun setMode(mode: MindMapOptionsMode) {
        _selectedMode.value = mode
    }
}