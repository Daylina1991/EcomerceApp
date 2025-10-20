package com.tapia.myapplication2025

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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

    fun validateData () {
        val nombreBinding = binding.editTextNombre.text.toString().trim()
        val mailBinding = binding.editTextEmail.text.toString().trim()
        val contrasenaBinding = binding.editTextContrasena.text.toString().trim()

        if (nombreBinding.isNotEmpty() && mailBinding.isNotEmpty() &&contrasenaBinding.isNotEmpty() ){
            Toast.makeText(this, "Bienvenida  $nombreBinding",  Toast.LENGTH_LONG).show()  //mensaje emergente

            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra( "username",nombreBinding)
            intent.putExtra( "mail",mailBinding)
            intent.putExtra("usercontrasena", contrasenaBinding.toString())
            startActivity(intent)

        } else {
            Toast.makeText(this, "¡Complete todos  los campos!",  Toast.LENGTH_LONG).show()

        }
    }
}

