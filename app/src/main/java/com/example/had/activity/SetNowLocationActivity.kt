package com.example.had.activity

import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.had.databinding.ActivitySetNowLocationBinding

class SetNowLocationActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySetNowLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetNowLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}