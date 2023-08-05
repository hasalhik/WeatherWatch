package com.example.weatherwatch.domain.place

import androidx.lifecycle.LiveData

interface PlaceRepository {
    suspend fun getCityInfoList(placeName: String): List<PlaceInfo>
    suspend fun insertPlaceToDb(place: PlaceInfo)
    fun getPlaceListFromDb(): LiveData<List<PlaceInfo>>
    suspend fun deletePlaceFromDb(place: PlaceInfo)
}