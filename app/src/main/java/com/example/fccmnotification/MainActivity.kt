package com.example.fccmnotification

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Initialize Firebase in your app
        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().isAutoInitEnabled = true


        val sendNotificationButton: Button = findViewById(R.id.sendNotificationButton)

        // Set click listener for the button
        sendNotificationButton.setOnClickListener {
            // Method to send a notification to the app
            sendNotificationToTopic(
                "All",
                "Hello", "This is a test notification"
            )
        }
    }

    private fun sendNotificationToTopic(topic: String, title: String, body: String) {
        val fcmNotificationSender = FcmNotificationSender()
        fcmNotificationSender.sendNotificationToTopic(topic, title, body)
    }

}
