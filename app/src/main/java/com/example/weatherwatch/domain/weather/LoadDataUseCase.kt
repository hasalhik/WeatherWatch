package com.example.weatherwatch.domain.weather

import javax.inject.Inject

class LoadDataUseCase@Inject constructor(
    private val repository: WeatherRepository
) {
     suspend operator fun invoke() = repository.loadData()
}