package com.jp_funda.todomind.view.task_detail

import androidx.compose.material.ExperimentalMaterialApi
import com.jp_funda.todomind.data.repositories.ogp.OgpRepository
import com.jp_funda.todomind.data.repositories.task.TaskRepository
import com.jp_funda.todomind.data.shared_preferences.SettingsPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalMaterialApi
@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    taskRepository: TaskRepository,
    ogpRepository: OgpRepository,
    settingsPreferences: SettingsPreferences,
) : TaskEditableViewModel(taskRepository, ogpRepository, settingsPreferences)
