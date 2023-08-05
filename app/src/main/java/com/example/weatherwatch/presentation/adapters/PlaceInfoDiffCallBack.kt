package com.example.weatherwatch.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.weatherwatch.domain.place.PlaceInfo

object PlaceInfoDiffCallBack : DiffUtil.ItemCallback<PlaceInfo>() {
    override fun areItemsTheSame(oldItem: PlaceInfo, newItem: PlaceInfo): Boolean {
        return if (oldItem.hashCode()==newItem.hashCode())
            oldItem == newItem
        else false
    }

    override fun areContentsTheSame(oldItem: PlaceInfo, newItem: PlaceInfo): Boolean {
        return if (oldItem.hashCode()==newItem.hashCode())
            oldItem == newItem
        else false
    }

}
