package com.example.weatherwatch.data.natework.model.forecast

import com.google.gson.annotations.SerializedName


data class ForecastDto(

    @SerializedName("cod") var cod: String? = null,
    @SerializedName("message") var message: Int? = null,
    @SerializedName("cnt") var cnt: Int? = null,
    @SerializedName("list") var listForecastDto: ArrayList<ListForecastDto> = arrayListOf(),
    @SerializedName("city") var cityDto: CityDto? = CityDto()

)
