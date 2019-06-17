package com.warriorminds.restaurants.maps

import android.annotation.SuppressLint
import android.app.Activity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.warriorminds.restaurants.models.LatitudeLongitude
import javax.inject.Inject

class LocationProviderImpl @Inject constructor(): LocationProvider {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun init(activity: Activity) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
    }

    @SuppressLint("MissingPermission")
    override fun getLocation(isPermissionGranted: Boolean, onLocationRetrieved:(LatitudeLongitude) -> Unit, onError:(LatitudeLongitude) -> Unit) {
        val latitudeLongitude = LatitudeLongitude()
        if (isPermissionGranted) {
            fusedLocationClient.lastLocation.addOnCompleteListener {
                if (it.isComplete && it.isSuccessful && it.result != null) {
                    latitudeLongitude.latitude = it.result!!.latitude
                    latitudeLongitude.longitude = it.result!!.longitude
                    onLocationRetrieved(latitudeLongitude)
                } else {
                    onError(latitudeLongitude)
                }
            }
        } else {
            onLocationRetrieved(latitudeLongitude)
        }
    }
}