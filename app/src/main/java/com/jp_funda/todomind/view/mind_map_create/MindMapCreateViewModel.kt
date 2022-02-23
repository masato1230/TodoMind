package com.jp_funda.todomind.view.mind_map_create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MindMapCreateViewModel @Inject constructor() : ViewModel() {
    private val _scale = MutableLiveData<Float>(1f)
    val scale: LiveData<Float> = _scale

    fun setScale(scale: Float) {
        _scale.value = scale
    }
}