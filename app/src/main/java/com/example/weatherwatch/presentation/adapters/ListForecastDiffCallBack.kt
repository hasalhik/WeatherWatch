package com.example.weatherwatch.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.weatherwatch.domain.weather.entities.ListForecast

object ListForecastDiffCallBack : DiffUtil.ItemCallback<ListForecast>() {
    override fun areItemsTheSame(oldItem: ListForecast, newItem: ListForecast): Boolean {
        return if (oldItem.hashCode()==newItem.hashCode())
            oldItem == newItem
        else false
    }

    override fun areContentsTheSame(oldItem: ListForecast, newItem: ListForecast): Boolean {
        return if (oldItem.hashCode()==newItem.hashCode())
            oldItem == newItem
        else false
    }

}
