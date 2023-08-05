package com.example.weatherwatch.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "place_list")
data class PlaceInfoDbModel(

    val name: String?,
    val localNames: String?,
    val lat: Double?,
    val lon: Double?,
    val country: String?,
    val state: String?,
    @PrimaryKey()
    val id: String = lat.toString() + lon.toString(),
    val selected:Boolean
)
