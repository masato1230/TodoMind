package com.jp_funda.todomind.view.mind_map_create

import com.jp_funda.todomind.data.repositories.ogp.OgpRepository
import com.jp_funda.todomind.data.repositories.task.TaskRepository
import com.jp_funda.todomind.view.task_detail.TaskEditableViewModel
import javax.inject.Inject

class EditTaskViewModel @Inject constructor(
    taskRepository: TaskRepository,
    ogpRepository: OgpRepository,
) : TaskEditableViewModel(taskRepository, ogpRepository) {
}