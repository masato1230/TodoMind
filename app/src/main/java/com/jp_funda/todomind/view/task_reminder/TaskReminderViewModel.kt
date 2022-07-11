package com.jp_funda.todomind.view.task_reminder

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.data.repositories.ogp.OgpRepository
import com.jp_funda.todomind.data.repositories.task.TaskRepository
import com.jp_funda.todomind.data.shared_preferences.SettingsPreferences
import com.jp_funda.todomind.domain.use_cases.task.GetTaskUseCase
import com.jp_funda.todomind.view.task_detail.TaskEditableViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@HiltViewModel
class TaskReminderViewModel @Inject constructor(
    private val getTaskUseCase: GetTaskUseCase,
    taskRepository: TaskRepository,
    ogpRepository: OgpRepository,
    settingsPreferences: SettingsPreferences,
) : TaskEditableViewModel(
    taskRepository = taskRepository,
    ogpRepository = ogpRepository,
    settingsPreferences = settingsPreferences,
) {
    private val _loading = MutableLiveData(true)
    val loading: LiveData<Boolean> = _loading

    fun getTask(id: UUID) {
        viewModelScope.launch(Dispatchers.IO) {
            val task = getTaskUseCase(id)
            setEditingTask(task!!) // TODO remove forced unwrap
            _loading.postValue(false)
        }
    }
}