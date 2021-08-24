package com.example.had

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.had.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.changeProfile.setOnClickListener{
            startActivity(Intent(this, ChangeProfileActivity::class.java))
        }


        binding.likedStore.setOnClickListener{
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.appSetting.setOnClickListener{
            startActivity(Intent(this, AppSettingActivity::class.java))
        }

        binding.appInfo.setOnClickListener{
            startActivity(Intent(this, ProfileActivity::class.java))
        }

    }
}