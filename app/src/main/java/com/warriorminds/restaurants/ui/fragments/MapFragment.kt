package com.warriorminds.restaurants.ui.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.warriorminds.restaurants.R
import com.warriorminds.restaurants.di.adapters.VenueMapAdapter
import com.warriorminds.restaurants.maps.GoogleMapProvider
import com.warriorminds.restaurants.maps.LocationProvider
import com.warriorminds.restaurants.maps.MapProvider
import com.warriorminds.restaurants.models.LatitudeLongitude
import com.warriorminds.restaurants.models.Venue
import com.warriorminds.restaurants.permissions.checkLocationPermissionsGranted
import com.warriorminds.restaurants.permissions.requestLocationPermissions
import com.warriorminds.restaurants.ui.fragments.DetailsFragment.Companion.VENUE_ID
import com.warriorminds.restaurants.viewmodels.MapViewModel
import com.warriorminds.restaurants.viewmodels.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_map.*
import javax.inject.Inject

class MapFragment : Fragment(), GoogleMapProvider.DetailsNavigation {

    companion object {
        var latitudeLongitude: LatitudeLongitude? = null
    }
    @Inject
    lateinit var mapProvider: MapProvider
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var venueMapAdapter: VenueMapAdapter
    @Inject
    lateinit var locationProvider: LocationProvider
    lateinit var viewModel: MapViewModel

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)

        locationProvider.init(activity!!)
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(MapViewModel::class.java)
        if (!checkLocationPermissionsGranted(context!!)) {
            requestLocationPermissions(this)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        com.warriorminds.restaurants.permissions.onRequestPermissionsResult(requestCode, grantResults, { init(true) },
            { init() })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        mapProvider.onCreate(savedInstanceState, view)
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        mapProvider.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onPause() {
        super.onPause()
        mapProvider.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapProvider.onResume()
        activity!!.title = getString(R.string.app_name)
        init(checkLocationPermissionsGranted(context!!))
    }

    override fun onDestroy() {
        super.onDestroy()
        mapProvider.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapProvider.onLowMemory()
    }

    override fun navigateToDetails(id: String) {
        val bundle = Bundle()
        bundle.putString(VENUE_ID, id)
        findNavController().navigate(R.id.details_action, bundle)
    }

    @SuppressLint("MissingPermission")
    private fun init(locationPermissionGranted: Boolean = false) {
        locationProvider.getLocation(locationPermissionGranted, {
            if (latitudeLongitude == null) {
                latitudeLongitude = it
            }
            latitudeLongitude?.let {
                initObservers(it.latitude, it.longitude)
                if (!locationPermissionGranted) {
                    Snackbar.make(map, getString(R.string.location_denied), Snackbar.LENGTH_LONG).show()
                }
            }
        }, {
            if (latitudeLongitude == null) {
                latitudeLongitude = it
            }
            initObservers(it.latitude, it.longitude)
            Snackbar.make(map, getString(R.string.location_error), Snackbar.LENGTH_LONG).show()
        })
    }

    override fun reloadPins(latitude: Double, longitude: Double) {
        latitudeLongitude = LatitudeLongitude(latitude, longitude)
        viewModel.getVenues(latitude, longitude)
    }

    private fun initObservers(latitude: Double, longitude: Double) {
        viewModel.venues.observe(this, Observer {
            initVenues(it)
        })
        viewModel.error.observe(this, Observer {
            Snackbar.make(map, getString(R.string.load_error), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.retry_action)) {
                    viewModel.getVenues(latitude, longitude)
                }
                .show()
        })
        mapProvider.setNavigationListener(this)
        viewModel.getVenues(latitude, longitude)
    }

    private fun initVenues(venues: List<Venue>) {
        mapProvider.setInfoWindowAdapter(venueMapAdapter)
        mapProvider.setVenues(venues, latitudeLongitude ?: LatitudeLongitude())
    }
}
