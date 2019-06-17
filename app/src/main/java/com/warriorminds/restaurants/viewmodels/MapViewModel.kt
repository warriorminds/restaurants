package com.warriorminds.restaurants.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.warriorminds.restaurants.models.Venue
import com.warriorminds.restaurants.repositories.MapRepository
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MapViewModel @Inject constructor(private val repository: MapRepository) : ViewModel(), CoroutineScope {

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    val venues: MutableLiveData<List<Venue>> = MutableLiveData()
    val error: MutableLiveData<Boolean> = MutableLiveData()

    fun getVenues(latitude: Double, longitude: Double) {
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.searchRestaurantsAsync(latitude, longitude)
            }
            if (response != null) {
                venues.value = response
            } else {
                error.value = error.value?.not() ?: true
            }
        }
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}