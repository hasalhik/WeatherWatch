package com.example.weatherwatch.presentation.viewModels

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherwatch.domain.place.GetPlaceInfoListUseCase
import com.example.weatherwatch.domain.place.PlaceInfo
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.weatherwatch.domain.place.InsertPlaceToDbUseCase
import kotlinx.coroutines.launch

class SearchPlaceViewModel @Inject constructor(
    private val getPlaceInfoListUseCase: GetPlaceInfoListUseCase,
    private val insertPlaceToDbUseCase: InsertPlaceToDbUseCase
) : ViewModel() {
    private var _placeInfoList = MutableLiveData<List<PlaceInfo>>()
    val placeInfoList: LiveData<List<PlaceInfo>>
        get() = _placeInfoList

    fun getPlaceInfoList(placeName: Editable?) {
        if (placeName?.isNotEmpty() == true)
            viewModelScope.launch(Dispatchers.Main) {
                _placeInfoList.value= getPlaceInfoListUseCase.invoke(placeName = placeName.toString())
            }
    }
    fun insertPlaceToDb(placeInfo: PlaceInfo){
        viewModelScope.launch(Dispatchers.IO) {
           placeInfo.selected=true
            insertPlaceToDbUseCase.invoke(placeInfo)
        }
    }
}