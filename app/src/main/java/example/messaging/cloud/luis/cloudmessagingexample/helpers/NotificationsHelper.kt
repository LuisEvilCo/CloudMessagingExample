package example.messaging.cloud.luis.cloudmessagingexample.helpers

import android.app.*
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Color
import android.os.Build
import example.messaging.cloud.luis.cloudmessagingexample.MainActivity

class NotificationsHelper(context : Context) : ContextWrapper(context) {
    companion object {
        private const val CHANNEL_ID = "default_channel"
    }

    private val mNotificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    // Constructor.Kt
    init {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val defaultChannel = NotificationChannel(
                CHANNEL_ID,
                "Default Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            defaultChannel.lightColor = Color.MAGENTA
            defaultChannel.setShowBadge(true)

            mNotificationManager.createNotificationChannel(defaultChannel)
        }
    }

    /**
     * Send a notification
     *
     * @param id                The ID of the notification
     * *
     * @param notification      The notification object
     */
    fun notify(id: Int, notification: Notification.Builder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mNotificationManager.notify(id, notification.build())
        } else{
            mNotificationManager.notify(id, notification.notification)
        }
    }

    /**
     *  Providing the builder rather than the notification can be useful for making
     *  notification changes

     *  @param title the title of the notification
     *  *
     *  @param body the body of the notification
     *  *
     *  @return A Notification.Builder configured with the selected channel and details
     */
    fun getNotificationDefault(title: String, body: String ): Notification.Builder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(applicationContext, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(smallIcon)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
        } else {
            Notification.Builder(applicationContext)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(smallIcon)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
        }
    }

    /**
     * Create a PendingIntent for opening up the MainActivity when the notification is pressed

     *  @return A PendingIntent that opens the MainActivity
     *
     */

    private // The stack builder object will contain an artificial back stack for the
    // started Activity
    // This ensures that navigating backward from the Activity leads out of
    // your application to the Home screen.
    // Adds the back stack for the Intent (but not the intent itself)
    // Adds the Intent that starts the Activity to the top of the stack
    val pendingIntent : PendingIntent
        get() {
            val openMainIntent = Intent(this, MainActivity::class.java)

            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                val stackBuilder = TaskStackBuilder.create(this)
                stackBuilder.addParentStack(MainActivity::class.java)
                stackBuilder.addNextIntent(openMainIntent)
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT)
            } else {
                val stackBuilder = android.support.v4.app.TaskStackBuilder.create(this)
                stackBuilder.addParentStack(MainActivity::class.java)
                stackBuilder.addNextIntent(openMainIntent)
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT)
            }
        }

    /**
     *  Get the small icon for this app

     * @return The small icon resource id
     */

    private val smallIcon : Int
        get() = android.R.drawable.stat_notify_chat
}