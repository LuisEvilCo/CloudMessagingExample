package example.messaging.cloud.luis.cloudmessagingexample.receiver

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.support.v4.content.WakefulBroadcastReceiver
import example.messaging.cloud.luis.cloudmessagingexample.services.GCMIntentService

class GCMBroadcastReceiver : WakefulBroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // execute when we receive a GCMIntent
        val componentName = ComponentName(
            context.packageName,
            GCMIntentService::class.java.name
        )
        startWakefulService(context, intent.setComponent(componentName))
        resultCode = Activity.RESULT_OK
    }
}