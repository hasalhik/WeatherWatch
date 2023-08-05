package com.example.weatherwatch.domain.weather

import androidx.lifecycle.LiveData
import com.example.weatherwatch.domain.weather.entities.Forecast
import javax.inject.Inject

class GetForecastUseCase@Inject constructor(
    private val repository: WeatherRepository
) {

    fun getForecast (): LiveData<Forecast> {
        return repository.getForecast()
    }
}