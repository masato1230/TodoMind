package com.jp_funda.todomind.view.task_detail

import com.jp_funda.todomind.data.repositories.ogp.OgpRepository
import com.jp_funda.todomind.data.repositories.task.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    taskRepository: TaskRepository,
    ogpRepository: OgpRepository,
) : TaskEditableViewModel(taskRepository, ogpRepository) {}
