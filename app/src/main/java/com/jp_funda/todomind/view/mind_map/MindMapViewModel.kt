package com.jp_funda.todomind.view.mind_map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.domain.use_cases.mind_map.GetAllMindMapsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MindMapViewModel @Inject constructor(
    private val getAllMindMapsUseCase: GetAllMindMapsUseCase,
) : ViewModel() {
    // All MindMap Data
    private val _mindMapList = MutableLiveData<List<MindMap>>()
    val mindMapList: LiveData<List<MindMap>> = _mindMapList

    fun refreshMindMapListData() {
        viewModelScope.launch(Dispatchers.IO) {
            val mindMps = getAllMindMapsUseCase().sortedWith(compareByDescending { it.updatedDate })
            _mindMapList.postValue(mindMps)
        }
    }
}