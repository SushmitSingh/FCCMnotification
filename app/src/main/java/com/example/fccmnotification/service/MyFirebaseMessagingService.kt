package com.example.fccmnotification.service

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.fccmnotification.R
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Handle incoming notification
        Log.d(TAG, "Message data payload: ${remoteMessage.data}")

        // Extract notification title and body
        val title = remoteMessage.data["title"]
        val body = remoteMessage.data["body"]

        // Trigger action to display notification in your app
        // You can customize the display logic according to your requirements
        displayNotification(this, title, body)
    }


    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token")
        // Subscribe to the desired topic
        FirebaseMessaging.getInstance().subscribeToTopic("All")
        Log.d(TAG, "Subscribed to topic: All")
    }


    private fun displayNotification(context: Context, title: String?, body: String?) {
        // Build the notification
        val notificationBuilder =
            NotificationCompat.Builder(context).setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title).setContentText(body).setAutoCancel(true)

        // Display the notification
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())
    }

    companion object {
        private const val TAG = "com.example.fccmnotification.service.MyFirebaseMessagingService"
    }
}
