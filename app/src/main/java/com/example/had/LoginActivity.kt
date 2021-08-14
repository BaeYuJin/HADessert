package com.example.had

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.had.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpButton.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        binding.findingButton.setOnClickListener {
            startActivity(Intent(this, IDnPwFindingActivity::class.java))
        }

        binding.loginButton.setOnClickListener {
            if(TextUtils.isEmpty(binding.idEditText.text) && TextUtils.isEmpty(binding.pwEditText.text)) {
                Toast.makeText(this, "아이디와 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
            }
            else if (TextUtils.isEmpty(binding.idEditText.text)) {
                Toast.makeText(this, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show()
            }
            else if(TextUtils.isEmpty(binding.pwEditText.text)) {
                Toast.makeText(this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
            }
            else
                startActivity(Intent(this, MainActivity::class.java))
        }
    }

}