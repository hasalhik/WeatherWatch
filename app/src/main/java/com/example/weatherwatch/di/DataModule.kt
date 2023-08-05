package com.example.weatherwatch.di

import android.app.Application
import com.example.weatherwatch.data.database.AppDatabase
import com.example.weatherwatch.data.database.CurrentWeatherDao
import com.example.weatherwatch.data.database.ForecastDao
import com.example.weatherwatch.data.database.PlaceInfoDao
import com.example.weatherwatch.data.natework.ApiFactory
import com.example.weatherwatch.data.natework.ApiService
import com.example.weatherwatch.data.repository.PlaceRepositoryImpl
import com.example.weatherwatch.data.repository.WeatherRepositoryImpl
import com.example.weatherwatch.domain.weather.WeatherRepository
import com.example.weatherwatch.domain.place.PlaceRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindPlaceRepository(impl: PlaceRepositoryImpl): PlaceRepository

    @Binds
    @ApplicationScope
    fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    companion object {

        @Provides
        @ApplicationScope
        fun providePlaceInfoDao(
            application: Application
        ): PlaceInfoDao {
            return AppDatabase.getInstance(application).placeInfoDao()
        }
        @Provides
        @ApplicationScope
        fun provideCurrentWeatherDao(
            application: Application
        ): CurrentWeatherDao {
            return AppDatabase.getInstance(application).currentWeatherDao()
        }

        @Provides
        @ApplicationScope
        fun provideForecastDao(
            application: Application
        ): ForecastDao {
            return AppDatabase.getInstance(application).forecastDao()
        }


        @Provides
        @ApplicationScope
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}
