package io.github.t1geryan.domain.repositories

import io.github.t1geryan.domain.models.Category

interface NotificationsRepository {

    fun sendLimitExceedNotification(category: Category)
}
