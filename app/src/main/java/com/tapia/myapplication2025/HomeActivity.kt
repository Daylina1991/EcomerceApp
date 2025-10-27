package com.tapia.myapplication2025

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.tapia.myapplication2025.databinding.ActivityHomeBinding
import com.tapia.myapplication2025.model.Producto

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val productosOriginales = listOf(
        Producto("Notebook HP 15", "Notebooks", 699999.0),
        Producto("Mouse Logitech G203", "Periféricos", 18999.0),
        Producto("Monitor Samsung 24'' Full HD", "Monitores", 120000.0),
        Producto("Teclado Mecánico Redragon", "Periféricos", 34000.0),
        Producto("Auriculares HyperX Cloud", "Audio", 56000.0),
        Producto("Webcam Logitech C920", "Periféricos", 42000.0),
        Producto("Disco SSD 1TB", "Almacenamiento", 95000.0),
        Producto("Webcam Logitech C920", "Periféricos", 42000.0),
        Producto("Teclado Mecánico Redragon Kumara K552", "Periféricos", 35000.0),
        Producto("Mouse Gamer Logitech G203", "Periféricos", 18000.0),
        Producto("Monitor Samsung 24'' Curvo", "Monitores", 95000.0),
        Producto("Auriculares HyperX Cloud II", "Audio", 72000.0),
        Producto("Notebook HP Pavilion 15", "Computadoras", 680000.0),
        Producto("Disco SSD Kingston 480GB", "Almacenamiento", 42000.0),
        Producto("Impresora Epson EcoTank L3250", "Impresoras", 210000.0),
        Producto("Laptop Dell Inspiron 14", "Notebooks", 650000.0)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        val botonSalir: Button = findViewById(R.id.salir)
        botonSalir.setOnClickListener {

            val prefs: SharedPreferences = getSharedPreferences(RegistroActivity.CREDENCIALES, MODE_PRIVATE)
            prefs.edit().clear().apply()


            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }


        mostrarProductos(productosOriginales, "")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as? SearchView
        searchView?.queryHint = "Buscar producto..."

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                mostrarProductos(productosOriginales, query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                mostrarProductos(productosOriginales, newText ?: "")
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
            contenedor.addView(card)
        }
    }
}
