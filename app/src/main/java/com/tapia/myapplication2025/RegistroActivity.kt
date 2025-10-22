package com.tapia.myapplication2025

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
            //shared  almacena  los datos
            val preferences = getSharedPreferences("datos_personales", MODE_PRIVATE)
            val edit = preferences.edit()

            //recuperamos los datos
            edit.putString("nombre", nombre)
            edit.putString("mail", mail)
            edit.putString("contrasena", contrasena)
            edit.apply()


            Toast.makeText(this, "Registro exitoso! $nombre",Toast.LENGTH_LONG).show()
                    //  $nombreBinding",  Toast.LENGTH_LONG).show()  //mensaje emergente

            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("nombre", nombre)
            intent.putExtra("mail", mail)
            intent.putExtra("contrasena", contrasena)
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

