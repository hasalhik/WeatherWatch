package com.example.weatherwatch.data.natework.model.weather

import com.example.weatherwatch.data.natework.model.forecast.MainDto
import com.google.gson.annotations.SerializedName


data class CurrentWeatherDto(

    @SerializedName("coord") var coordDto: CoordDto? = CoordDto(),
    @SerializedName("weather") var weather: ArrayList<WeatherDto> = arrayListOf(),
    @SerializedName("base") var base: String? = null,
    @SerializedName("main") var mainDto: MainDto? = MainDto(),
    @SerializedName("visibility") var visibility: Int? = null,
    @SerializedName("wind") var windDto: WindDto? = WindDto(),
    @SerializedName("dt") var dt: Int? = null,
    @SerializedName("sys") var sys: SysDto? = SysDto(),
    @SerializedName("timezone") var timezone: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("cod") var cod: Int? = null

)