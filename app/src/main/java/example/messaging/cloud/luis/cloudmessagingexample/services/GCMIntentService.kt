package example.messaging.cloud.luis.cloudmessagingexample.services

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import example.messaging.cloud.luis.cloudmessagingexample.helpers.NotificationsHelper
import java.util.*

class GCMIntentService: FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        val from: String? = message.from
        val data = message.data

        val mNotificationsHelper = NotificationsHelper(this)

        val payload = data["message"] ?: "no message found in payload"

        val notification = mNotificationsHelper.getNotificationDefault(
            "Cloud Messaging Example",
            payload
        )

        mNotificationsHelper.notify(Random().nextInt(), notification)
    }
}