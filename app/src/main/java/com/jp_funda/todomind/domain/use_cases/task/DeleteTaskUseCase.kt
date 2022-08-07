package com.jp_funda.todomind.domain.use_cases.task

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.database.repositories.task.entity.Task
import com.jp_funda.todomind.domain.repositories.TaskRepository
import com.jp_funda.todomind.domain.use_cases.SetNextReminderUseCase
import javax.inject.Inject

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
class DeleteTaskUseCase @Inject constructor(
    private val repository: TaskRepository,
    private val setNextReminderUseCase: SetNextReminderUseCase,
) {
    suspend operator fun invoke(task: Task) {
        repository.deleteTask(task)

        // Set a reminder
        setNextReminderUseCase()
    }
}