package com.example.weatherwatch.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherwatch.domain.place.DeletePlaceFromDbUseCase
import com.example.weatherwatch.domain.place.GetPlaceListFromDbUseCase
import com.example.weatherwatch.domain.place.InsertPlaceToDbUseCase
import com.example.weatherwatch.domain.place.PlaceInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesPlaceViewModel @Inject constructor(
    private val getPlaceInfoListFromDbUseCase: GetPlaceListFromDbUseCase,
    private val deletePlaceFromDbUseCase: DeletePlaceFromDbUseCase,
    private val insertPlaceToDbUseCase: InsertPlaceToDbUseCase
) : ViewModel() {

    val placeInfoList = getPlaceInfoListFromDbUseCase.getPlaceListFromDb()


    fun getPlaceInfoList() {
//        Log.d("FavoritesPlaceViewModel", "1_placeInfoList: ${_placeInfoList.value}")
//            _placeInfoList= getPlaceInfoListFromDbUseCase.getPlaceListFromDb() as MutableLiveData<List<PlaceInfo>>
//            Log.d("FavoritesPlaceViewModel", "2_placeInfoList: ${_placeInfoList.value}")


    }

    fun deletePlace(placeInfo: PlaceInfo) {
        Log.d("FavoritesPlaceViewModel", "deletePlace ${placeInfo.toString()}")
        viewModelScope.launch(Dispatchers.Main) {
            deletePlaceFromDbUseCase.deletePlaceFromDbUseCase(placeInfo)
        }
    }

    fun placeSelect(place: PlaceInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            placeInfoList.value?.forEach {
                if (it.selected) {
                    Log.d("FavoritesPlaceViewModel", "UNSelected ${it.localNames}")
                    insertPlaceToDbUseCase.invoke(it.apply { selected = false })
                }
            }
            Log.d("FavoritesPlaceViewModel", "Selected ${place.localNames}")
            insertPlaceToDbUseCase.invoke(place.apply { selected = true })
        }
    }
}
