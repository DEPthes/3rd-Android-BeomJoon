package com.example.app.utils

import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

object PermissionUtils {

    fun checkPermissions(
        activity: Activity,
        permissions: Array<String>
    ): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(activity, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    fun requestPermissions(
        permissionLauncher: ActivityResultLauncher<Array<String>>,
        permissions: Array<String>
    ) {
        permissionLauncher.launch(permissions)
    }
}

