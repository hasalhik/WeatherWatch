package com.example.weatherwatch.domain.weather

import androidx.lifecycle.LiveData
import com.example.weatherwatch.domain.weather.entities.CurrentWeather
import com.example.weatherwatch.domain.place.PlaceInfo
import javax.inject.Inject

class GetCurrentWeatherListUseCase @Inject constructor(
    private val repository: WeatherRepository
)  {

    fun getCurrentWeatherList (): LiveData<List<CurrentWeather>> {
        return repository.getCurrentWeatherList()
    }
}