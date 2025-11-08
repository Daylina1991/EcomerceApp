package com.tapia.myapplication2025.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CarritoItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val categoria: String,
    val precio: Double,
    val cantidad: Int = 1
)
