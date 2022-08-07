package com.jp_funda.todomind.domain.use_cases.task

import com.jp_funda.todomind.database.repositories.task.entity.Task
import com.jp_funda.todomind.database.repositories.task.entity.TaskStatus
import com.jp_funda.todomind.domain.repositories.TaskRepository
import javax.inject.Inject

class GetTasksFilteredByStatusUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(status: TaskStatus): List<Task> {
        return repository.getTasksFilteredByStatus(status)
    }
}