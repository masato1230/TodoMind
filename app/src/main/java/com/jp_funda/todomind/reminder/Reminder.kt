package com.jp_funda.todomind.reminder

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

class Reminder : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        try {
            showNotification(context, "Make it Easy", "Compose Notification")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showNotification(context: Context, title: String, desc: String) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "message_channel"
        val channelName = "message_name"

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(desc)
            .setColor(Color(R.color.light_purple).toArgb())
            .setSmallIcon(R.drawable.ic_mind_map)

        manager.notify(1, builder.build())
    }
}