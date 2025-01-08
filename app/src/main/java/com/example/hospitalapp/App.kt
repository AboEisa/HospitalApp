package com.example.hospitalapp

import android.app.Application
import android.os.StrictMode
import dagger.hilt.android.HiltAndroidApp
import de.hdodenhof.circleimageview.BuildConfig

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects() // Detect leaked SQLite cursors
                    .detectLeakedClosableObjects() // Detect leaked Closeable objects
                    .detectLeakedRegistrationObjects() // Detect leaked BroadcastReceiver or ServiceConnection
                    .penaltyLog() // Log violations to Logcat
                    .build()
            )

            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll() // Detect all thread violations
                    .penaltyLog() // Log violations to Logcat
                    .build()
            )
        }
    }


}