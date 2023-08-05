package com.example.weatherwatch.domain.weather.entities



data class ListForecast(

    var dt: Int? = null,
    var main: Main? = Main(),
    var weather: ArrayList<Weather> = arrayListOf(),
    var wind: Wind? = Wind(),
    var visibility: Int? = null,
    var pop: Double? = null,
    var sysForecast: SysForecast? = SysForecast(),
    var textDayOfWeek: Int? = null,
    var textDate: String? = null,
    var textTime: String? = null

)