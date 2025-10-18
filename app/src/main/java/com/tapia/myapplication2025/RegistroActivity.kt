package com.tapia.myapplication2025

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tapia.myapplication2025.databinding.ActivityRegistroBinding

class RegistroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}
