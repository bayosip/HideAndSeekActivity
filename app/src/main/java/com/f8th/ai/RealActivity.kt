package com.f8th.ai

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent

class RealActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_real)
    }

    override fun onResume() {
        super.onResume()
        App.activityResumed()
        registerReceiver(receiver, getFilter())
    }

    override fun onPause() {
        super.onPause()
        App.activityPaused()
    }

    override fun onBackPressed() {
        App.activityPaused()
        super.onBackPressed()
    }

    private val receiver = object :BroadcastReceiver(){
        override fun onReceive(p0: Context?, intent: Intent?) {
            if (intent!=null && intent.action?.equals(App.ACTION_HIDE)!!){
                onBackPressed()
            }
        }
    }

    private fun getFilter():IntentFilter{
        val filter = IntentFilter()
        filter.addAction(App.ACTION_HIDE)
        return filter
    }
}