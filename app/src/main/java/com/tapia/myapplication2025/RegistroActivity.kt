package com.tapia.myapplication2025

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.tapia.myapplication2025.databinding.ActivityRegistroBinding

class RegistroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botonRegistro.setOnClickListener {
            validateData()
        }


            //val nombre = binding.editTextNombreRegistro.text.toString().trim()
            //val email = binding.editTextEmailRegistro.text.toString().trim()
            //val password = binding.editTextContrasenaRegistro.text.toString().trim()
    }

    fun validateData () {//.ttrimborra espacios
        val nombre = binding.editTextNombre.text.toString().trim()
        val mail = binding.editTextEmail.text.toString().trim()
        val contrasena = binding.editTextContrasena.text.toString().trim()
        val acepto_terminos = binding.cbAceptarTerminos.isChecked

        val mail_valido = mail.contains("@") && mail.isNotBlank()

        if (nombre.isNotBlank() && mail_valido && contrasena.length == 8 && acepto_terminos){

            val user = Usuario(nombre, mail, contrasena)
            val gson = Gson()
            val json = gson.toJson(user)

            val prefs = getSharedPreferences(CREDENCIALES, MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("userData", json)
            editor.putBoolean("autoLogin", true)
            editor.apply()


            Toast.makeText(this, "Registro exitoso! $nombre",Toast.LENGTH_LONG).show()
                    //  $nombreBinding",  Toast.LENGTH_LONG).show()  //mensaje emergente

            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()


        } else {
            Toast.makeText(this, "¡Complete todos  los campos!",  Toast.LENGTH_LONG).show()

        }
    }
    // para acceder a las  variables
    companion object {
       val  CREDENCIALES = "datos_personales"

    }
}

