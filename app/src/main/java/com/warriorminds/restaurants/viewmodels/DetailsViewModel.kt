package com.warriorminds.restaurants.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.warriorminds.restaurants.models.Details
import com.warriorminds.restaurants.models.Venue
import com.warriorminds.restaurants.repositories.DetailsRepository
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DetailsViewModel @Inject constructor(private val detailsRepository: DetailsRepository): ViewModel(), CoroutineScope {

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + job

    val details: MutableLiveData<Details> = MutableLiveData()
    val error: MutableLiveData<Boolean> = MutableLiveData()

    fun getDetails(id: String) {
        launch {
            val response = withContext(Dispatchers.IO) {
                detailsRepository.getDetailsAsync(id)
            }
            if (response != null) {
                details.value = response
            } else {
                error.value = error.value?.not() ?: true
            }
        }
    }


}