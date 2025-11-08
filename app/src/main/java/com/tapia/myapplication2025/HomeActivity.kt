package com.tapia.myapplication2025

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.navigation.NavigationView
import com.tapia.myapplication2025.databinding.ActivityHomeBinding
import com.tapia.myapplication2025.model.Producto
import com.tapia.myapplication2025.ui.viewmodel.ProductoViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var productoViewModel: ProductoViewModel

    private val productosOriginales = listOf(
        Producto(0, "Notebook HP 15", "Notebooks", 699999.0),
        Producto(0, "Mouse Logitech G203", "Periféricos", 18999.0),
        Producto(0, "Monitor Samsung 24'' Full HD", "Monitores", 120000.0),
        Producto(0, "Teclado Mecánico Redragon", "Periféricos", 34000.0),
        Producto(0, "Auriculares HyperX Cloud", "Audio", 56000.0),
        Producto(0, "Webcam Logitech C920", "Periféricos", 42000.0),
        Producto(0, "Disco SSD 1TB", "Almacenamiento", 95000.0),
        Producto(0, "Webcam Logitech C920", "Periféricos", 42000.0),
        Producto(0, "Teclado Mecánico Redragon Kumara K552", "Periféricos", 35000.0),
        Producto(0, "Mouse Gamer Logitech G203", "Periféricos", 18000.0),
        Producto(0, "Monitor Samsung 24'' Curvo", "Monitores", 95000.0),
        Producto(0, "Auriculares HyperX Cloud II", "Audio", 72000.0),
        Producto(0, "Notebook HP Pavilion 15", "Computadoras", 680000.0),
        Producto(0, "Disco SSD Kingston 480GB", "Almacenamiento", 42000.0),
        Producto(0, "Impresora Epson EcoTank L3250", "Impresoras", 210000.0),
        Producto(0, "Laptop Dell Inspiron 14", "Notebooks", 650000.0)
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

        //inserta un producto
        binding.btnAgregar.setOnClickListener {
            val nuevoProducto = Producto(
                0,
                "Producto nuevo",
                "Categoría demo",
                123456.0
            )
            productoViewModel.agregar(nuevoProducto)
        }

// elimina todos los productos
        binding.btnEliminar.setOnClickListener {
            productoViewModel.productos.value?.forEach {
                productoViewModel.eliminar(it)
            }
        }

//cambia el nombre del primer producto (si existe)
        binding.btnActualizar.setOnClickListener {
            val lista = productoViewModel.productos.value
            if (!lista.isNullOrEmpty()) {
                val producto = lista[0]
                val actualizado = Producto(
                    producto.id,
                    "Producto actualizado",
                    producto.categoria,
                    producto.precio
                )
                productoViewModel.actualizar(actualizado)
            }
        }


        // ✅ Insertar productos originales solo una vez
        val prefs: SharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val yaInsertados = prefs.getBoolean("productosInsertados", false)

        productoViewModel.productos.observe(this) { lista ->
            if (lista.isEmpty() && !yaInsertados) {
                for (producto in productosOriginales) {
                    productoViewModel.agregar(producto)
                }
                prefs.edit().putBoolean("productosInsertados", true).apply()
            } else {
                mostrarProductos(lista, "")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as? SearchView
        searchView?.queryHint = "Buscar producto..."

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                productoViewModel.productos.value?.let {
                    mostrarProductos(it, query ?: "")
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                productoViewModel.productos.value?.let {
                    mostrarProductos(it, newText ?: "")
                }
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun mostrarProductos(lista: List<Producto>, filtro: String) {
        val contenedor: LinearLayout = binding.contenedorProductos
        contenedor.removeAllViews()

        val filtrados = if (filtro.isEmpty()) lista else lista.filter {
            it.nombre.contains(filtro, ignoreCase = true) ||
                    it.categoria.contains(filtro, ignoreCase = true)
        }

        for (producto in filtrados) {
            val card = CardView(this)
            card.setCardBackgroundColor(ContextCompat.getColor(this, android.R.color.white))
            card.radius = 16f
            card.setContentPadding(16, 16, 16, 16)

            val cardParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            cardParams.setMargins(0, 0, 0, 16)
            card.layoutParams = cardParams

            val tv = TextView(this)
            tv.text = """
                ${producto.nombre}
                Categoría: ${producto.categoria}
                Precio: $${producto.precio.toInt()}
            """.trimIndent()
            tv.setTextColor(ContextCompat.getColor(this, android.R.color.black))
            tv.textSize = 16f

            card.addView(tv)

            card.setOnClickListener {
                productoViewModel.eliminar(producto)
            }

            contenedor.addView(card)
        }
    }
}
