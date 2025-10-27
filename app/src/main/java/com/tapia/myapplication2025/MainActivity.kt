package com.tapia.myapplication2025

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.tapia.myapplication2025.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = getSharedPreferences(RegistroActivity.CREDENCIALES, MODE_PRIVATE)
        val autoLogin = preferences.getBoolean("autoLogin", false)

        // Si ya está logueado, ir directo a HomeActivity
        if (autoLogin) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
            return
        }

        // Botón de acceso
        binding.botonacceso.setOnClickListener {
            validateData()
        }

        // Enlace de registro
        binding.enlaceRegistro.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }

        Log.d("CiclosDeVida", "onCreate()")
    }

    override fun onStart() {
        super.onStart()
        Log.d("CiclosDeVida", "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("CiclosDeVida", "onResume()")
        // No hace falta repetir autoLogin aquí
    }

    override fun onPause() {
        super.onPause()
        Log.d("CiclosDeVida", "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("CiclosDeVida", "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("CiclosDeVida", "onDestroy()")
    }

    private fun validateData() {
        val nombreBinding = binding.editTextnombre.text.toString().trim()
        val contrasenaBinding = binding.editTextcontrasena.text.toString().trim()

        if (nombreBinding.isEmpty() || contrasenaBinding.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val preferences = getSharedPreferences(RegistroActivity.CREDENCIALES, MODE_PRIVATE)
        val nombreInJsonFormat = preferences.getString("userData", null)

        if (nombreInJsonFormat == null) {
            Toast.makeText(this, "Primero debes registrar un usuario!", Toast.LENGTH_LONG).show()
            return
        }

        val gson = Gson()
        val user = gson.fromJson(nombreInJsonFormat, Usuario::class.java)

        if (nombreBinding == user.nombre && contrasenaBinding == user.contrasena) {
            // Guardar autoLogin
            preferences.edit().putBoolean("autoLogin", true).apply()

            // Ir a HomeActivity
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Usuario o contraseña incorrectos!", Toast.LENGTH_LONG).show()
        }
    }
}
