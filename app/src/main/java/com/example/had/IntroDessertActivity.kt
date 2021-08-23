package com.example.had

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.had.databinding.ActivityIntroDessertBinding

class IntroDessertActivity : AppCompatActivity() {
    private lateinit var binding : ActivityIntroDessertBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroDessertBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}