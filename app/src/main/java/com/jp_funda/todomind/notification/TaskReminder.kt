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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.NotificationCompat
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.repositories.task.TaskRepository
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.data.shared_preferences.NotificationPreferences
import com.jp_funda.todomind.data.shared_preferences.PreferenceKeys
import com.jp_funda.todomind.extension.extractFirstFiveDigits
import com.jp_funda.todomind.view.task_reminder.TaskReminderActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import kotlin.math.abs

@ExperimentalMaterialApi
@AndroidEntryPoint
class TaskReminder : BroadcastReceiver() {

    @Inject
    lateinit var taskRepository: TaskRepository

    @Inject
    lateinit var notificationPreferences: NotificationPreferences

    companion object {
        const val CHANNEL_ID = "task_reminder_channel"
        const val CHANNEL_NAME = "task_reminder_channel"
        const val ID_KEY = "task_reminder_id"

//        fun setReminder2(task: Task, context: Context) {
//            task.dueDate?let { dueDate ->}
//        }

        fun setTaskReminder(task: Task, context: Context) {
            task.dueDate?.let { dueDate ->
                val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
                val intent = Intent(context, TaskReminder::class.java)
                    .putExtra(ID_KEY, task.id.toString())
                val pendingIntent = getBroadcast(
                    context,
                    0,
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
            val pendingIntent = PendingIntent.getBroadcast(
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
            taskRepository.getTask(UUID.fromString(it))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { task ->
                    Log.d("onSuccess", "OK")
                    if (task.dueDate == null || abs(task.dueDate!!.time - Date().time) > 1000 * 120) return@doOnSuccess
                    try {
                        showNotification(
                            context,
                            task.title ?: "",
                            task.description ?: "",
                            task.id.toString(),
                        )
                        // set next reminder
                        taskRepository.getNextRemindTask(task)
                            .doOnSuccess { nextTask ->
                                setTaskReminder(nextTask, context)
                            }
                            .subscribe()

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                .subscribe()
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
//
//    private fun setNearest() {
//        taskRepository.
//    }
}