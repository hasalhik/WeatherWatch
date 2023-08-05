package com.example.weatherwatch.domain.weather

import androidx.lifecycle.LiveData
import com.example.weatherwatch.domain.weather.entities.CurrentWeather
import com.example.weatherwatch.domain.place.PlaceInfo
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
)  {

    fun getCurrentWeather(placeInfo:PlaceInfo): LiveData<CurrentWeather> {
        return repository.getCurrentWeather(placeInfo)
    }
}