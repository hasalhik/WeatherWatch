package com.example.weatherwatch.presentation.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.weatherwatch.databinding.ItemSearchPlaceInfoBinding
import com.example.weatherwatch.domain.place.PlaceInfo

class SearchPlaceAdapter(
    private val context: Context,

    ) : ListAdapter<PlaceInfo, SearchPlaceViewHolder>(PlaceInfoDiffCallBack) {
    var onPlaceClickListener: OnPlaceClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchPlaceViewHolder {
        val binding = ItemSearchPlaceInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchPlaceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchPlaceViewHolder, position: Int) {
        val place = getItem(position)
        with(holder.binding) {
            Log.d("SearchPlaceAdapter", "place.localName ${place.localNames}")

            placeName.text = place.localNames
            country.text = place.country
            state.text = place.state
            root.setOnClickListener {

                onPlaceClickListener?.onPlaceClick(place)
            }

        }
    }

    interface OnPlaceClickListener {
        fun onPlaceClick(placeInfo: PlaceInfo)
    }

}
