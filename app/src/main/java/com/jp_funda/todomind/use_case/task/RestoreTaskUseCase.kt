package com.jp_funda.todomind.use_case.task

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.repositories.task.entity.Task
import com.jp_funda.repositories.task.TaskRepository
import com.jp_funda.todomind.use_case.SetNextReminderUseCase
import java.util.*
import javax.inject.Inject

@ExperimentalComposeUiApi
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