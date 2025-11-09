package com.tapia.myapplication2025.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tapia.myapplication2025.model.Producto

@Dao
interface ProductoDao {
    @Query("SELECT * FROM producto")
    fun getAll(): LiveData<List<Producto>>

    @Insert
    suspend fun insert(producto: Producto)


    @Delete
    suspend fun delete(producto: Producto)

    @Update
    suspend fun update(producto: Producto)
}

