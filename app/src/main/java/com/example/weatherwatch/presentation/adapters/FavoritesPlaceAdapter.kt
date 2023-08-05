package com.example.weatherwatch.presentation.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import com.example.weatherwatch.R
import com.example.weatherwatch.databinding.ItemPlaceInfoBinding
import com.example.weatherwatch.domain.place.PlaceInfo

class FavoritesPlaceAdapter(
    private val context: Context,

    ) : ListAdapter<PlaceInfo, FavoritesPlaceViewHolder>(PlaceInfoDiffCallBack) {
    var onPlaceClickListener: OnPlaceClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesPlaceViewHolder {
        val binding = ItemPlaceInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoritesPlaceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesPlaceViewHolder, position: Int) {
        val place = getItem(position)
        with(holder.binding) {
            placeName.text = place.localNames
            country.text = place.country
            state.text = place.state
            if (place.selected)
                selectIcon.setColorFilter(ContextCompat.getColor(context, R.color.yellow))
            else
                selectIcon.setColorFilter(ContextCompat.getColor(context, R.color.gray))

            root.setOnClickListener {
                onPlaceClickListener?.onPlaceClick(place)
            }


            deleteIcon.setOnClickListener {
                onPlaceClickListener?.onDeleteClick(place)
            }
            selectIcon.setOnClickListener {
                onPlaceClickListener?.onSelectClick(place)
            }

        }
    }

    interface OnPlaceClickListener {
        fun onPlaceClick(placeInfo: PlaceInfo)
        fun onDeleteClick(placeInfo: PlaceInfo)
        fun onSelectClick(place: PlaceInfo)
    }

}
