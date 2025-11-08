package com.tapia.myapplication2025.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tapia.myapplication2025.model.CarritoItem
import com.tapia.myapplication2025.model.Producto

@Database(entities = [Producto::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "producto_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
