package example.messaging.cloud.luis.cloudmessagingexample.services

import com.google.android.gms.iid.InstanceIDListenerService
import example.messaging.cloud.luis.cloudmessagingexample.utils.GCMUtils

class GCMIDListenerService : InstanceIDListenerService() {

    override fun onTokenRefresh() {
        GCMUtils.registerDevice(this, "")
    }
}