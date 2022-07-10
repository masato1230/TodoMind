package com.jp_funda.todomind.view.task_detail

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.data.repositories.ogp.OgpRepository
import com.jp_funda.todomind.data.repositories.task.TaskRepository
import com.jp_funda.todomind.data.shared_preferences.SettingsPreferences
import com.jp_funda.todomind.domain.use_cases.task.CreateTasksUseCase
import com.jp_funda.todomind.domain.use_cases.task.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    taskRepository: TaskRepository,
    ogpRepository: OgpRepository,
    createTasksUseCase: CreateTasksUseCase,
    updateTaskUseCase: UpdateTaskUseCase,
    settingsPreferences: SettingsPreferences,
) : TaskEditableViewModel(
    taskRepository = taskRepository,
    ogpRepository = ogpRepository,
    createTasksUseCase = createTasksUseCase,
    updateTaskUseCase = updateTaskUseCase,
    settingsPreferences = settingsPreferences,
)
