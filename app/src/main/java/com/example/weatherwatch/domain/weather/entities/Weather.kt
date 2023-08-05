package com.example.weatherwatch.domain.weather.entities


data class Weather(

    var id: Int? = null,
    var main: String? = null,
    var description: String? = null,
    var icon: String? = null

)