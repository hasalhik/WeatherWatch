package com.example.weatherwatch.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherwatch.data.natework.model.forecast.CityDto
import com.example.weatherwatch.data.natework.model.forecast.ListForecastDto

@Entity(tableName = "forecast")
data class ForecastDbModel(
    @PrimaryKey()
    val id: String = "1",
    val cod: String? = null,
    val message: Int? = null,
    val cnt: Int? = null,
    val listForecastDto: ArrayList<ListForecastDto> = arrayListOf(),
    val cityDto: CityDto? = CityDto()

)
