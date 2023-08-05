package com.example.weatherwatch.data.natework.model.forecast

import com.example.weatherwatch.data.natework.model.weather.WeatherDto
import com.example.weatherwatch.data.natework.model.weather.WindDto
import com.google.gson.annotations.SerializedName


data class ListForecastDto(

    @SerializedName("dt") var dt: Int? = null,
    @SerializedName("main") var mainDto: MainDto? = MainDto(),
    @SerializedName("weather") var weather: ArrayList<WeatherDto> = arrayListOf(),
    @SerializedName("wind") var windDto: WindDto? = WindDto(),
    @SerializedName("visibility") var visibility: Int? = null,
    @SerializedName("pop") var pop: Double? = null,
    @SerializedName("sys") var sysForecastDto: SysForecastDto? = SysForecastDto(),
    @SerializedName("dt_txt") var dtTxt: String? = null

)