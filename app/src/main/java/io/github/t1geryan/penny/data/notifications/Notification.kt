package io.github.t1geryan.penny.data.notifications

import androidx.annotation.DrawableRes

data class Notification(
    @field:DrawableRes val iconResId: Int,
    val title: String,
    val description: String,
)
