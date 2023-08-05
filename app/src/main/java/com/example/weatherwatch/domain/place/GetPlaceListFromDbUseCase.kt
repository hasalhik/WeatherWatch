package com.example.weatherwatch.domain.place

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetPlaceListFromDbUseCase @Inject constructor(
    private val repository: PlaceRepository
) {
    fun getPlaceListFromDb(): LiveData<List<PlaceInfo>> {
        return repository.getPlaceListFromDb()
    }
}