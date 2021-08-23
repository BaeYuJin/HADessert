package com.example.had


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import com.example.had.databinding.ActivityMainBinding
import android.graphics.Typeface
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        binding.searchView.setOnCloseListener {
         startActivity(Intent(this, 최근검색어&인기검색어 액티비티::class.java))
        }*/

        binding.profileImageButton.setOnClickListener{
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.introDessertView.setOnClickListener {
            startActivity(Intent(this, IntroDessertActivity::class.java))
        }

    }
}