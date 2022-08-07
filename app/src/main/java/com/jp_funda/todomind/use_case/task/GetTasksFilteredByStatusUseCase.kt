package com.jp_funda.todomind.use_case.task

import com.jp_funda.repositories.task.entity.Task
import com.jp_funda.repositories.task.entity.TaskStatus
import com.jp_funda.repositories.task.TaskRepository
import javax.inject.Inject

class GetTasksFilteredByStatusUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(status: TaskStatus): List<Task> {
        return repository.getTasksFilteredByStatus(status)
    }
}