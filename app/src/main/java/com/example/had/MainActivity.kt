package com.example.had


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import com.example.had.databinding.ActivityMainBinding
import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.test.NaverSearchPlace

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var mBackWait:Long = 0

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

        /*val thread = Thread {
                var apiExamSearchBlog = NaverSearchPlace()
                apiExamSearchBlog.main()
            }.start()*/

    }
    override fun onBackPressed() {
        if(System.currentTimeMillis() - mBackWait >=2000 ) {
            mBackWait = System.currentTimeMillis()
            Toast.makeText(this,"뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show()
        } else {
            finishAffinity() //액티비티 종료
        }
    }
}