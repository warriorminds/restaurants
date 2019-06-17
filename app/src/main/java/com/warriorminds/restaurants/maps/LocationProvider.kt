package com.warriorminds.restaurants.maps

import android.app.Activity
import com.warriorminds.restaurants.models.LatitudeLongitude

interface LocationProvider {
    fun init(activity: Activity)

    fun getLocation(isPermissionGranted: Boolean, onLocationRetrieved:(LatitudeLongitude) -> Unit, onError:(LatitudeLongitude) -> Unit)
}