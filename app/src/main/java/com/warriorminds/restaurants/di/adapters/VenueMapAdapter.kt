package com.warriorminds.restaurants.di.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.Navigation
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.warriorminds.restaurants.R
import com.warriorminds.restaurants.models.Venue
import com.warriorminds.restaurants.ui.fragments.DetailsFragment.Companion.VENUE_ID
import kotlinx.android.synthetic.main.venue_item.view.*
import javax.inject.Inject

class VenueMapAdapter @Inject constructor(private val context: Context): GoogleMap.InfoWindowAdapter {
    override fun getInfoContents(marker: Marker): View {
        val view = LayoutInflater.from(context).inflate(R.layout.venue_item, null)
        val venue = marker.tag as Venue
        view.venue_name.text = venue.name
        view.venue_address.text = venue.location.formattedAddress.joinToString(" ")
        return view
    }

    override fun getInfoWindow(marker: Marker?) = null
}