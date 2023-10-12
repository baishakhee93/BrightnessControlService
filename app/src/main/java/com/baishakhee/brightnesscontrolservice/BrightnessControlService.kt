package com.baishakhee.brightnesscontrolservice

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.provider.Settings
import android.util.Log

class BrightnessControlService : Service() {
    private val handler = Handler()
    private val brightnessValues = intArrayOf(77, 128, 255) // 30%, 50%, 100%
    private var currentIndex = 0

    private val brightnessChangeRunnable = object : Runnable {
        override fun run() {
            try {
                // Adjust the screen brightness using the Settings API
                Settings.System.putInt(
                    contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS,
                    brightnessValues[currentIndex]
                )
                currentIndex = (currentIndex + 1) % brightnessValues.size
                Log.d("ControlService", "run........ permission.")

                println("Britness....."+currentIndex)
            } catch (e: SecurityException) {
                // Handle permission issues
                Log.e("ControlService", "Permission denied. Please grant the WRITE_SETTINGS permission.")

            }
            Log.d("ControlService", "WRITE_SETTINGS permission.")

            // Schedule the next brightness change after 30 seconds
            handler.postDelayed(this, 30000)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Start the initial brightness change
        handler.post(brightnessChangeRunnable)
        Log.d("ControlService", "onStartCommand............. permission.")

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ControlService", "onDestroy............. permission.")

        // Remove the callbacks to stop the service when it's destroyed
        handler.removeCallbacks(brightnessChangeRunnable)
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d("ControlService", "onBind............. permission.")

        return null
    }
}
