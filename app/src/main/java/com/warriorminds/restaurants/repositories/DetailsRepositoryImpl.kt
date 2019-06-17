package com.warriorminds.restaurants.repositories

import com.warriorminds.restaurants.models.Details
import com.warriorminds.restaurants.network.RestaurantsService
import com.warriorminds.restaurants.utils.API_V
import java.lang.Exception
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(private val service: RestaurantsService) : DetailsRepository {
    override suspend fun getDetailsAsync(id: String): Details? {
        try {
            val response = service.getVenueDetail(id, API_V).await()
            if (response.isSuccessful && response.body() != null && response.body()!!.meta.code == "200") {
                return response.body()!!.response.venue
            }
        } catch (e: Exception) {
            return null
        }
        return null
    }
}