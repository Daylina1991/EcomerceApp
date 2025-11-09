package com.tapia.myapplication2025.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.tapia.myapplication2025.data.database.AppDatabase
import com.tapia.myapplication2025.model.Producto
import kotlinx.coroutines.launch

class ProductoViewModel(application: Application) : AndroidViewModel(application) {

    private val productoDao = AppDatabase.getDatabase(application).productoDao()
    val productos: LiveData<List<Producto>> = productoDao.getAll()

    // Simplificamos: no hacemos inserción automática aquí (la realiza HomeActivity con productosOriginales)
    fun agregar(producto: Producto) {
        viewModelScope.launch {
            productoDao.insert(producto)
        }
    }

    fun eliminar(producto: Producto) {
        viewModelScope.launch {
            productoDao.delete(producto)
        }
    }

    fun actualizar(producto: Producto) {
        viewModelScope.launch {
            productoDao.update(producto)
        }
    }
}
