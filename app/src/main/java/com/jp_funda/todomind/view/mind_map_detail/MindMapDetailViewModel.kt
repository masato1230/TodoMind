package com.jp_funda.todomind.view.mind_map_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jp_funda.todomind.data.repositories.mind_map.MindMapRepository
import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MindMapDetailViewModel @Inject constructor(
    private val mindMapRepository: MindMapRepository
) : ViewModel() {
    private var _mindMap = MutableLiveData(MindMap(createdDate = Date()))
    val mindMap: LiveData<MindMap> = _mindMap

    var isEditing: Boolean = false
}