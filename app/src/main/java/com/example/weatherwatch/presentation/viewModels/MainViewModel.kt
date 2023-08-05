package com.example.weatherwatch.presentation.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherwatch.domain.weather.GetCurrentWeatherListUseCase
import com.example.weatherwatch.domain.weather.GetForecastUseCase
import com.example.weatherwatch.domain.weather.LoadDataUseCase
import com.example.weatherwatch.domain.place.PlaceInfo
import com.example.weatherwatch.domain.weather.GetSelectedCurrentWeatherUseCase
import com.example.weatherwatch.domain.weather.entities.CurrentWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getSelectedCurrentWeatherUseCase: GetSelectedCurrentWeatherUseCase,
    private val getForecastUseCase: GetForecastUseCase,
    private val loadDataUseCase: LoadDataUseCase
) : ViewModel() {


    val currentWeather=getSelectedCurrentWeatherUseCase.getSelectedCurrentWeather()

    val forecast = getForecastUseCase.getForecast()


    fun loadData() {
        Log.d("MainViewModel ", "loadData")
        viewModelScope.launch(Dispatchers.IO) {
            loadDataUseCase.invoke()

        }
    }


}
