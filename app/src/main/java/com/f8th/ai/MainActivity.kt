package com.f8th.ai

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.f8th.ai.service.SwitchVisibilityService

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAccessibilityPermission()
        finishAndRemoveTask()
    }

    fun checkAccessibilityPermission() {
        var accessEnabled = 0
        try {
            accessEnabled =
                Settings.Secure.getInt(this.contentResolver, Settings.Secure.ACCESSIBILITY_ENABLED)
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
        }
        if (accessEnabled == 0) {
            //if not construct intent to request permission  */
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            //request permission via start activity for result  */
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(receiver, getFilter())
    }

    private val receiver = object : BroadcastReceiver(){
        override fun onReceive(p0: Context?, intent: Intent?) {
            if (intent!=null && intent.action?.equals(App.SERVICE_STARTED)!!){
                Log.d(TAG, "onReceive: -> hide")
                this@MainActivity.finishAndRemoveTask()
            }
        }
    }

    private fun getFilter(): IntentFilter {
        val filter = IntentFilter()
        filter.addAction(App.SERVICE_STARTED)
        return filter
    }

}