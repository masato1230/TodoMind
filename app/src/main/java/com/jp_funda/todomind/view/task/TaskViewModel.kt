package com.jp_funda.todomind.view.task

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor() : ViewModel() {
    // TODO: Implement the ViewModel
    fun logging() {
        Log.d("Success", "Hilt success")
    }
}