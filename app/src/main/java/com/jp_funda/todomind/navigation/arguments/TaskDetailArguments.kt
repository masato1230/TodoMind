package com.jp_funda.todomind.navigation.arguments

import com.jp_funda.todomind.data.repositories.task.entity.Task

/**
 * @param editingTask editing task, null create new task
 */
data class TaskDetailArguments(val editingTask: Task?)