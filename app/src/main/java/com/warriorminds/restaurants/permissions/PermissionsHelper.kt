package com.warriorminds.restaurants.permissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

const val LOCATION_PERMISSION_REQUEST_CODE = 2000

fun checkLocationPermissionsGranted(context: Context): Boolean {
    val coarseLocation = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
    val fineLocation = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)

    return coarseLocation == PackageManager.PERMISSION_GRANTED && fineLocation == PackageManager.PERMISSION_GRANTED
}

fun requestLocationPermissions(fragment: Fragment) {
    fragment.requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
}

fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray, onAccepted: () -> Unit, onDenied: () -> Unit) {
    if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.size == 2 &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
        onAccepted()
    } else {
        onDenied()
    }
}