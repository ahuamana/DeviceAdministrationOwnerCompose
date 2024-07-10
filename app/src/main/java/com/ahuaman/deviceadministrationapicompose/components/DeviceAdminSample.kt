package com.ahuaman.deviceadministrationapicompose.components

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class DeviceAdminSample :DeviceAdminReceiver() {


    override fun onEnabled(context: Context, intent: Intent) {
       Log.d("DeviceAdminSample", "Device Admin Enabled")
    }

    override fun onDisabled(context: Context, intent: Intent) {
        Log.d("DeviceAdminSample", "Device Admin Disabled")
    }
}