package com.example.weatherwatch.domain.place

import javax.inject.Inject

class DeletePlaceFromDbUseCase  @Inject constructor(
    private val repository: PlaceRepository
) {
    suspend fun deletePlaceFromDbUseCase(place: PlaceInfo) { repository.deletePlaceFromDb(place)}
}