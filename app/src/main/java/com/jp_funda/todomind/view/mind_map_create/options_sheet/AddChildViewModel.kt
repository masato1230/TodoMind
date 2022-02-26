package com.jp_funda.todomind.view.mind_map_create.options_sheet

import com.jp_funda.todomind.data.repositories.ogp.OgpRepository
import com.jp_funda.todomind.data.repositories.task.TaskRepository
import com.jp_funda.todomind.view.task_detail.TaskEditableViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddChildViewModel @Inject constructor(
    taskRepository: TaskRepository,
    ogpRepository: OgpRepository,
) : TaskEditableViewModel(taskRepository, ogpRepository) {}