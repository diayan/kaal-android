package com.diayan.kaal.data.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.diayan.kaal.data.model.Store

@Dao
interface StoreDao {

    @Query("SELECT * FROM stores WHERE id = id")
    fun getPagedStoresById(id: Int): DataSource.Factory<Int, Store>

    @Query("SELECT * FROM stores WHERE id = :id")
    fun getStores(id: String): LiveData<Store>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stores: List<Store>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stores: Store)
}