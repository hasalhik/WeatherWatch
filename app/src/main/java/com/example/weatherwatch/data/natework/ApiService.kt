package com.example.weatherwatch.data.natework

import com.example.weatherwatch.data.natework.model.forecast.ForecastDto
import com.example.weatherwatch.data.natework.model.place.PlaceInfoDto
import com.example.weatherwatch.data.natework.model.weather.CurrentWeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("geo/1.0/direct")
    suspend fun getPlaceInfoList(
        @Query(QUERY_PARAM_Q)placeName:String="",
        @Query(QUERY_PARAM_LIMIT)limit:Int=5,
        @Query(QUERY_PARAM_API_ID)apiID:String= API_ID
    ): List<PlaceInfoDto>

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query(QUERY_PARAM_LAT) lat: Double? =null,
        @Query(QUERY_PARAM_LON) lon: Double? =null,
        @Query(QUERY_PARAM_LANGUAGE) lang: String = LANGUAGE_RU,
        @Query(QUERY_PARAM_API_ID) apiID:String= API_ID
    ): CurrentWeatherDto

    @GET("data/2.5/forecast")
    suspend fun getForecast(
        @Query(QUERY_PARAM_LAT) lat: Double? =null,
        @Query(QUERY_PARAM_LON) lon: Double? =null,
        @Query(QUERY_PARAM_LANGUAGE) lang: String = LANGUAGE_RU,
        @Query(QUERY_PARAM_API_ID) apiID:String= API_ID
    ): ForecastDto


    companion object {
        private const val QUERY_PARAM_API_ID = "appid"
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_Q = "q"
        private const val QUERY_PARAM_LANGUAGE = "lang"

        private const val QUERY_PARAM_LAT = "lat"
        private const val QUERY_PARAM_LON = "lon"



        private const val API_ID  = "832ee5f4797d22ffc4acb2ff4e5bb8d6"
        private const val LANGUAGE_RU  = "ru"
    }
}