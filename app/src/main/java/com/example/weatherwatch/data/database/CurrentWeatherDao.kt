package com.example.weatherwatch.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
@TypeConverters(Converters::class)
interface CurrentWeatherDao {
    @Query("SELECT * FROM current_weather_list")
     fun getCurrentWeatherList(): LiveData<List<CurrentWeatherDbModel>>

    @Query("SELECT * FROM current_weather_list")
    fun getNoLiveCurrentWeatherList(): List<CurrentWeatherDbModel>

    @Query("SELECT * FROM current_weather_list LIMIT 1")
    fun getCurrentWeather(): LiveData<CurrentWeatherDbModel>

    @Query("SELECT * FROM current_weather_list WHERE selected =:selected")
    fun getSelectedCurrentWeather(selected:Boolean): LiveData<CurrentWeatherDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeatherList(currentWeatherList: List<CurrentWeatherDbModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(currentWeather: CurrentWeatherDbModel)



    @Query("UPDATE current_weather_list SET selected = 'false'")
    fun setAllSelectedToFalse()

}
