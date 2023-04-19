package com.example.fccmnotification.service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.fccmnotification.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Handle incoming FCM message
        val title = remoteMessage.notification?.title
        val body = remoteMessage.notification?.body
        if (title != null && body != null) {
            sendNotification(title, body)
        }
    }

    private fun sendNotification(title: String, message: String) {
        // Create a notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "channel_id"
            val channelName = "Channel Name"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, importance)
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Build the notification
        val builder =
            NotificationCompat.Builder(this, "channel_id").setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title).setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH).setAutoCancel(true)

        // Show the notification
        val notificationManager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationManager.notify(0, builder.build())
    }

    override fun onNewToken(token: String) {
        // Handle token refresh or registration
        // You can send the updated token to your server or perform any other logic here
    }
}
