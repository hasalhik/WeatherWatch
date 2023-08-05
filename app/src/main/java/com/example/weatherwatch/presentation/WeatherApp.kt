package com.example.weatherwatch.presentation

import android.app.Application
import com.example.weatherwatch.di.DaggerApplicationComponent


class WeatherApp : Application(){


   val component by lazy {
       DaggerApplicationComponent.factory().create(this)
   }
    override fun onCreate() {
        super.onCreate()
    }


}