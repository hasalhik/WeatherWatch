package com.example.weatherwatch.domain.place


data class PlaceInfo(

    var name: String? = "",
    var localNames: String?,
    var lat: Double? = null,
    var lon: Double? = null,
    var country: String? = "",
    var state: String? = "",
    var id: String? = null,
    var selected: Boolean = false,
)



