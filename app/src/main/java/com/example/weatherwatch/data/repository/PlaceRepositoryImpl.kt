package com.example.weatherwatch.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.weatherwatch.data.database.PlaceInfoDao
import com.example.weatherwatch.data.mapper.PlaceMapper
import com.example.weatherwatch.data.natework.ApiService
import com.example.weatherwatch.domain.place.PlaceInfo
import com.example.weatherwatch.domain.place.PlaceRepository
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
    //private val application: Application,
    private val apiService: ApiService,
    private val mapper: PlaceMapper,
    private val placeInfoDao: PlaceInfoDao,
) : PlaceRepository {
    override suspend fun insertPlaceToDb(place: PlaceInfo) {
        placeInfoDao.setAllSelectedToFalse()
        Log.d("PlaceRepositoryImpl", "insertPlaceToDb ${place.toString()}")
        placeInfoDao.insertPlaceList(listOf(mapper.placeInfoToDbModel(place)))

    }

    override fun getPlaceListFromDb(): LiveData<List<PlaceInfo>> = Transformations.map(
        placeInfoDao.getPlaceList()
    ) {
        mapper.mapListDbModelToListEntity(it)
    }


    override suspend fun deletePlaceFromDb(place: PlaceInfo) {
        Log.d("PlaceRepositoryImpl", "deletePlaceFromDb ${place.toString()}")
        place.id?.let { placeInfoDao.deletePlace(it) }
    }

    override suspend fun getCityInfoList(placeName: String): List<PlaceInfo> {
        try {
            val response = apiService.getPlaceInfoList(placeName = placeName)
            return mapper.listPlaceInfoDtoToListPlaceInfo(response)

        } catch (e: Exception) {

        }
        return listOf()
    }
}