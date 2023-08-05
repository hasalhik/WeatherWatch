package com.example.weatherwatch.domain.weather.entities


data class Forecast(
    var id: String,
    var cod: String? = null,
    var message: Int? = null,
    var cnt: Int? = null,
    var listForecast: ArrayList<ListForecast> = arrayListOf(),
    var city: City? = City()

)
