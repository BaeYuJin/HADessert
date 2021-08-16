package com.example.had

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.had.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private var emailPattern = "[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}"

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
            if(TextUtils.isEmpty(binding.loginEmailEditText.text) && TextUtils.isEmpty(binding.pwEditText.text)) {
                Toast.makeText(this, "이메일과 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
             }
            else {
                if(binding.loginEmailEditText.text.toString().trim { it <= ' ' }.matches(emailPattern.toRegex()) && !TextUtils.isEmpty(binding.pwEditText.text)) {
                    startActivity(Intent(this, MainActivity::class.java))
                    binding.loginEmailEditText.setBackgroundColor(R.drawable.white_edittext)
                }
                else {
                    Toast.makeText(this, "이메일 형식으로 입력하세요.", Toast.LENGTH_SHORT).show()
                    binding.loginEmailEditText.setBackgroundResource(R.drawable.red_edittext)
                }
                if (TextUtils.isEmpty(binding.loginEmailEditText.text)) {
                    Toast.makeText(this, "이메일을 입력하세요.", Toast.LENGTH_SHORT).show()
                }
                else if(TextUtils.isEmpty(binding.pwEditText.text)) {
                    Toast.makeText(this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}