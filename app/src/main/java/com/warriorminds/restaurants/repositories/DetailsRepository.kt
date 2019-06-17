package com.warriorminds.restaurants.repositories

import com.warriorminds.restaurants.models.Details

interface DetailsRepository {
    suspend fun getDetailsAsync(id: String): Details?
}