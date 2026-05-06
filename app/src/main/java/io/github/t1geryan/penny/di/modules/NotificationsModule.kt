package io.github.t1geryan.penny.di.modules

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.t1geryan.penny.data.notifications.PennyNotificationsManager
import io.github.t1geryan.penny.data.notifications.PennyNotificationsManagerImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationsModule {

    @Provides
    @Singleton
    fun provideSystemNotificationManagerCompat(
        @ApplicationContext context: Context,
    ): NotificationManagerCompat = NotificationManagerCompat.from(context)

    @Provides
    @Singleton
    fun providePennyNotificationsManager(
        @ApplicationContext context: Context,
        notificationManagerCompat: NotificationManagerCompat,
    ): PennyNotificationsManager = PennyNotificationsManagerImpl(context, notificationManagerCompat)
}
