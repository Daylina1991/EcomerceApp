package com.tapia.myapplication2025.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Producto(
    val id: Int,
    val nombre: String,
    val categoria: String,
    val precio: Double,
    val descripcion: String
) : Parcelable
