package com.example.had.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.had.R
import com.example.had.databinding.ActivityMainBinding
import com.example.had.databinding.ActivitySetLocationBinding

class SetLocationActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySetLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.setLocationBackButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}