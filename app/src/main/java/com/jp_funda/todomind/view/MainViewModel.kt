package com.jp_funda.todomind.view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.data.SampleData
import com.jp_funda.todomind.database.repositories.task.entity.Task
import com.jp_funda.todomind.data.shared_preferences.PreferenceKeys
import com.jp_funda.todomind.data.shared_preferences.SettingsPreferences
import com.jp_funda.todomind.domain.use_cases.mind_map.CreateMindMapUseCase
import com.jp_funda.todomind.domain.use_cases.task.CreateTasksUseCase
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
    private val settingsPreferences: SettingsPreferences,
) : ViewModel() {
    fun addSampleData() {
        viewModelScope.launch(Dispatchers.IO) {
            createMindMapUseCase(SampleData.mindMap)
            createTasksUseCase(SampleData.sampleTasks)
            settingsPreferences.setBoolean(
                PreferenceKeys.IS_NOT_FIRST_TIME_LAUNCH,
                true,
            )
        }
    }

    /** Currently deleted task - show at task list snackbar to restore the task */
    var currentlyDeletedTask: Task? = null
}