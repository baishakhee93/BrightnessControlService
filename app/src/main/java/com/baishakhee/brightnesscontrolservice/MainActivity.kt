package com.baishakhee.brightnesscontrolservice

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.content.Intent
import android.provider.Settings
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private val WRITE_SETTINGS_PERMISSION_REQUEST = 123
    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private var serviceIntent: Intent? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startButton = findViewById(R.id.startButton)
        stopButton = findViewById(R.id.stopButton)
        startButton.setOnClickListener {
            startService(serviceIntent)
        }

        stopButton.setOnClickListener {
            stopService(serviceIntent)
        }
        serviceIntent = Intent(this, BrightnessControlService::class.java)

        // Check if the app has WRITE_SETTINGS permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_SETTINGS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Request the permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_SETTINGS),
                WRITE_SETTINGS_PERMISSION_REQUEST
            )
        } else {

            // The permission is already granted, so start the service
            startService(serviceIntent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == WRITE_SETTINGS_PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start the service
                startService(serviceIntent)
                Log.d("ControlService", "onRequestPermissionsResult..0...... permission.")

            } else {
                // Permission denied, inform the user to grant it manually
                informUserToGrantPermissionManually()
                Log.d("ControlService", "onRequestPermissionsResult........ permission.")

            }
        }
    }

    private fun informUserToGrantPermissionManually() {
        // Display a dialog or message to guide the user to grant the WRITE_SETTINGS permission manually in system settings.
        val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
        startActivity(intent)
    }

    private fun startService() {
        Log.d("ControlService", "startService....00.... permission.")
         serviceIntent = Intent(this, BrightnessControlService::class.java)

        // Start the background service
    /*    val serviceIntent = Intent(this, BrightnessControlService::class.java)
        startService(serviceIntent)*/
        Log.d("ControlService", "startService........ permission.")

    }

    override fun onResume() {
        super.onResume()
        // The permission is already granted, so start the service
        startService(serviceIntent)
     /*   // Check if the app has WRITE_SETTINGS permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_SETTINGS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Request the permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_SETTINGS),
                WRITE_SETTINGS_PERMISSION_REQUEST
            )
        } else {
            // The permission is already granted, so start the service
            startService()
        }*/
    }
}
