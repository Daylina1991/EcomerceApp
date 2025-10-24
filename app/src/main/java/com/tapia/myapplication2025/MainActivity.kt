package com.tapia.myapplication2025

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.gson.Gson
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tapia.myapplication2025.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding  //relacionamos vista de login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) //punto de partida oncreate
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater) //
        //setContentView(R.layout.activity_main)//relaciona la vista
        setContentView(binding.root)//relaciona directorio completo
        // boton de acceso
        binding.botonacceso.setOnClickListener {
            validateData()

        } //enlace de registro
        binding.enlaceRegistro.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
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
        val preferences = getSharedPreferences(RegistroActivity.CREDENCIALES, MODE_PRIVATE)
        val autoLogin = preferences.getBoolean("autoLogin", false)
        val userData = preferences.getString("userData", null)


        //Toast.makeText(this, "autoLogin={$autoLogin}", Toast.LENGTH_SHORT).show()

        if (autoLogin == true) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
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


    fun validateData() {
        val nombreBinding = binding.editTextnombre.text.toString().trim()
        val contrasenaBinding = binding.editTextcontrasena.text.toString().trim()

        if (nombreBinding.isEmpty() || contrasenaBinding.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val preferences = getSharedPreferences(RegistroActivity.CREDENCIALES, MODE_PRIVATE)
        val nombreInJsonFormat = preferences.getString("userData", null)

        if (nombreInJsonFormat == null) {
            Toast.makeText(this, "Primero debes almacenar algún dato!", Toast.LENGTH_LONG).show()
            return
        }

        val gson = Gson()
        val user = gson.fromJson(nombreInJsonFormat, Usuario::class.java)
        if (nombreBinding == user.nombre && contrasenaBinding == user.contrasena) {
            val editor = preferences.edit()
            editor.putBoolean("autoLogin", true)
            editor.apply()

            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "CORRECTO!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Ingrese nuevamente!", Toast.LENGTH_LONG).show()

        }
    }
}