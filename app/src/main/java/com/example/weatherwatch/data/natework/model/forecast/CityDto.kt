package com.example.weatherwatch.data.natework.model.forecast

import com.example.weatherwatch.data.natework.model.weather.CoordDto
import com.google.gson.annotations.SerializedName


data class CityDto(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("coord") var coordDto: CoordDto? = CoordDto(),
    @SerializedName("country") var country: String? = null,
    @SerializedName("population") var population: Int? = null,
    @SerializedName("timezone") var timezone: Int? = null,
    @SerializedName("sunrise") var sunrise: Int? = null,
    @SerializedName("sunset") var sunset: Int? = null

)