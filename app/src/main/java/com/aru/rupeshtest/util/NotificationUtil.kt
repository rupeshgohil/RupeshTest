@file:Suppress("DEPRECATION")

package com.aru.rupeshtest.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.aru.rupeshtest.view.MainActivity
import com.aru.rupeshtest.R
object NotificationUtil{
    private val TAG = "Util"
    var notificationManager: NotificationManager? = null
    val icon =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) R.drawable.ic_clocknewsvg else R.drawable.ic_clocknewsvg
    fun setNotification(hms: String, mainActivity: MainActivity) {
        val pendingIntent = PendingIntent.getActivity(
            mainActivity, 0, mainActivity.intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val notificationBuilder = NotificationCompat.Builder(mainActivity)
            .setSmallIcon(icon)
            .setContentTitle(mainActivity.resources.getString(R.string.time_start))
            .setContentText(hms)
            .setAutoCancel(true)
            .setUsesChronometer(true)
            .setContentIntent(pendingIntent)
        notificationManager = mainActivity.applicationContext.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        /*set Notification channel for default  notification */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = mainActivity.resources.getString(R.string.default_notification_channel_id)
            val channel =
                NotificationChannel(channelId, "test", NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = "test"
            notificationManager!!.createNotificationChannel(channel)
            notificationBuilder.setChannelId(channelId)
        }
        if (null != notificationManager) {
            notificationManager!!.notify(1, notificationBuilder.build())
        }
    }

    fun CancelNotification(mContext: Context) {
        notificationManager!!.cancel(1)
    }

}

