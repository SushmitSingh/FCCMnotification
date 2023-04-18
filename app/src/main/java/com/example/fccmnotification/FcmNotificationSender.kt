package com.example.fccmnotification

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class FcmNotificationSender {
    private val FCM_API = "https://fcm.googleapis.com/fcm/send"
    private val serverKey =
        "AAAAMVPC3TE:APA91bFRpyrh_Dv0HayOrqTvccOSwe0uK7hTZGZy05r4XYBZovuufLF9C7harxBE3pb8HXu8mLDlO9HlHMlXsD7ObB7fzCL8Cpb3kI4pqysG9GdgByMWQm7vHAAOsUGbAbzFgYUOxAeb" // Replace with your actual FCM server key
    private val contentType = "application/json"
    private val client = OkHttpClient()

    fun sendNotificationToTopic(topic: String, title: String, message: String) {
        val json = JSONObject()
        val notificationJson = JSONObject()

        notificationJson.put("title", title)
        notificationJson.put("body", message)

        json.put("to", "/topics/$topic") // Set the topic to send the notification to
        json.put("notification", notificationJson)

        val body = json.toString().toRequestBody(contentType.toMediaType())
        val request =
            Request.Builder().url(FCM_API).addHeader("Authorization", "key=$serverKey").post(body)
                .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                try {
                    val responseData = response.body?.string()
                    if (response.isSuccessful && responseData != null) {
                        // Handle success
                        println("Notification sent successfully to topic: $topic")
                    } else {
                        // Handle error
                        println("Failed to send notification to topic: $topic, error: $responseData")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                e.printStackTrace()
            }
        })
    }
}
