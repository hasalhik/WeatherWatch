package com.example.weatherwatch.data.natework.model.weather

import com.google.gson.annotations.SerializedName


data class CoordDto (

  @SerializedName("lon" ) var lon : Double? = null,
  @SerializedName("lat" ) var lat : Double? = null

)