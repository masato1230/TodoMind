package com.jp_funda.todomind.domain.use_cases.task

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.domain.repositories.TaskRepository
import com.jp_funda.todomind.domain.use_cases.SetNextReminderUseCase
import java.util.*
import javax.inject.Inject

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
class UpdateTaskUseCase @Inject constructor(
    private val repository: TaskRepository,
    private val setNextReminderUseCase: SetNextReminderUseCase,
) {
    suspend operator fun invoke(updatedTask: Task) {
        // Update db
        updatedTask.updatedDate = Date()
        repository.updateTask(updatedTask)

        // Update reminder
        setNextReminderUseCase()
    }
}