package example.messaging.cloud.luis.cloudmessagingexample.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.AsyncTask
import android.preference.PreferenceManager
import android.text.TextUtils
import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.iid.FirebaseInstanceId
import java.io.IOException


object GCMUtils {
    // AsyncTask is only appropriate for when we need to do work on the main
    // application thread, and that's not correct for calling from a service since we are
    // leaking a strong reference to context
    // look into : ScheduleExecutorService
    @SuppressLint("StaticFieldLeak")
    fun registerDevice(context: Context) {

        object : AsyncTask<Void, Void, String>() {

            override fun doInBackground(vararg voids: Void): String? {
                //val gcm = getInstance(context)
                return try {
                    val registrationToken = FirebaseInstanceId.getInstance().token
                    Log.d("FCMUTILS",registrationToken )
                    registrationToken
                } catch (e: IOException) {
                    e.printStackTrace()
                    null
                }

            }

            override fun onPostExecute(result: String) {
                if (!TextUtils.isEmpty(result)) {

                    // store registration id to preference
                    storeRegistrationId(context, result)

                    // send registration id to back-end
                    //sendRegistrationIdToBackend(context, spiceManager, result)
                } else {
                    Log.i("GCMUTILS","There is problem with device registration to GCM server")
                }
            }
        }.execute()
    }

    /**
     * Gets SharedPreferences for GCM (Google Cloud Messaging).
     * @return SharedPreferences object
     */
    private fun getFCMSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
        //return Application().applicationContext.getSharedPreferences("GCM", Context.MODE_PRIVATE)
    }

    private fun storeRegistrationId(context: Context, registrationId: String) {
        val preferences = getFCMSharedPreferences(context)
        // store registration id and current version of application
        val editor = preferences.edit()
        editor.putString("token", registrationId)
        //editor.putInt(PREF_APP_VERSION, Integer.parseInt(Utils.getCurrentAppVersion(context)))
        editor.apply()
    }

    private fun isServiceAvailable(context: Context) : Boolean {
        val googleApi = GoogleApiAvailability.getInstance()
        val resultCode = googleApi.isGooglePlayServicesAvailable(context)
        return resultCode == ConnectionResult.SUCCESS
    }

    private fun getResgistrationId(context: Context) : String {
        val preferences = getFCMSharedPreferences(context
        )
        return preferences.getString("token", "")
    }

    private fun isDeviceRegistered(context: Context) : Boolean {
        return !TextUtils.isEmpty(getResgistrationId(context))
    }


    fun registerPush(context: Context){
        if (isServiceAvailable(context)) {
            if(isDeviceRegistered(context)) {
                Log.i("GCMUTILS", "already register")
                Log.i("GCMUTILS", getResgistrationId(context))
            } else {
                registerDevice(context)
            }
        } else {
            Log.i("GCMUTILS", "Cant register device, google service is not available ")
        }
    }

}