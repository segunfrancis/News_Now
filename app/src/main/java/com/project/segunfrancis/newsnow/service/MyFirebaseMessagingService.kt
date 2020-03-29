package com.project.segunfrancis.newsnow.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.project.segunfrancis.newsnow.R

/**
 * Created by SegunFrancis
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        createNotificationChannel()

        val args = Bundle()
        args.putString("myarg", "www.facebook.com")
        val pendingIntent = NavDeepLinkBuilder(this)
            .setGraph(R.navigation.main_navigation)
            .setDestination(R.id.webViewFragment)
            .setArguments(args)
            .createPendingIntent()

        val notificationBuilder = NotificationCompat.Builder(this, "channel_id")
            .setContentTitle(remoteMessage.notification?.title)
            .setContentText(remoteMessage.notification?.body)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(NotificationCompat.BigTextStyle())
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setSmallIcon(R.drawable.letter_n)
            .setAutoCancel(true)

        val notificationManagerCompat = NotificationManagerCompat.from(this)
        notificationManagerCompat.notify(0, notificationBuilder.build())
    }

    private fun createNotificationChannel() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create Notification Channel
            val channel = NotificationChannel(
                "CHANNEL_ID",
                "my_notification_channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "channel description"

            notificationManager.createNotificationChannel(channel)
        }
    }
}