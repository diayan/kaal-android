package com.diayan.kaal.data.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.diayan.kaal.data.model.Place

@Dao
interface PlaceDao {

    @Query("SELECT * FROM places WHERE id = :id")
    fun getPagedPlacesById(id: Int): DataSource.Factory<Int, Place>

    @Query("SELECT * FROM places WHERE id = :id")
    fun getPlaces(id: String): LiveData<Place>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(places: List<Place>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(places: Place)
}