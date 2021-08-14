package com.ruzzante.recursosnativos.util

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.ruzzante.recursosnativos.MapsActivity

class AccessRequest {
    companion object{
        val REQUEST_STORAGE = 2
        val IMAGE_PICK_CODE = 3
        val REQUEST_CAMERA = 4
        val CAMERA_PHOTO_CODE = 5
        val REQUEST_WRITE_STORAGE = 6
        val LOCATION_PERMISSION_REQUEST_CODE = 7

        fun requestLocationPermission(activity: MapsActivity){
            ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        fun hasLocationPermission(context: Context):Boolean{
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                return false
            return true
        }
    }


}