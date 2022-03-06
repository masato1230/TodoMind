package com.jp_funda.todomind.view.mind_map_detail

import com.jp_funda.todomind.data.repositories.mind_map.MindMapRepository
import com.jp_funda.todomind.data.repositories.task.TaskRepository
import com.jp_funda.todomind.view.mind_map_create.MindMapCreateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MindMapThumbnailViewModel @Inject constructor(
    taskRepository: TaskRepository,
    mindMapRepository: MindMapRepository,
) : MindMapCreateViewModel(
    mindMapRepository = mindMapRepository,
    taskRepository = taskRepository,
)