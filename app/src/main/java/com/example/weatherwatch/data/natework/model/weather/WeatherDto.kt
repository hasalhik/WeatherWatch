package com.example.weatherwatch.data.natework.model.weather

import com.google.gson.annotations.SerializedName


data class WeatherDto(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("main") var main: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("icon") var icon: String? = null

)