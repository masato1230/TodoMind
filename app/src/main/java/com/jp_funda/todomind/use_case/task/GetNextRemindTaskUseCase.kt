package com.jp_funda.todomind.use_case.task

import com.jp_funda.repositories.task.entity.Task
import com.jp_funda.repositories.task.TaskRepository
import javax.inject.Inject

// todo consider deletion
class GetNextRemindTaskUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(lastRemindedTask: Task?): Task? {
        return repository.getNextRemindTask(lastRemindedTask)
    }
}