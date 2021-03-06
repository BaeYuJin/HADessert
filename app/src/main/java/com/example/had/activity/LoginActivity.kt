package com.example.had.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.had.FireStorageViewModel
import com.example.had.PreferenceUtil
import com.example.had.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {
    private val TAG = "Register"
    private var emailPattern = "[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}"
    private lateinit var auth: FirebaseAuth
    private val viewModel: FireStorageViewModel by viewModels()
    var mBackWait:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.saveIDCheckBox.isChecked = PreferenceUtil.getUserId(this) != ""

        val s = PreferenceUtil.getUserId(this)
        binding.loginEmailEditText.setText(s)

        binding.signUpButton.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        binding.changingPWButton.setOnClickListener {
            startActivity(Intent(this, FindingIDnPWActivity::class.java))
        }

        binding.loginButton.setOnClickListener {
            if(binding.saveIDCheckBox.isChecked) {
                PreferenceUtil.setUserId(this, binding.loginEmailEditText.text.toString().trim())
            } else {
                PreferenceUtil.setUserId(this, "")
            }
            if(binding.autoLoginCheckBox.isChecked){
                PreferenceUtil.setAutoLogin(this, "true".toString())

            } else {
                PreferenceUtil.setAutoLogin(this, "false")
            }
            if(TextUtils.isEmpty(binding.loginEmailEditText.text) && TextUtils.isEmpty(binding.pwEditText.text)) {
                Toast.makeText(this, "???????????? ??????????????? ???????????????.", Toast.LENGTH_SHORT).show()
            }
            else {
                if(binding.loginEmailEditText.text.toString().trim { it <= ' ' }.matches(emailPattern.toRegex()) && !TextUtils.isEmpty(binding.pwEditText.text)) {
                    auth.signInWithEmailAndPassword(binding.loginEmailEditText.text.toString().trim(), binding.pwEditText.text.toString().trim())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success")
                                val user = auth.currentUser
                                viewModel.setImageFile(this)
                                Handler().postDelayed(Runnable {
                                    //????????? ??? ????????? ?????? ??????
                                    startActivity(Intent(this, MainActivity::class.java))
                                }, 1200)
                                //binding.loginEmailEditText.setBackgroundColor(R.drawable.white_edittext)
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.exception)
                                Toast.makeText(baseContext, "???????????? ?????? ?????????????????? ????????? ?????????????????????.",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                }
                else {
                    if (TextUtils.isEmpty(binding.loginEmailEditText.text)) {
                        Toast.makeText(this, "???????????? ???????????????.", Toast.LENGTH_SHORT).show()
                    }
                    else if (TextUtils.isEmpty(binding.pwEditText.text)) {
                        Toast.makeText(this, "??????????????? ???????????????.", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(this, "????????? ???????????? ???????????????.", Toast.LENGTH_SHORT).show()
                        //binding.loginEmailEditText.setBackgroundResource(R.drawable.red_edittext)
                    }
                }
            }
        }
    }
    override fun onBackPressed() {
        if(System.currentTimeMillis() - mBackWait >=2000 ) {
            mBackWait = System.currentTimeMillis()
            Toast.makeText(this,"???????????? ????????? ?????? ??? ????????? ???????????????.",Toast.LENGTH_LONG).show()
        } else {
            finish()
        }
    }
}