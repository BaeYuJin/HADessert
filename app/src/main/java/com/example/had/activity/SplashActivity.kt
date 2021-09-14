package com.example.had.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.had.FireStorageViewModel
import com.example.had.PreferenceUtil
import com.example.had.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SplashActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val viewModel: FireStorageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        auth = Firebase.auth

        val user = auth.currentUser
        val handler = Handler()
        if ((user != null ) && (PreferenceUtil.getAutoLogin(this) == "true")) {
            handler.postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                viewModel.getImageRef()
                startActivity(intent)
                finish()
            }, DURATION)
        } else {
            handler.postDelayed({
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }, DURATION)
        }
    }

    companion object {
        private const val DURATION : Long = 800
    }


}