package com.tapia.myapplication2025

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tapia.myapplication2025.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:  ActivityMainBinding  //relacionamos vista de login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) //punto de partida oncreate
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater) //
        //setContentView(R.layout.activity_main)//relaciona la vista
        setContentView(binding.root)//relaciona directorio completo

        binding.botonacceso.setOnClickListener {
            validateData()

        }
        binding.enlaceRegistro.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

    }

    fun validateData () {
        val nombreBinding = binding.editTextnombre.text.toString().trim()
        val contrasenaBinding = binding.editTextcontrasena.text.toString().trim()

        if (nombreBinding.isNotEmpty() && contrasenaBinding.isNotEmpty()){
            //Toast.makeText(this, "Bievenida  $nombreBinding",  Toast.LENGTH_LONG).show()  //mensaje emergente
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra( "username",nombreBinding)
            intent.putExtra("usercontrasena", contrasenaBinding.toString())
            startActivity(intent)

        } else {
            Toast.makeText(this, "¡Complete todos  los campos!",  Toast.LENGTH_LONG).show()

        }
    }
}
