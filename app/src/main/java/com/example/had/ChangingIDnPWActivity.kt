package com.example.had

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.had.databinding.ActivityChangingidpwBinding

class ChangingIDnPWActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangingidpwBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangingidpwBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}