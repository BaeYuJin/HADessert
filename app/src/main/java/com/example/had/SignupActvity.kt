package com.example.had

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.had.databinding.ActivitySignupBinding

class SignupActvity : AppCompatActivity() {
    val binding = ActivitySignupBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        binding.signupbutton.setOnClickListener {

            var name = binding.name.text
            var id = binding.id.text
            var pw = binding.pw.text
            var birth = binding.birth.text

            Log.d("Name : ", name.toString())
        }
    }

}