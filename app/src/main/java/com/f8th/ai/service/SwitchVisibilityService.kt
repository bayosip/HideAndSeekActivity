package com.f8th.ai.service

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.util.Log
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import com.f8th.ai.App
import com.f8th.ai.MainActivity
import com.f8th.ai.RealActivity
private const val MAGIC_NUM =3
private const val RESET = -1
class SwitchVisibilityService: AccessibilityService() {
    private  val TAG = "SwitchVisibilityService"
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        sendBroadcast(Intent(App.SERVICE_STARTED))
        return START_NOT_STICKY
    }

    override fun onAccessibilityEvent(p0: AccessibilityEvent?) {
        //TODO("Not yet implemented")
    }

    override fun onKeyEvent(event: KeyEvent): Boolean {
        Log.d(TAG, "Key pressed via accessibility is: " + event.getKeyCode())
        if (event.keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
            App.counter++
            if (App.counter == MAGIC_NUM) {
                if (App.isActivityVisible()){//check if activity is visible
                    sendBroadcast(Intent(App.ACTION_HIDE))//send broadcast to hide activity
                }else {
                    val intent = Intent(this@SwitchVisibilityService, RealActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                App.counter= RESET
            }
        }else{
            App.counter = RESET //if any other key is pressed except volume down, counter resets
        }
        return true
    }


    override fun onInterrupt() {
    }
}