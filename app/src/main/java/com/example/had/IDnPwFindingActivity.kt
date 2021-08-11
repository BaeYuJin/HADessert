package com.example.had

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.had.databinding.ActivityIdpwfindingBinding

class IDnPwFindingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIdpwfindingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIdpwfindingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}