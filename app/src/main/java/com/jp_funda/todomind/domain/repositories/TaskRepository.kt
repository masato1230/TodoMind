package com.jp_funda.todomind.domain.repositories

import com.jp_funda.todomind.database.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.database.repositories.task.entity.Task
import com.jp_funda.todomind.database.repositories.task.entity.TaskStatus
import java.util.*

interface TaskRepository {
    suspend fun insertTasks(tasks: List<Task>)

    suspend fun getAllTasks(): List<Task>

    suspend fun getTask(id: UUID): Task?

    suspend fun getNextRemindTask(lastRemindedTask: Task?): Task?

    suspend fun getTasksInAMindMap(mindMap: MindMap): List<Task>

    suspend fun getTasksFilteredByStatus(status: TaskStatus): List<Task>

    suspend fun updateTask(updatedTask: Task)

    suspend fun deleteTask(task: Task)

    suspend fun deleteTasksInAMindMap(mindMap: MindMap)
}