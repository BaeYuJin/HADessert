package com.example.had.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.had.databinding.ActivityMainBinding
import android.widget.Toast
import com.example.had.fragment.HotPlaceFragment
import com.example.had.R
import com.naver.maps.map.NaverMapSdk

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var mBackWait:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 검색 창 클릭 시 액티비티 이동
        binding.mainSearchView.setOnClickListener {
            startActivity(Intent(this, Search1Activity::class.java))
        }

        binding.profileImage.setOnClickListener{
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.introDessertView.setOnClickListener {
            startActivity(Intent(this, IntroDessertActivity::class.java))
        }

        /**/
        NaverMapSdk.getInstance(this).client =
                NaverMapSdk.NaverCloudPlatformClient("enltqog9k1")

        supportFragmentManager.beginTransaction()
            .replace(R.id.hotPlaceMap_fragment, HotPlaceFragment())
            .commit()

        binding.setLocationTextView.setOnClickListener {
            startActivity(Intent(this, SetLocationActivity::class.java))
        }
    }

    override fun onBackPressed() {
        if(System.currentTimeMillis() - mBackWait >=2000 ) {
            mBackWait = System.currentTimeMillis()
            Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show()
        } else {
            finishAffinity() //액티비티 종료
        }
    }
}