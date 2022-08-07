package com.jp_funda.todomind.view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.repositories.SampleData
import com.jp_funda.repositories.task.entity.Task
import com.jp_funda.todomind.sharedpreference.PreferenceKey
import com.jp_funda.todomind.sharedpreference.SettingsPreference
import com.jp_funda.todomind.use_case.mind_map.CreateMindMapUseCase
import com.jp_funda.todomind.use_case.task.CreateTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@HiltViewModel
class MainViewModel
@Inject constructor(
    private val createTasksUseCase: CreateTasksUseCase,
    private val createMindMapUseCase: CreateMindMapUseCase,
    private val settingsPreference: SettingsPreference,
) : ViewModel() {
    fun addSampleData() {
        viewModelScope.launch(Dispatchers.IO) {
            createMindMapUseCase(SampleData.mindMap)
            createTasksUseCase(SampleData.sampleTasks)
            settingsPreference.setBoolean(
                PreferenceKey.IS_NOT_FIRST_TIME_LAUNCH,
                true,
            )
        }
    }

    /** Currently deleted task - show at task list snackbar to restore the task */
    var currentlyDeletedTask: Task? = null
}