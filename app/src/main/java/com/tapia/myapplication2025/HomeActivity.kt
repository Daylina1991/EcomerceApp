package com.tapia.myapplication2025

import android.content.Intent
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import android.widget.Toast
import com.google.gson.Gson
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.tapia.myapplication2025.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.nav_drawer_home_open,
            R.string.nav_drawer_home_close

        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Botón cerrar sesión
        binding.btnSignOut.setOnClickListener {
            val preferences = getSharedPreferences(RegistroActivity.CREDENCIALES, MODE_PRIVATE)
            preferences.edit().clear().apply()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}




