package com.example.weatherwatch.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
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

    @SuppressLint("DiscouragedApi")
    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val forecast = getItem(position)
        with(holder.binding) {
            dividerTop.visibility= View.INVISIBLE
            dividerBottom.visibility= View.INVISIBLE
            when(forecast.textTime){
                "00:00"->dividerTop.visibility= View.VISIBLE
                "21:00"->dividerBottom.visibility= View.VISIBLE
            }



        time.text=forecast.textTime
            day.text=forecast.textDate.toString()
           weatherIcon.setImageResource(
                context.resources.getIdentifier(
                    "@drawable/ic_${forecast.weather[0].icon}", null, context.packageName
                )
            )
            degree.text=forecast.main?.temp
           if (forecast.textDayOfWeek != null)
               root.setOnClickListener {
                   onPlaceClickListener?.onPlaceClick(forecast)
               }
            val dayOfWEEK=forecast.textDayOfWeek?:return@with
            dayOfWeek.text=context.resources.getStringArray(R.array.day_of_week)[dayOfWEEK-1]





        }
    }

    interface OnPlaceClickListener {
        fun onPlaceClick(forecast: ListForecast)
    }

}
