package com.example.had

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.had.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {
    private val TAG = "Register"
    private var emailPattern = "[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}"
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.signUpButton.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        binding.findingButton.setOnClickListener {
            startActivity(Intent(this, FindingIDnPWActivity::class.java))
        }

        binding.loginButton.setOnClickListener {
            if(TextUtils.isEmpty(binding.loginEmailEditText.text) && TextUtils.isEmpty(binding.pwEditText.text)) {
                Toast.makeText(this, "이메일과 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
            }
            else {
                if(binding.loginEmailEditText.text.toString().trim { it <= ' ' }.matches(emailPattern.toRegex()) && !TextUtils.isEmpty(binding.pwEditText.text)) {
                    auth.signInWithEmailAndPassword(binding.loginEmailEditText.text.toString().trim(), binding.pwEditText.text.toString().trim())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success")
                                val user = auth.currentUser
                                startActivity(Intent(this, MainActivity::class.java))
                                //binding.loginEmailEditText.setBackgroundColor(R.drawable.white_edittext)
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.exception)
                                Toast.makeText(baseContext, "없는 이메일이거나 잘못된 비밀번호입니다.",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                }
                else {
                    if (TextUtils.isEmpty(binding.loginEmailEditText.text)) {
                        Toast.makeText(this, "이메일을 입력하세요.", Toast.LENGTH_SHORT).show()
                    }
                    else if (TextUtils.isEmpty(binding.pwEditText.text)) {
                        Toast.makeText(this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(this, "이메일 형식으로 입력하세요.", Toast.LENGTH_SHORT).show()
                        //binding.loginEmailEditText.setBackgroundResource(R.drawable.red_edittext)
                    }
                }
            }
        }
    }

}