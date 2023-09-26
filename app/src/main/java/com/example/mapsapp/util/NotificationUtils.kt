package com.example.mapsapp.util

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.example.mapsapp.ui.PointReceiverService
import com.example.mapsapp.R
import com.example.mapsapp.domain.entity.MapPoint

fun PointReceiverService.buildForegroundNotification(): Notification {
    val channelId = packageName
    val channelName = "TCP Background Service"

    val notificationChannel = NotificationChannel(
        channelId, channelName, NotificationManager.IMPORTANCE_NONE
    )
    val notificationManager =
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
    notificationManager.createNotificationChannel(notificationChannel)
    val notificationBuilder = NotificationCompat.Builder(this, channelId)

    return notificationBuilder.setOngoing(true)
        .setSmallIcon(R.drawable.ic_receiver)
        .setContentTitle(getString(R.string.tcp_running_background))
        .setPriority(NotificationManager.IMPORTANCE_MIN)
        .setCategory(Notification.CATEGORY_SERVICE).build()
}

fun PointReceiverService.buildPointReceivedNotification(point: MapPoint): Notification {
    val channelId = packageName
    val channelName = "Receiving Point"

    val notificationChannel = NotificationChannel(
        channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT
    )
    val notificationManager =
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
    notificationManager.createNotificationChannel(notificationChannel)
    val notificationBuilder = NotificationCompat.Builder(this, channelId)

    val latitude = String.format("%.6f", point.latitude)
    val longitude = String.format("%.6f", point.longitude)

    return notificationBuilder
        .setSmallIcon(R.drawable.ic_new_point)
        .setContentTitle(getString(R.string.new_point_received))
        .setContentText(
            getString(
                R.string.a_new_point_with_coordinates_has_been_added,
                latitude,
                longitude
            )
        )
        .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
        .setCategory(Notification.CATEGORY_SERVICE).build()
}

fun checkSelfPermission(context: Context) {
    if (ActivityCompat.checkSelfPermission(
            context, Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return
    }
}