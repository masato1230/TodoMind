package com.jp_funda.todomind.domain.repositories

import com.jp_funda.todomind.data.repositories.task.entity.Task
import java.util.*

interface TaskRepository {
    suspend fun insertTasks(tasks: List<Task>)

    suspend fun getAllTasks(): List<Task>

    suspend fun getTask(id: UUID): Task?
}