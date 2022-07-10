package com.jp_funda.todomind.domain.use_cases

import android.content.Context
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.notification.TaskReminder
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where
import java.util.*
import javax.inject.Inject

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
class SetNextReminderUseCase @Inject constructor(private val context: Context) {
    suspend operator fun invoke() {
        // TODO Change below
        Realm.getDefaultInstance().use { realm ->
            // set Reminder
            val date = Date()
            date.minutes -= 1
            val result = realm.where<Task>()
                .greaterThan("dueDate", date)
                .sort("dueDate", Sort.ASCENDING)
                .findFirst()
            result?.let { nextRemindTask ->
                Log.d("Next", nextRemindTask.toString())
                TaskReminder.setTaskReminder(nextRemindTask, context)
            }
        }
    }
}