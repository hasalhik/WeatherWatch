package com.example.weatherwatch.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
@TypeConverters(Converters::class)
interface ForecastDao {
    @Query("SELECT * FROM forecast LIMIT 1")
    fun getForecast(): LiveData<ForecastDbModel>


    @Query("SELECT * FROM forecast ")
    fun getForecastList(): LiveData<List<ForecastDbModel>>
    @Query("SELECT * FROM forecast ")
    fun getForecastListNoLive(): List<ForecastDbModel>
    @Query("SELECT * FROM forecast LIMIT 1")
    fun getForecastNoLive(): ForecastDbModel


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecast(forecast:ForecastDbModel)


}
