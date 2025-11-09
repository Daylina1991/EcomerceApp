package com.tapia.myapplication2025.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import androidx.room.Ignore

@Parcelize
@Entity(tableName = "producto")
data class Producto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val categoria: String,
    val precio: Double,
    val descripcion: String,

    @Ignore
    val imagenResId: Int = 0
) : Parcelable {
    constructor(
        id: Int,
        nombre: String,
        categoria: String,
        precio: Double,
        descripcion: String
    ) : this(id, nombre, categoria, precio, descripcion, 0)
}