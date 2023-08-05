package com.example.weatherwatch.data.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.weatherwatch.data.natework.model.forecast.CityDto
import com.example.weatherwatch.data.natework.model.forecast.ListForecastDto
import com.example.weatherwatch.data.natework.model.forecast.MainDto
import com.example.weatherwatch.data.natework.model.weather.CoordDto
import com.example.weatherwatch.data.natework.model.weather.SysDto
import com.example.weatherwatch.data.natework.model.weather.WeatherDto
import com.example.weatherwatch.data.natework.model.weather.WindDto
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.util.*



class Converters {

    @TypeConverter
    fun fromArrayListWeatherDto(array: ArrayList<WeatherDto>): String =
        Gson().toJson(array)

    @TypeConverter
    fun fromStringToWeatherDto(string: String): ArrayList<WeatherDto> {
        return Gson().fromJson(string, object : TypeToken<ArrayList<WeatherDto?>?>() {}.type)
    }

    @TypeConverter
    fun fromListForecastDto(array: ArrayList<ListForecastDto>): String =
        Gson().toJson(array)

    @TypeConverter
    fun fromStringToListForecastDto(string: String): ArrayList<ListForecastDto> {
        return Gson().fromJson(string, object : TypeToken<ArrayList<ListForecastDto>?>() {}.type)
    }

    @TypeConverter
    fun fromCityDto(it: CityDto): String =
        Gson().toJson(it)


    @TypeConverter
    fun toCityDto(string: String): CityDto {
        return Gson().fromJson(string, object : TypeToken<CityDto>() {}.type)
    }

    @TypeConverter
    fun fromMainDto(it: MainDto): String =
        Gson().toJson(it)


    @TypeConverter
    fun toMainDto(string: String): MainDto {
        return Gson().fromJson(string, object : TypeToken<MainDto>() {}.type)
    }


    @TypeConverter
    fun fromWindDto(it: WindDto): String =
        Gson().toJson(it)


    @TypeConverter
    fun toWindDto(string: String): WindDto {
        return Gson().fromJson(string, object : TypeToken<WindDto>() {}.type)
    }

    @TypeConverter
    fun fromCoordDto(it: CoordDto): String =
        Gson().toJson(it)


    @TypeConverter
    fun toCoordDto(string: String): CoordDto {
        return Gson().fromJson(string, object : TypeToken<CoordDto>() {}.type)
    }

    @TypeConverter
    fun fromSysDto(it: SysDto): String =
        Gson().toJson(it)


    @TypeConverter
    fun toSysDto(string: String): SysDto {
        return Gson().fromJson(string, object : TypeToken<SysDto>() {}.type)
    }


}