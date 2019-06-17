package com.warriorminds.restaurants.maps

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.warriorminds.restaurants.R
import com.warriorminds.restaurants.models.LatitudeLongitude
import com.warriorminds.restaurants.models.Venue
import javax.inject.Inject

class GoogleMapProvider @Inject constructor(private val context: Context) : MapProvider, GoogleMap.OnCameraIdleListener,
    GoogleMap.OnCameraMoveStartedListener {

    private var cameraHasStartedMoving: Boolean = false
    private var mapView: MapView? = null
    private var selectedMarker: Marker? = null
    private var navigationListener: DetailsNavigation? = null

    override fun onCreate(savedInstanceState: Bundle?, view: View) {
        val mapContainer = view.findViewById<FrameLayout>(R.id.map_container)
        mapView = mapContainer.findViewById(R.id.map)

        mapView?.let {
            it.onCreate(savedInstanceState)
            it.getMapAsync { googleMap ->
                googleMap.uiSettings.isMapToolbarEnabled = false
                val padding = context.resources.getDimension(R.dimen.padding_16).toInt()
                googleMap.setPadding(padding, padding, padding, padding)
                googleMap.setOnCameraIdleListener(this)
                googleMap.setOnCameraMoveStartedListener(this)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        mapView?.onSaveInstanceState(outState)
    }

    override fun onPause() {
        mapView?.onPause()
    }

    override fun onResume() {
        mapView?.onResume()
    }

    override fun onDestroy() {
        mapView?.onDestroy()
    }

    override fun onLowMemory() {
        mapView?.onLowMemory()
    }

    override fun moveCamera(latLongBounds: LatLngBounds) {
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(latLongBounds, 0)
        mapView?.getMapAsync { googleMap ->
            googleMap.moveCamera(cameraUpdate)
        }
    }

    override fun setInfoWindowAdapter(adapter: GoogleMap.InfoWindowAdapter) {
        mapView?.getMapAsync {
            it.setInfoWindowAdapter(adapter)
            it.setOnMarkerClickListener { marker ->
                selectedMarker = marker
                marker.showInfoWindow()
                true
            }
        }
    }

    override fun showInfoWindow() {
        selectedMarker?.let {
            if (it.isInfoWindowShown) {
                it.showInfoWindow()
            }
        }
    }

    override fun setVenues(venues: List<Venue>, latitudeLongitude: LatitudeLongitude) {
        mapView?.getMapAsync {map ->
            if (venues.isNotEmpty()) {
                val builder = LatLngBounds.builder()
                for (i in 0 until venues.size) {
                    val latLong = LatLng(venues[i].location.latitude, venues[i].location.longitude)
                    builder.include(latLong)
                    val marker = map.addMarker(MarkerOptions().position(latLong))
                    marker.tag = venues[i]
                }
                moveCamera(builder.build())

                navigationListener?.let {
                    map.setOnInfoWindowClickListener { marker ->
                        val venue = marker.tag as Venue
                        it.navigateToDetails(venue.id)
                    }
                }
            } else {
                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(LatLng(latitudeLongitude.latitude, latitudeLongitude.longitude), 13.0f)
                map.moveCamera(cameraUpdate)
            }
        }
    }

    override fun setNavigationListener(listener: DetailsNavigation) {
        this.navigationListener = listener
    }

    override fun onCameraMoveStarted(reason: Int) {
        when (reason) {
            REASON_GESTURE -> this.cameraHasStartedMoving = true
        }
    }

    override fun onCameraIdle() {
        if (cameraHasStartedMoving) {
            cameraHasStartedMoving = false
            mapView?.getMapAsync { map ->
                val latLong = map.projection.visibleRegion.latLngBounds
                navigationListener?.let {
                    it.reloadPins(latLong.center.latitude, latLong.center.longitude)
                }
            }
        }
    }

    interface DetailsNavigation {
        fun navigateToDetails(id: String)

        fun reloadPins(latitude: Double, longitude: Double)
    }
}