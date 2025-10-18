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

        val editTextnombre = findViewById<EditText>(R.id.editTextnombre)
        val editTextcontrasena = findViewById<EditText>(R.id.editTextcontrasena)
        val botonacceso = findViewById<Button>(R.id.botonacceso)
        val botonregistro = findViewById<Button>(R.id.botonregistro)


        botonacceso.setOnClickListener {
            val nombre = editTextnombre.text.toString()
            val contrasena = editTextcontrasena.text.toString()

            validateData()



        }

    }

    fun validateData () {
        val nombreBinding = binding.editTextnombre.text.toString()
        val contrasenaBinding = binding.editTextcontrasena.text.toString()

        if (nombreBinding.isNotEmpty() && contrasenaBinding.isNotEmpty()){
            //Toast.makeText(this, "Bievenida  $nombreBinding",  Toast.LENGTH_LONG).show()  //mensaje emergente
            val intent = Intent(this,RegistroActivity::class.java) //intent relaciona entre activitys explicito
            intent.putExtra( "username",nombreBinding)//navega hasta id xml
            intent.putExtra("usercontrasena", contrasenaBinding.toInt())
            startActivity(intent)

        } else {
            Toast.makeText(this, "¡Complete todos  los campos!",  Toast.LENGTH_LONG).show()

        }
    }
}