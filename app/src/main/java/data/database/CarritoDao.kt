package com.tapia.myapplication2025.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tapia.myapplication2025.model.CarritoItem

@Dao
interface CarritoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CarritoItem)

    @Delete
    suspend fun delete(item: CarritoItem)

    @Query("SELECT * FROM CarritoItem")
    fun getAll(): LiveData<List<CarritoItem>>

    @Query("DELETE FROM CarritoItem")
    suspend fun clear()
}
