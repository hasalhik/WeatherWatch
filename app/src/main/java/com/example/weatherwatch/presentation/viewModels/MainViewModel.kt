package com.example.weatherwatch.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherwatch.domain.weather.GetHourlyForecastUseCase
import com.example.weatherwatch.domain.weather.LoadDataUseCase
import com.example.weatherwatch.domain.weather.GetSelectedCurrentWeatherUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getSelectedCurrentWeatherUseCase: GetSelectedCurrentWeatherUseCase,
    private val getHourlyForecastUseCase: GetHourlyForecastUseCase,
    private val loadDataUseCase: LoadDataUseCase
) : ViewModel() {


    val currentWeather=getSelectedCurrentWeatherUseCase.getSelectedCurrentWeather()

    val forecast = getHourlyForecastUseCase.getForecast()


    fun loadData() {
        Log.d("MainViewModel ", "loadData")
        viewModelScope.launch(Dispatchers.IO) {
            loadDataUseCase.invoke()

        }
    }


}
