package com.jp_funda.todomind.domain.use_cases.task

import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.domain.repositories.TaskRepository
import java.util.*
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(id: UUID): Task? {
        return repository.getTask(id)
    }
}