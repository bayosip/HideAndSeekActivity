package com.f8th.ai

import android.app.Application

class App:Application() {

    companion object{

        fun isActivityVisible(): Boolean {
            return activityVisible
        }

        fun activityResumed() {
            activityVisible = true
        }

        fun activityPaused() {
            activityVisible = false
        }

        private var activityVisible = false

        val ACTION_HIDE = "hide_self"
        val SERVICE_STARTED = "started_service"

        @Volatile
        var counter = -1
    }
}