package com.jp_funda.todomind.domain.use_cases.task

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.database.repositories.task.entity.Task
import com.jp_funda.todomind.domain.repositories.TaskRepository
import com.jp_funda.todomind.domain.use_cases.SetNextReminderUseCase
import java.util.*
import javax.inject.Inject

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
class CreateTasksUseCase @Inject constructor(
    private val repository: TaskRepository,
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val setNextReminderUseCase: SetNextReminderUseCase,
) {
    suspend operator fun invoke(tasks: List<Task>) {
        // Calculate reversed order
        val maxReservedOrder =
            getAllTasksUseCase().maxWithOrNull(Comparator.comparingInt { lastTask ->
                lastTask.reversedOrder ?: 0
            })?.reversedOrder ?: 0

        val now = Date()
        tasks.forEachIndexed { index, task ->
            task.run {
                reversedOrder = maxReservedOrder + index + 1
                createdDate = now
                updatedDate = now
            }
        }

        // Save tasks to DB
        repository.insertTasks(tasks)
        // set a reminder
        setNextReminderUseCase()
    }
}