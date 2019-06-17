package com.warriorminds.restaurants.models

import com.google.gson.annotations.SerializedName

data class Meta(val code: String,
                val requestId: String)

data class Location(val address: String,
                    val crossStreet: String,
                    @SerializedName("lat") val latitude: Double,
                    @SerializedName("lng") val longitude: Double,
                    val labeledLatLngs: List<LabeledLatitudeLongitude>,
                    val distance: Int,
                    val postalCode: String,
                    @SerializedName("cc") val countryCode: String,
                    val city: String,
                    val state: String,
                    val country: String,
                    val formattedAddress: List<String>)

data class Category(val id: String,
                    val name: String,
                    val pluralName: String,
                    val shortName: String,
                    val icon: Icon,
                    val primary: Boolean)

data class Icon(val prefix: String,
                val suffix: String)

data class LatitudeLongitude(var latitude: Double = 40.7099,
                             var longitude: Double = -73.9622)