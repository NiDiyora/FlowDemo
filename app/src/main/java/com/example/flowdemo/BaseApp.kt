package com.example.flowdemo

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import com.droidnet.DroidNet
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApp : Application()/*,Configuration.Provider*/ {
//    override fun getWorkManagerConfiguration() =
//        Configuration.Builder()
//            .setMinimumLoggingLevel(Log.VERBOSE)
//            .build()

    override fun onCreate() {
        super.onCreate()
        DroidNet.init(this)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        DroidNet.getInstance().removeAllInternetConnectivityChangeListeners();

    }
}