package com.warriorminds.restaurants.repositories

import com.warriorminds.restaurants.models.Venue
import com.warriorminds.restaurants.network.RestaurantsService
import com.warriorminds.restaurants.utils.*
import java.lang.Exception
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(private val service: RestaurantsService): MapRepository {
    override suspend fun searchRestaurantsAsync(latitude: Double, longitude: Double): List<Venue>? {
        try {
            val response = service.searchNearbyRestaurants(API_V, "$latitude,$longitude",
                BROWSE_INTENT, RADIUS, LIMIT, RESTAURANT_CATEGORY_ID).await()
            if (response.isSuccessful && response.body()?.meta?.code == "200") {
                return response.body()!!.response.venues
            }
        } catch (e: Exception) {
            return null
        }
        return null
    }
}