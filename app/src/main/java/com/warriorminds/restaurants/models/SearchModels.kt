package com.warriorminds.restaurants.models

import com.google.gson.annotations.SerializedName

data class SearchResponse(val meta: Meta,
                          val response: Response)

data class Response(val venues: List<Venue>)

data class Venue(val id: String,
                 val name: String,
                 val location: Location,
                 val categories: List<Category>,
                 val referralId: String,
                 val hasPerk: Boolean)

data class VenuePage(val id: String)

data class LabeledLatitudeLongitude(val label: String,
                                    @SerializedName("lat") val latitude: Double,
                                    @SerializedName("lng") val longitude: Double)