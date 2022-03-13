package com.jp_funda.todomind.extension

import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus

/** Calculate progress rate
 * @return progress rate in range of 0..100
 */
fun List<Task>.getProgressRate(): Float {
    val completedTaskCount = this.filter { it.statusEnum == TaskStatus.Complete }.size
    if (this.isEmpty()) {
        return 0f
    }
    return completedTaskCount / this.size.toFloat() * 100
}