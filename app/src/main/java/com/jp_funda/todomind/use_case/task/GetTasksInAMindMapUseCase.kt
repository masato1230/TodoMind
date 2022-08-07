package com.jp_funda.todomind.use_case.task

import com.jp_funda.repositories.mind_map.entity.MindMap
import com.jp_funda.repositories.task.entity.Task
import com.jp_funda.repositories.task.TaskRepository
import javax.inject.Inject

class GetTasksInAMindMapUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(mindMap: MindMap): List<Task> {
        return repository.getTasksInAMindMap(mindMap)
    }
}