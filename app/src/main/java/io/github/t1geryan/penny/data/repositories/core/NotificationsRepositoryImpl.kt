package io.github.t1geryan.penny.data.repositories.core

import android.content.Context
import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.domain.repositories.NotificationsRepository
import io.github.t1geryan.penny.R
import io.github.t1geryan.penny.data.notifications.Notification
import io.github.t1geryan.penny.data.notifications.PennyNotificationsManager
import javax.inject.Inject

class NotificationsRepositoryImpl @Inject constructor(
    private val context: Context,
    private val pennyNotificationsManager: PennyNotificationsManager,
) : NotificationsRepository {

    override fun sendLimitExceedNotification(category: Category) {
        pennyNotificationsManager.sendNotification(
            Notification(
                iconResId = android.R.drawable.ic_dialog_alert,
                title = context.getString(R.string.notification_limit_exceeded_title),
                description = context.getString(R.string.notification_limit_exceeded_message, category.name),
            ),
        )
    }
}
