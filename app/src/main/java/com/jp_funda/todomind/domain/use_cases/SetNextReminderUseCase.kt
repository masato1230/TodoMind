package com.jp_funda.todomind.domain.use_cases

import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.database.repositories.task.entity.Task
import com.jp_funda.todomind.domain.use_cases.task.GetNextRemindTaskUseCase
import com.jp_funda.todomind.notification.TaskReminder
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
class SetNextReminderUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getNextRemindTaskUseCase: GetNextRemindTaskUseCase,
) {
    suspend operator fun invoke(lastRemindedTask: Task? = null) {
        val nextRemindTask = getNextRemindTaskUseCase.invoke(lastRemindedTask)
        if (nextRemindTask != null) {
            TaskReminder.setTaskReminder(nextRemindTask, context)
        }
    }
}