package com.example.weatherwatch.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlaceInfoDao {
    @Query("SELECT * FROM place_list ORDER BY selected ASC")
     fun getPlaceList(): LiveData<List<PlaceInfoDbModel>>

    @Query("SELECT * FROM place_list")
    fun getNoLivePlaceList(): List<PlaceInfoDbModel>

    @Query("SELECT * FROM place_list WHERE id=:placeId LIMIT 1")
    fun getPlace(placeId: String): PlaceInfoDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaceList(place: List<PlaceInfoDbModel>)

    @Query("UPDATE place_list SET selected = 'false'")
    suspend fun setAllSelectedToFalse()

    @Query("SELECT * FROM place_list WHERE selected =:selected")
    suspend fun getSelected(selected: Boolean): PlaceInfoDbModel

    @Query("DELETE FROM place_list WHERE id=:placeId")
    suspend fun deletePlace(placeId: String)

}

