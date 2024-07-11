package com.example.twoforyou_botcscript.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionUtil {

    fun requestPermission(requestPermissions: Array<String>, context: Context) {

        val PERMISSIONS_REQUEST_CODE = 100

        val activity = context.getActivityOrNull()!!
        val permissionCheck =
            ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                ActivityCompat.requestPermissions(
                    activity,
                    requestPermissions,
                    PERMISSIONS_REQUEST_CODE
                )
            } else {
                ActivityCompat.requestPermissions(
                    activity,
                    requestPermissions,
                    PERMISSIONS_REQUEST_CODE
                )
            }
        }
    }

    private fun Context.getActivityOrNull(): Activity? {
        var context = this
        while (context is ContextWrapper) {
            if (context is Activity) return context
            context = context.baseContext
        }

        return null
    }

}