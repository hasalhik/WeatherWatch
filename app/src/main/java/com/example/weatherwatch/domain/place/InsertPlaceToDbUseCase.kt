package com.example.weatherwatch.domain.place

import javax.inject.Inject

class InsertPlaceToDbUseCase  @Inject constructor(
    private val repository: PlaceRepository
) {
    suspend operator fun invoke(place: PlaceInfo) { repository.insertPlaceToDb(place)}
}