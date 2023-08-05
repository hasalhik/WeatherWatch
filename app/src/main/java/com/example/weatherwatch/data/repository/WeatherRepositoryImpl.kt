package com.example.weatherwatch.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.weatherwatch.data.database.*
import com.example.weatherwatch.data.mapper.PlaceMapper
import com.example.weatherwatch.data.natework.ApiService
import com.example.weatherwatch.domain.weather.WeatherRepository
import com.example.weatherwatch.domain.weather.entities.CurrentWeather
import com.example.weatherwatch.domain.weather.entities.Forecast
import com.example.weatherwatch.domain.place.PlaceInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    //private val application: Application,
    private val apiService: ApiService,
    private val mapper: PlaceMapper,
    private val placeInfoDao: PlaceInfoDao,
    private val forecastDao: ForecastDao,
    private val currentWeatherDao: CurrentWeatherDao,
) : WeatherRepository {
    override suspend fun loadData() {
        try {
            val place = placeInfoDao.getSelected(true)
            if (place == null) return
            Log.d("WeatherRepositoryImpl", "loadData ${place.toString()}")
            forecastDao.insertForecast(
                mapper.forecastDtoToDbModel(
                    apiService.getForecast(
                        lat = place.lat,
                        lon = place.lon
                    )
                )
            )
            currentWeatherDao.setAllSelectedToFalse()
            currentWeatherDao.insertCurrentWeather(
                mapper.currentWeatherDtoToDbModel(
                    apiService.getCurrentWeather(
                        lat = place.lat,
                        lon = place.lon
                    ), selected = true, place.localNames
                )
            )
        } catch (e: Exception) {
Log.d("WeatherRepositoryImpl","loadData catch: ${e.toString()}")
        }


    }

    override fun getCurrentWeather(placeInfo: PlaceInfo): LiveData<CurrentWeather> =
        Transformations.map(currentWeatherDao.getCurrentWeather()) {
            mapper.currentWeatherDbModelToEntity(it)
        }

    override fun getCurrentWeatherList(): LiveData<List<CurrentWeather>> =
        Transformations.map(currentWeatherDao.getCurrentWeatherList()) {
            mapper.listCurrentWeatherDbModelToListEntity(it)
        }

    override fun getForecast(): LiveData<Forecast> =
        Transformations.map(forecastDao.getForecast()) {
            mapper.forecastDbModelToEntity(it ?: ForecastDbModel())
        }


    override fun getSelectedCurrentWeather(): LiveData<CurrentWeather> =
        Transformations.map(currentWeatherDao.getSelectedCurrentWeather(true)) {
            mapper.currentWeatherDbModelToEntity(it ?: CurrentWeatherDbModel(selected = true))
        }


}