package com.divineiq.app.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.divineiq.app.R

/**
 * Creates DivineIQ's notification channel and posts notifications through
 * it. Channel creation is a no-op below API 26, which is fine since
 * [com.divineiq.app.DivineIQApplication] calls [ensureChannel] on every
 * launch regardless of OS version.
 *
 * No notification is ever posted by the app today — this is scaffolding
 * for the future quiz-reminder / new-content features — but the channel
 * itself is real, registered infrastructure rather than a stub.
 */
object NotificationHelper {

    const val CHANNEL_ID = "divineiq_general"

    fun ensureChannel(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val channel = NotificationChannel(
            CHANNEL_ID,
            context.getString(R.string.notification_channel_name),
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = context.getString(R.string.notification_channel_description)
        }

        val manager = context.getSystemService(NotificationManager::class.java)
        manager?.createNotificationChannel(channel)
    }

    /**
     * Posts a notification, first checking POST_NOTIFICATIONS on API 33+
     * (the permission doesn't exist below that). The check is verified
     * manually here, so Lint's static [MissingPermission] analysis is
     * suppressed rather than worked around with a less readable structure.
     */
    @SuppressLint("MissingPermission")
    fun notify(context: Context, notificationId: Int, title: String, message: String) {
        val hasPermission = Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

        if (!hasPermission) return

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notes)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(context).notify(notificationId, notification)
    }
}
