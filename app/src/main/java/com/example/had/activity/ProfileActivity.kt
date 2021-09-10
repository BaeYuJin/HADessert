package com.example.had.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.had.AppInfoActivity
import com.example.had.PreferenceUtil
import com.example.had.databinding.ActivityProfileBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
//import com.google.firebase.storage.ktx.storage

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val storage = Firebase.storage

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.changeProfile.setOnClickListener{
            startActivity(Intent(this, ChangeProfileActivity::class.java))
        }

        binding.likedStore.setOnClickListener{
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.logout.setOnClickListener{
            Firebase.auth.signOut()
            PreferenceUtil.setAutoLogin(this, "false")
            Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

}