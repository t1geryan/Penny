package io.github.t1geryan.penny.data.notifications

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import io.github.t1geryan.penny.R
import javax.inject.Inject
import kotlin.time.Clock

interface PennyNotificationsManager {

    fun sendNotification(notification: Notification)
}

class PennyNotificationsManagerImpl @Inject constructor(
    private val context: Context,
    private val notificationManager: NotificationManagerCompat,
) : PennyNotificationsManager {

    companion object {
        private const val DEFAULT_CHANNEL_ID = "DEFAULT_CHANNEL_ID"
    }

    init {
        createNotificationsChannel()
    }

    private fun createNotificationsChannel() {
        val name = context.getString(R.string.app_name)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(DEFAULT_CHANNEL_ID, name, importance)
        notificationManager.createNotificationChannel(channel)
    }

    @SuppressLint("MissingPermission")
    override fun sendNotification(notification: Notification) {
        val builder = NotificationCompat.Builder(context, DEFAULT_CHANNEL_ID)
            .setSmallIcon(notification.iconResId)
            .setContentTitle(notification.title)
            .setContentText(notification.description)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        notificationManager.notify(generateId(), builder.build())
    }

    private fun generateId() = Clock.System.now().epochSeconds.toInt()
}
