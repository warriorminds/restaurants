package com.warriorminds.restaurants.maps

import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLngBounds
import com.warriorminds.restaurants.models.LatitudeLongitude
import com.warriorminds.restaurants.models.Venue

interface MapProvider {
    /**
     * Method to be called when the map is created.
     *
     * @param savedInstanceState
     * @param view
     */
    fun onCreate(savedInstanceState: Bundle?, view: View)

    /**
     * Method to be called when the map container goes to onSaveInstance State.
     *
     * @param outState
     */
    fun onSaveInstanceState(outState: Bundle)

    /**
     * Method to be called when the map container goes to on Pause.
     */
    fun onPause()

    /**
     * Method to be called when the map container goes to onResume.
     */
    fun onResume()

    /**
     * Method to be called when the map container goes to onDestroy.
     */
    fun onDestroy()

    /**
     * Method to be called when the map container goes to onLowMemory.
     */
    fun onLowMemory()

    /**
     * Method to be called to move the camera to the specified bounds.
     *
     * @param latLongBounds Bounds to move the camera to.
     */
    fun moveCamera(latLongBounds: LatLngBounds)

    /**
     * Sets the venues list to be shown in the map.
     *
     * @param venues list of venues
     */
    fun setVenues(venues: List<Venue>, latitudeLongitude: LatitudeLongitude)

    /**
     * Sets the info window adapter to be used for the bubble view for each pin.
     *
     * @param adapter InfoWindowAdapter implementation to be used.
     */
    fun setInfoWindowAdapter(adapter: GoogleMap.InfoWindowAdapter)

    /**
     * Method that shows the info window for the last selected pin.
     */
    fun showInfoWindow()

    fun setNavigationListener(listener: GoogleMapProvider.DetailsNavigation)
}
