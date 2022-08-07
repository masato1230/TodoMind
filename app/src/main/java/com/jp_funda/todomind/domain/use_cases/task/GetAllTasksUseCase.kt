package com.jp_funda.todomind.domain.use_cases.task

import com.jp_funda.todomind.database.repositories.task.entity.Task
import com.jp_funda.todomind.domain.repositories.TaskRepository
import javax.inject.Inject

class GetAllTasksUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(): List<Task> {
        return repository.getAllTasks()
    }
}