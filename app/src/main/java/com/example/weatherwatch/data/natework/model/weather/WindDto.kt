package com.example.weatherwatch.data.natework.model.weather

import com.google.gson.annotations.SerializedName


data class WindDto(

    @SerializedName("speed") var speed: Double? = null,
    @SerializedName("deg") var deg: Int? = null,
    @SerializedName("gust") var gust: Double? = null

)