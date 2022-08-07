package com.jp_funda.todomind.use_case.task

import com.jp_funda.repositories.task.entity.Task
import com.jp_funda.repositories.task.TaskRepository
import javax.inject.Inject

class GetAllTasksUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(): List<Task> {
        return repository.getAllTasks()
    }
}