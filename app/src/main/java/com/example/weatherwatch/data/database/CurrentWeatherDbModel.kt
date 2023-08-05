package com.example.weatherwatch.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherwatch.data.natework.model.forecast.MainDto
import com.example.weatherwatch.data.natework.model.weather.*

@Entity(tableName = "current_weather_list")
data class CurrentWeatherDbModel(

    val coord: CoordDto? = CoordDto(),
    @PrimaryKey()
    val id: String = coord?.lat.toString() + coord?.lon.toString(),
    val weather: ArrayList<WeatherDto> = arrayListOf(),
    val base: String? = null,
    val main: MainDto? = MainDto(),
    val visibility: Int? = null,
    val wind: WindDto? = WindDto(),
    val clouds: Int? = null,
    val dt: Int? = null,
    val sys: SysDto? = SysDto(),
    val timezone: Int? = null,
    val name: String? = null,
    val cod: Int? = null,
    val selected:Boolean

)