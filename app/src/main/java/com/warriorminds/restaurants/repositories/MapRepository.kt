package com.warriorminds.restaurants.repositories

import com.warriorminds.restaurants.models.Venue

interface MapRepository {
    suspend fun searchRestaurantsAsync(latitude: Double, longitude: Double): List<Venue>?
}