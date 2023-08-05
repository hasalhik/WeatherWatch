package com.example.weatherwatch.domain.weather.entities


data class CurrentWeather(
    var id: String? = null,
    var coord: Coord? = Coord(),
    var weather: ArrayList<Weather> = arrayListOf(),
    var base: String? = null,
    var main: Main? = Main(),
    var visibility: Int? = null,
    var wind: Wind,
    var dt: String?= null,
    var sys: Sys? = Sys(),
    var timezone: Int? = null,
    var name: String? = null,
    var cod: Int? = null

)