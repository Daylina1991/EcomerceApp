package com.tapia.myapplication2025

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tapia.myapplication2025.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username")
        binding.textBienvenida.text = "¡Bienvenido a INDISE, $username!"
    }
}
