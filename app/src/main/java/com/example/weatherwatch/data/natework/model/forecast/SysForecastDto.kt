package com.example.weatherwatch.data.natework.model.forecast

import com.google.gson.annotations.SerializedName


data class SysForecastDto(

    @SerializedName("pod") var pod: String? = null

)