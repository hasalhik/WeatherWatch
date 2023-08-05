package com.example.weatherwatch.domain.weather

import androidx.lifecycle.LiveData
import com.example.weatherwatch.domain.weather.entities.CurrentWeather
import com.example.weatherwatch.domain.weather.entities.Forecast
import com.example.weatherwatch.domain.place.PlaceInfo


interface WeatherRepository {
    suspend fun loadData()
    fun getCurrentWeather(placeInfo: PlaceInfo): LiveData<CurrentWeather>
    fun getCurrentWeatherList(): LiveData<List<CurrentWeather>>
    fun getForecast(): LiveData<Forecast>
    fun getSelectedCurrentWeather(): LiveData<CurrentWeather>
}