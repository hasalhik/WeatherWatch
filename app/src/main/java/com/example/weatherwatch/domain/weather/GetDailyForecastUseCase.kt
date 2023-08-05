package com.example.weatherwatch.domain.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherwatch.domain.weather.entities.Forecast
import com.example.weatherwatch.domain.weather.entities.ListForecast
import javax.inject.Inject

class GetDailyForecastUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

}