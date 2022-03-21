package com.jp_funda.todomind.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.NotificationCompat
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.repositories.task.TaskRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Reminder : BroadcastReceiver() {
    @Inject
    lateinit var taskRepository: TaskRepository

    companion object {
        const val CHANNEL_ID = "reminder_channel"
        const val CHANNEL_NAME = "reminder_channel"
    }

    override fun onReceive(context: Context, intent: Intent) {
        try {
            showNotification(
                context,
                intent.getStringExtra("title") ?: "title fail",
                intent.getStringExtra("desc") ?: "desc fail"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showNotification(context: Context, title: String, desc: String) {
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

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(desc)
            .setColor(Color(R.color.light_purple).toArgb())
            .setSmallIcon(R.drawable.ic_mind_map)
        manager.notify(1, builder.build())
    }

}