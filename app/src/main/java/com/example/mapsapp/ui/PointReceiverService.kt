package com.example.mapsapp.ui

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationManagerCompat
import com.example.mapsapp.data.remote.client.PointReceiverClient
import com.example.mapsapp.domain.entity.MapPoint
import com.example.mapsapp.domain.usecase.SaveMapPointUseCase
import com.example.mapsapp.util.buildForegroundNotification
import com.example.mapsapp.util.buildPointReceivedNotification
import com.example.mapsapp.util.checkSelfPermission
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PointReceiverService : Service() {

    @Inject lateinit var saveMapPointUseCase: SaveMapPointUseCase

    @Inject lateinit var pointReceiverClient: PointReceiverClient

    @Inject lateinit var coroutineScope: CoroutineScope

    companion object {
        const val FOREGROUND_CHANNEL_ID = 1
        const val POINT_CHANNEL_ID = 2
    }

    override fun onCreate() {
        super.onCreate()
        startOnForeground()
        pointReceiverClient.start(::handleReceivedMapPoint)
    }

    private fun startOnForeground() {
        val notification = buildForegroundNotification()
        startForeground(FOREGROUND_CHANNEL_ID, notification)
    }

    private fun handleReceivedMapPoint(mapPoint: MapPoint) {

        coroutineScope.launch { saveMapPointUseCase.invoke(mapPoint) }

        val notification = buildPointReceivedNotification(mapPoint)
        val notificationManager = NotificationManagerCompat.from(this)

        checkSelfPermission(this)
        notificationManager.notify(POINT_CHANNEL_ID, notification)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        pointReceiverClient.stop()
        super.onDestroy()
    }
}

