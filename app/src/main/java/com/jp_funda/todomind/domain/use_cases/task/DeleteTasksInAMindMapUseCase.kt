package com.jp_funda.todomind.domain.use_cases.task

import com.jp_funda.todomind.database.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.domain.repositories.TaskRepository
import javax.inject.Inject

class DeleteTasksInAMindMapUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(mindMap: MindMap) {
        repository.deleteTasksInAMindMap(mindMap)
    }
}