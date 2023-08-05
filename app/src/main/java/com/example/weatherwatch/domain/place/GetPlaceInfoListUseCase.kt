package com.example.weatherwatch.domain.place

import javax.inject.Inject

class GetPlaceInfoListUseCase @Inject constructor(
    private val repository: PlaceRepository
) {
    suspend operator  fun invoke(placeName: String) = repository.getCityInfoList(placeName)
}
