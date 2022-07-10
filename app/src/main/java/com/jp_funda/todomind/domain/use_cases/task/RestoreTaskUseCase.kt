package com.jp_funda.todomind.domain.use_cases.task

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.domain.repositories.TaskRepository
import com.jp_funda.todomind.domain.use_cases.SetNextReminderUseCase
import java.util.*
import javax.inject.Inject

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
class RestoreTaskUseCase @Inject constructor(
    private val repository: TaskRepository,
    private val setNextReminderUseCase: SetNextReminderUseCase,
) {
    suspend operator fun invoke(task: Task) {
        // Restore and set updatedDate
        task.updatedDate = Date()
        repository.insertTasks(listOf(task))

        // Set a reminder
        setNextReminderUseCase()
    }
}