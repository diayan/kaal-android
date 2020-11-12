package com.diayan.kaal.data.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.diayan.kaal.data.model.Regions

@Dao
interface RegionsDao {

    @Query("SELECT * FROM events WHERE id = :id")
    fun getPagedEventsById(id: Int): DataSource.Factory<Int, Regions>

    @Query("SELECT * FROM events WHERE id = :id")
    fun getEvents(id: String): LiveData<Regions>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(regions: List<Regions>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(events: Regions)
}