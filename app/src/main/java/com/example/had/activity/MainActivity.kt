package com.example.had.activity


import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.had.databinding.ActivityMainBinding
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.example.had.FireStorageViewModel
import com.example.had.fragment.HotPlaceFragment
import com.example.had.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
//import com.example.had.databinding.ActivitySetNowLocationBinding
import net.daum.android.map.MapView
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import java.io.File
import org.w3c.dom.Text

//import com.naver.maps.map.NaverMapSdk

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var mBackWait:Long = 0
    private val storage = Firebase.storage
    private val user = Firebase.auth.currentUser
    private val storageRef = storage.reference
    private val imageRefChild = storageRef.child("profileImages/${user?.uid}.jpg")
    private val imageRefUrl = storage.getReferenceFromUrl("gs://hadessert-c6192.appspot.com/profileImages/${user?.uid}.jpg")
    private val viewModel: FireStorageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        downloadFirebaseImage(imageRefUrl)

        viewModel.setImage(binding.profileImage)

        // 검색 창 클릭 시 액티비티 이동
        binding.mainSearchView.setOnClickListener {
            startActivity(Intent(this, Search1Activity::class.java))
        }

        binding.profileImage.setOnClickListener{
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.textView8.setOnClickListener {
            startActivity(Intent(this, IntroDessertActivity::class.java))
        }

        val secondIntent = intent
        var tv: TextView = binding.mainTextView
        tv.text = secondIntent.getStringExtra("location")
        if (tv.text.isNotEmpty())
            binding.textView9.isVisible = false

        tv.setOnClickListener {
            val locaBuilder = AlertDialog.Builder(this)
            locaBuilder.setTitle("현재 위치")
            if (tv.text.isEmpty())
                locaBuilder.setMessage("위치를 설정하세요.")
            else
                locaBuilder.setMessage(tv.text)
            locaBuilder.setNeutralButton("확인") { _: DialogInterface?, _: Int -> }
            locaBuilder.setIcon(R.drawable.appicon)
            locaBuilder.show()
        }

        /*val mapView = MapView(this)  카카오매배배배배배뱁
        val mapViewContainer = binding.mapView as ViewGroup
        mapViewContainer.addView(mapView)*/

        /*
        NaverMapSdk.getInstance(this).client =
                NaverMapSdk.NaverCloudPlatformClient("enltqog9k1")

         */

        /*supportFragmentManager.beginTransaction()
            .replace(R.id.hotPlaceMap_fragment, HotPlaceFragment())
            .commit()
         */

        binding.setLocationTextView.setOnClickListener {
            startActivity(Intent(this, SetLocationActivity::class.java))
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("마카롱")
        builder.setMessage("마카롱(macaron)은 작고 동그란 모양의 머랭 크러스트(meringue crust) 사이에 잼, 가나슈(ganache), 버터크림 등의 필링(filling)을 채워 만든 프랑스 쿠키이다.")
        builder.setNeutralButton("확인",{dialogInterface: DialogInterface?, i:Int-> })
        binding.imageView9.setOnClickListener {
            builder.show()
        }
        val builder2 = AlertDialog.Builder(this)
        builder2.setTitle("다쿠아즈")
        builder2.setMessage("다쿠아즈(Dacquoise)는 프랑스 누벨아키텐 레지옹(Région) 랑드 데파르트망(Département) 닥스 지방에 전해 내려오는 겉이 바삭하고 속이 부드럽고 폭신한 과자이다.")
        builder2.setNeutralButton("확인",{dialogInterface: DialogInterface?, i:Int-> })
        binding.imageView10.setOnClickListener {
            builder2.show()
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

    private fun downloadFirebaseImage(imageRef: StorageReference?){
        val localFile = File.createTempFile("images", "jpg")

        imageRef?.getFile(localFile)?.addOnSuccessListener {
            // Local temp file has been created
            //R.drawable.profile. = localFile
        }?.addOnFailureListener {
            // Handle any errors
        }
    }
}