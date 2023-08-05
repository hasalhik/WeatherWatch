package com.example.weatherwatch.presentation.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import com.example.weatherwatch.R
import com.example.weatherwatch.databinding.ItemForecastBinding
import com.example.weatherwatch.databinding.ItemPlaceInfoBinding
import com.example.weatherwatch.domain.place.PlaceInfo
import com.example.weatherwatch.domain.weather.entities.Forecast
import com.example.weatherwatch.domain.weather.entities.ListForecast

class ForecastAdapter(
    private val context: Context,

    ) : ListAdapter<ListForecast, ForecastViewHolder>(ListForecastDiffCallBack) {
    var onPlaceClickListener: OnPlaceClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val binding = ItemForecastBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val forecast = getItem(position)
        with(holder.binding) {
        time.text=forecast.dtTxt
            day.text=forecast.dt.toString()
           weatherIcon.setImageResource(
                context.resources.getIdentifier(
                    "@drawable/ic_${forecast.weather[0].icon}", null, context?.packageName
                )
            )
            degree.text=forecast.main?.temp

            root.setOnClickListener {
                onPlaceClickListener?.onPlaceClick(forecast)
            }



        }
    }

    interface OnPlaceClickListener {
        fun onPlaceClick(forecast: ListForecast)
    }

}
