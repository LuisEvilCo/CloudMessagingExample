package example.messaging.cloud.luis.cloudmessagingexample.services

import android.os.Bundle
import com.google.android.gms.gcm.GcmListenerService
import example.messaging.cloud.luis.cloudmessagingexample.helpers.NotificationsHelper
import java.util.*

class GCMIntentService: GcmListenerService() {

    override fun onMessageReceived(from: String, data: Bundle) {
        val mNotificationHelper = NotificationsHelper(this)

        val payload = data["message"]?.let { it -> it as String } ?: "no message found in payload"

        val notification = mNotificationHelper.getNotificationDefault(
            "Cloud Messaging Example",
            payload
        )

        mNotificationHelper.notify(Random().nextInt() , notification)
    }
}