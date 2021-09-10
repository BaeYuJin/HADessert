package com.example.had.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.had.databinding.ActivityMainBinding
import com.example.had.databinding.ActivitySetNowLocationBinding

class SetNowLocationActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySetNowLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetNowLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.setLocationNowBackButton2.setOnClickListener {
            startActivity(Intent(this, SetLocationActivity::class.java))
        }
    }
}