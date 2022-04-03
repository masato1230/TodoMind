package com.jp_funda.todomind.view.mind_map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jp_funda.todomind.data.repositories.mind_map.MindMapRepository
import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class MindMapViewModel @Inject constructor(
    private val mindMapRepository: MindMapRepository
) : ViewModel() {
    // All MindMap Data
    private val _mindMapList = MutableLiveData<List<MindMap>>(null)
    val mindMapList: LiveData<List<MindMap>> = _mindMapList

    // Dispose
    private val disposables = CompositeDisposable()

    fun refreshMindMapListData() {
        disposables.add(
            mindMapRepository.getAllMindMaps()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    // TODO delete
                    Log.d("Mind Map Data", it.toString())
                    _mindMapList.value = emptyList() // Change list length to notify data change
                    val sortedMindList = it.sortedWith(compareByDescending { comparingMindMap -> comparingMindMap.updatedDate })
                    _mindMapList.value = sortedMindList
                }
                .subscribe()
        )
    }
}