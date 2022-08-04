package com.jp_funda.todomind.notification

import android.app.*
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.getBroadcast
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.NotificationCompat
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
import com.jp_funda.todomind.data.shared_preferences.NotificationPreferences
import com.jp_funda.todomind.data.shared_preferences.PreferenceKeys
import com.jp_funda.todomind.data.shared_preferences.SettingsPreferences
import com.jp_funda.todomind.domain.use_cases.SetNextReminderUseCase
import com.jp_funda.todomind.domain.use_cases.task.GetNextRemindTaskUseCase
import com.jp_funda.todomind.domain.use_cases.task.GetTaskUseCase
import com.jp_funda.todomind.extension.extractFirstFiveDigits
import com.jp_funda.todomind.view.task_reminder.TaskReminderActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.math.abs

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class TaskReminder : BroadcastReceiver() {

    @Inject
    lateinit var getTaskUseCase: GetTaskUseCase

    @Inject
    lateinit var getNextReminderUseCase: GetNextRemindTaskUseCase

    @Inject
    lateinit var setReminderUseCase: SetNextReminderUseCase

    @Inject
    lateinit var notificationPreferences: NotificationPreferences

    @Inject
    lateinit var settingsPreferences: SettingsPreferences

    companion object {
        const val CHANNEL_ID = "task_reminder_channel"
        const val CHANNEL_NAME = "task_reminder_channel"
        const val ID_KEY = "task_reminder_id"

        fun setTaskReminder(task: Task, context: Context) {
            task.dueDate?.let { dueDate ->
                val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
                val intent = Intent(context, TaskReminder::class.java)
                    .putExtra(ID_KEY, task.id.toString())
                val pendingIntent = getBroadcast(
                    context,
                    task.id.extractFirstFiveDigits(),
                    intent,
                    FLAG_IMMUTABLE
                )
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    dueDate.time,
                    pendingIntent
                )
            }
        }

        fun cancelTaskReminder(task: Task, context: Context) {
            val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, TaskReminder::class.java)
                .putExtra(ID_KEY, task.id.toString())
            val pendingIntent = getBroadcast(
                context,
                task.id.extractFirstFiveDigits(),
                intent,
                FLAG_IMMUTABLE
            )
            alarmManager.cancel(pendingIntent)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("OnReceive", "Receive")
        val taskId = intent.getStringExtra(ID_KEY)
        Log.d("id", taskId.toString())
        taskId?.let {
            CoroutineScope(Dispatchers.Default).launch {
                val task = getTaskUseCase(UUID.fromString(it))!! // TODO remove forced unwrap
                try {
                    if (
                        settingsPreferences.getBoolean(PreferenceKeys.IS_REMIND_TASK_DEADLINE) &&
                        task.dueDate != null &&
                        abs(task.dueDate!!.time - Date().time) < 1000 * 120 &&
                        task.statusEnum != TaskStatus.Complete
                    ) {
                        showNotification(
                            context,
                            task.title ?: "",
                            task.description ?: "",
                            task.id.toString(),
                        )
                    }
                    // Set nextReminder todo replace with invoking setNextReminderUseCase
                    setReminderUseCase(task)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun showNotification(context: Context, title: String, desc: String, taskId: String) {
        // set reminding task id to shared preference
        notificationPreferences.setString(PreferenceKeys.REMINDING_TASK_ID, taskId)

        // notify
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            manager.createNotificationChannel(channel)
        }

        // Create an Intent for the ReminderActivity
        val resultIntent = Intent(context, TaskReminderActivity::class.java)
        val resultPendingIntent: PendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(resultIntent)
            getPendingIntent(UUID.fromString(taskId).extractFirstFiveDigits(), FLAG_IMMUTABLE)
        }

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentIntent(resultPendingIntent)
            .setContentTitle(title)
            .setContentText(desc)
            .setColor(Color(R.color.light_purple).toArgb())
            .setSmallIcon(R.drawable.ic_mind_map)
        manager.notify(0, builder.build())
    }
}