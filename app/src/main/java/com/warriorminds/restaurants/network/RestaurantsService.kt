package com.warriorminds.restaurants.network

import com.warriorminds.restaurants.models.DetailsResponse
import com.warriorminds.restaurants.models.SearchResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestaurantsService {
    @GET("v2/venues/search")
    fun searchNearbyRestaurants(@Query("v") date: String,
                                @Query("ll") latlong: String,
                                @Query("intent") intent: String,
                                @Query("radius") radius: Int,
                                @Query("limit") limit: Int,
                                @Query("categoryId") categoryId: String): Deferred<Response<SearchResponse>>

    @GET("v2/venues/{id}")
    fun getVenueDetail(@Path("id") venueId: String,
                       @Query("v") date: String): Deferred<Response<DetailsResponse>>
}