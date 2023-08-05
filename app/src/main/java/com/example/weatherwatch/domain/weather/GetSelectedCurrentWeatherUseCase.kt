package com.example.weatherwatch.domain.weather

import androidx.lifecycle.LiveData
import com.example.weatherwatch.domain.weather.entities.CurrentWeather
import com.example.weatherwatch.domain.place.PlaceInfo
import javax.inject.Inject

class GetSelectedCurrentWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
)  {

    fun getSelectedCurrentWeather(): LiveData<CurrentWeather> {
        return repository.getSelectedCurrentWeather()
    }
}