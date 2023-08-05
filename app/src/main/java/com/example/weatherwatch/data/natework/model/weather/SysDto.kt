package com.example.weatherwatch.data.natework.model.weather

import com.google.gson.annotations.SerializedName


data class SysDto(

    @SerializedName("sunrise") var sunrise: Int? = null,
    @SerializedName("sunset") var sunset: Int? = null

)