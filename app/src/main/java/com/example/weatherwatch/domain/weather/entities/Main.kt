package com.example.weatherwatch.domain.weather.entities


data class Main(

    var temp: String? = null,
    var feelsLike: String? = null,
     var tempMin: Double? = null,
    var tempMax: Double? = null,
    var pressure: String? = null,
    var seaLevel: Int? = null,
     var grndLevel: Int? = null,
    var humidity: String? = null,
     var tempKf: Double? = null

)