package example.messaging.cloud.luis.cloudmessagingexample.services

import com.google.firebase.iid.FirebaseInstanceIdService
import example.messaging.cloud.luis.cloudmessagingexample.utils.GCMUtils


class GCMIDListenerService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        GCMUtils.registerDevice(this) // senderId is not needed since we have google-services.json
    }
}