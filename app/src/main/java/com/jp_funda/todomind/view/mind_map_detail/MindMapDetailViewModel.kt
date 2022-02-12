package com.jp_funda.todomind.view.mind_map_detail

import androidx.lifecycle.ViewModel
import com.jp_funda.todomind.data.repositories.mind_map.MindMapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MindMapDetailViewModel @Inject constructor(
    private val mindMapRepository: MindMapRepository
) : ViewModel() {
    var isEditing: Boolean = false
}