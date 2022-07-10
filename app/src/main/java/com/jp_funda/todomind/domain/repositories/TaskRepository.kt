package com.jp_funda.todomind.domain.repositories

import com.jp_funda.todomind.data.repositories.task.entity.Task

interface TaskRepository {
    suspend fun insertTasks(tasks: List<Task>)

    suspend fun getAllTasks(): List<Task>
}