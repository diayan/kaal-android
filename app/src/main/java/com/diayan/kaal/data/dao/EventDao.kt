package com.diayan.kaal.data.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.diayan.kaal.data.model.Event

@Dao
interface EventDao {

    @Query("SELECT * FROM events WHERE id = id")
    fun getPagedEventsById(id: Int): DataSource.Factory<Int, Event>

    @Query("SELECT * FROM events WHERE id = :id")
    fun getEvents(id: String): LiveData<Event>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(events: List<Event>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(events: Event)
}