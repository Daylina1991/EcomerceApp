package com.tapia.myapplication2025

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tapia.myapplication2025.databinding.ActivityHomeBinding
import com.tapia.myapplication2025.model.Producto
import com.tapia.myapplication2025.ui.view.ProductoAdapter
import com.tapia.myapplication2025.ui.viewmodel.ProductoViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var productoViewModel: ProductoViewModel

    private val productosOriginales = listOf(
        Producto(0, "Notebook HP 15", "Notebooks", 699999.0, "Notebook HP con pantalla de 15 pulgadas y procesador Intel.", R.drawable.laptop),
        Producto(0, "Mouse Logitech G203", "Periféricos", 18999.0, "Mouse gamer con luces RGB y alta precisión.", R.drawable.mouse),
        Producto(0, "Monitor Samsung 24'' Full HD", "Monitores", 120000.0, "Monitor curvo para mayor inmersión", R.drawable.monitor_samsung),
        Producto(0, "Teclado Mecánico Redragon", "Periféricos", 34000.0, "Teclado mecánico con switches rojos", R.drawable.teclado_mecanico),
        Producto(0, "Auriculares HyperX Cloud", "Audio", 56000.0,  "Auriculares con sonido envolvente", R.drawable.auricular_hyperx),
        Producto(0, "Webcam Logitech C920", "Periféricos", 42000.0, "Webcam HD para videollamadas", R.drawable.webcam),
        Producto(0, "Disco SSD 1TB", "Almacenamiento", 95000.0, "Disco sólido de 1TB para alto rendimiento", R.drawable.disco_duro),
        Producto(0, "Teclado Mecánico Redragon Kumara K552", "Periféricos", 35000.0, "Teclado mecánico con switches rojos", R.drawable.teclado_mecanico),
        Producto(0, "Mouse Gamer Logitech G203", "Periféricos", 18000.0, "Mouse gamer con sensor preciso", R.drawable.mouse_gamer),
        Producto(0, "Monitor Samsung 24'' Curvo", "Monitor", 95000.0, "Monitor curvo para mayor inmersión", R.drawable.monitor_samsung),
        Producto(0, "Auriculares HyperX Cloud II", "Audio", 72000.0,  "Versión mejorada con sonido 7.1", R.drawable.auricular_hyperx),
        Producto(0, "Notebook HP Pavilion 15", "Computadoras", 680000.0, "Notebook potente para productividad", R.drawable.laptop_dell),
        Producto(0, "Disco SSD Kingston 480GB", "Almacenamiento", 42000.0,  "Disco sólido de alto rendimiento", R.drawable.disco_duro),
        Producto(0, "Impresora Epson EcoTank L3250", "Impresoras", 210000.0, "Impresora con sistema de tinta continua", R.drawable.impresora_epson),
        Producto(0, "Laptop Dell Inspiron 14", "Notebooks", 650000.0, "Laptop liviana y eficiente", R.drawable.laptop_dell)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navegation.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_logout -> {
                    val prefs: SharedPreferences = getSharedPreferences(RegistroActivity.CREDENCIALES, MODE_PRIVATE)
                    prefs.edit().putBoolean("autoLogin", false).apply()

                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }

        productoViewModel = ViewModelProvider(this)[ProductoViewModel::class.java]
        val adapter = ProductoAdapter { producto ->

        }
        binding.recyclerProductos.layoutManager = LinearLayoutManager(this)
        binding.recyclerProductos.adapter = adapter

        val prefs: SharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val yaInsertados = prefs.getBoolean("productosInsertados", false)

        productoViewModel.productos.observe(this) { productos ->
            if (productos.isEmpty() && !yaInsertados) {
                for (producto in productosOriginales) {
                    productoViewModel.agregar(producto)
                }
                prefs.edit().putBoolean("productosInsertados", true).apply()
            }
            val productosConImagen = productos.map { producto ->
                val imagen = when (producto.nombre) {
                    "Notebook HP 15" -> R.drawable.laptop
                    "Mouse Logitech G203" -> R.drawable.mouse
                    "Monitor Samsung 24'' Full HD" -> R.drawable.monitor_samsung
                    "Teclado Mecánico Redragon" -> R.drawable.teclado_mecanico
                    "Auriculares HyperX Cloud" -> R.drawable.auricular_hyperx
                    "Webcam Logitech C920" -> R.drawable.webcam
                    "Disco SSD 1TB" -> R.drawable.disco_duro
                    "Teclado Mecánico Redragon Kumara K552" -> R.drawable.teclado_mecanico
                    "Mouse Gamer Logitech G203" -> R.drawable.mouse_gamer
                    "Monitor Samsung 24'' Curvo" -> R.drawable.monitor_samsung
                    "Auriculares HyperX Cloud II" -> R.drawable.auricular_hyperx
                    "Notebook HP Pavilion 15" -> R.drawable.laptop_dell
                    "Disco SSD Kingston 480GB" -> R.drawable.disco_duro
                    "Impresora Epson EcoTank L3250" -> R.drawable.impresora_epson
                    "Laptop Dell Inspiron 14" -> R.drawable.laptop_dell
                    else -> R.drawable.imagen_no_disponible
                }
                producto.copy(imagenResId = imagen)
            }
            adapter.submitList(productosConImagen)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as? SearchView
        searchView?.queryHint = "Buscar producto..."

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // si implementás búsqueda, hacelo aquí
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // filtrado en vivo si querés
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}
