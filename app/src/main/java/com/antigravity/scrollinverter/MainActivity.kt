package com.antigravity.scrollinverter

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnOpenSettings = findViewById<Button>(R.id.btn_open_settings)
        btnOpenSettings.setOnClickListener {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
        }
    override fun onResume() {
        super.onResume()
        checkServiceStatus()
    }

    private fun checkServiceStatus() {
        val tvStatus = findViewById<TextView>(R.id.tv_status)
        if (isAccessibilityServiceEnabled()) {
             tvStatus.text = getString(R.string.service_status_active)
             tvStatus.setTextColor(getColor(android.R.color.holo_green_dark))
        } else {
             tvStatus.text = getString(R.string.service_status_inactive)
             tvStatus.setTextColor(getColor(android.R.color.holo_red_dark))
        }
    }

    private fun isAccessibilityServiceEnabled(): Boolean {
        val componentName = ComponentName(this, ScrollService::class.java)
        val activityManager = getSystemService(ACTIVITY_SERVICE) as android.app.ActivityManager
        // Note: isAccessibilityServiceEnabled is not a direct API on Activity.
        // Better to check Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        val enabledServices = Settings.Secure.getString(contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
        return enabledServices?.contains(componentName.flattenToString()) == true
    }
