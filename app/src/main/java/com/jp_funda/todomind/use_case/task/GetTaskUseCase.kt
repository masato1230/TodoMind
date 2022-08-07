package com.jp_funda.todomind.use_case.task

import com.jp_funda.repositories.task.entity.Task
import com.jp_funda.repositories.task.TaskRepository
import java.util.*
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(id: UUID): Task? {
        return repository.getTask(id)
    }
}