package com.tapia.myapplication2025.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.IgnoredOnParcel
import androidx.room.Ignore

@Parcelize
@Entity(tableName = "producto")
data class Producto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val categoria: String,
    val precio: Double,
    val descripcion: String,
    val imagenNombre: String = "" // <- valor por defecto
) : Parcelable {
    @Ignore
    @IgnoredOnParcel // evita que Parcelize intente serializar esta propiedad
    var imagenResId: Int = 0
}
