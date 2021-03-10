package com.example.customnotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    private var CHANNEL_ID = "notification"
    lateinit var notificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationManager  = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel()
        val notificationLayout = RemoteViews(packageName, R.layout.custom_notification)
        val intent = Intent(this, NewActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Custom Notification")
                .setSmallIcon(R.drawable.ic_baseline_notifications_none_24)
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationLayout)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setDeleteIntent(pendingIntent)
                .setAutoCancel(true)

        val notifyBtn = findViewById<Button>(R.id.notify_btn)
        notifyBtn.setOnClickListener {
            with(NotificationManagerCompat.from(this)) {
                notify(0, builder.build())
            }
        }
    }

    override fun onStop() {
        super.onStop()
        notificationManager.cancel(0)
    }

    override fun onDestroy() {
        super.onDestroy()
        notificationManager.cancel(0)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "App Notification"
            val descriptionText = "Description of Notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }
    }
}
