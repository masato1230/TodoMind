package com.jp_funda.todomind.domain.use_cases.task

import com.jp_funda.todomind.database.repositories.task.entity.Task
import com.jp_funda.todomind.domain.repositories.TaskRepository
import javax.inject.Inject

// todo consider deletion
class GetNextRemindTaskUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(lastRemindedTask: Task?): Task? {
        return repository.getNextRemindTask(lastRemindedTask)
    }
}