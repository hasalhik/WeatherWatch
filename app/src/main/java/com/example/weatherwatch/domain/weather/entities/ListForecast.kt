package com.example.weatherwatch.domain.weather.entities



data class ListForecast(

    var dt: String? = null,
    var main: Main? = Main(),
    var weather: ArrayList<Weather> = arrayListOf(),
    var wind: Wind? = Wind(),
    var visibility: Int? = null,
    var pop: Double? = null,
    var sysForecast: SysForecast? = SysForecast(),
    var dtTxt: String? = null

)