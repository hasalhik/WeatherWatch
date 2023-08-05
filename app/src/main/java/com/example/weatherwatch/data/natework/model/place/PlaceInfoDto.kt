package com.example.weatherwatch.data.natework.model.place

import com.google.gson.annotations.SerializedName


data class PlaceInfoDto(

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("local_names")
    var localNames: LocalNames? = LocalNames(),

    @SerializedName("lat")
    var lat: Double? = null,

    @SerializedName("lon")
    var lon: Double? = null,

    @SerializedName("country")
    var country: String? = null,

    @SerializedName("state")
    var state: String? = null

)

