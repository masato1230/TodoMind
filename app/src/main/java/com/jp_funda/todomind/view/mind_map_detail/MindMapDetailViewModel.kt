package com.jp_funda.todomind.view.mind_map_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jp_funda.todomind.data.repositories.mind_map.MindMapRepository
import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MindMapDetailViewModel @Inject constructor(
    private val mindMapRepository: MindMapRepository
) : ViewModel() {
    private var _mindMap = MutableLiveData(MindMap(createdDate = Date()))
    val mindMap: LiveData<MindMap> = _mindMap

    var isEditing: Boolean = false

    private val disposables = CompositeDisposable()

    fun setTitle(title: String) {
        _mindMap.value!!.title = title
        notifyChangeToView()
    }

    fun setDescription(description: String) {
        _mindMap.value!!.description = description
        notifyChangeToView()
    }

    private fun saveMindMapAndClearDisposables() {
        disposables.add(
            if (!isEditing) {
                mindMapRepository.createMindMap(_mindMap.value!!)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally { disposables.clear() }
                    .subscribe()
            } else {
                mindMapRepository.updateMindMap(_mindMap.value!!)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally { disposables.clear() }
                    .subscribe()
            }
        )
    }

    private fun notifyChangeToView() {
        _mindMap.value = mindMap.value?.copy() ?: MindMap()
    }

    override fun onCleared() {
        saveMindMapAndClearDisposables()
    }
}