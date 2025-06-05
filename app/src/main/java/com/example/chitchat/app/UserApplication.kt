package com.example.chitchat.app

import android.app.Application
import androidx.activity.enableEdgeToEdge
import com.cloudinary.android.MediaManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class UserApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Cloudinary config
        val config: HashMap<String, String> = hashMapOf(
            "cloud_name" to "dxwlx1aaw",
            "api_key" to "389748989356581",
            "api_secret" to "your_api_secret_here" // Add this if required
        )

        // initialize
        MediaManager.init(this, config)
    }
}
