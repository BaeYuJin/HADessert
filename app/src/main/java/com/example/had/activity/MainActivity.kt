package com.example.had.activity


import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import com.example.had.databinding.ActivityMainBinding
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.had.FireStorageViewModel
import com.example.had.R
import com.example.had.adapter.RecyclerAdapterStar
import com.example.had.dataclass.StarData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
//import com.example.had.databinding.ActivitySetNowLocationBinding
import net.daum.mf.map.api.MapView
import net.daum.mf.map.api.MapPoint
import java.security.MessageDigest
import kotlin.system.exitProcess

//import com.example.had.databinding.ActivitySetNowLocationBinding
//import java.io.File
//import com.naver.maps.map.NaverMapSdk


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var mBackWait:Long = 0
    private val storage = Firebase.storage
    private val user = Firebase.auth.currentUser
    private val storageRef = storage.reference
    private val db = Firebase.firestore
    private val imageRefChild = storageRef.child("profileImages/${user?.uid}.jpg")
    private val imageRefUrl = storage.getReferenceFromUrl("gs://hadessert-c6192.appspot.com/profileImages/${user?.uid}.jpg")

    val PERMISSIONS_REQUEST_CODE = 100
    var REQUIRED_PERMISSIONS = arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION)
    private val viewModel: FireStorageViewModel by viewModels()

    private val starlist = mutableListOf<StarData>()
    private lateinit var recyclerAdapterStar: RecyclerAdapterStar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
            locaBuilder.setIcon(R.drawable.mainicon)
            locaBuilder.show()
        }

        val mapView = MapView(this)
        val mapViewContainer = binding.mapView as ViewGroup
        val locationIntent = intent

        var location: Location? = null
        var currentPosition: com.google.android.gms.maps.model.LatLng? = null

        val nowLatitude = locationIntent.getDoubleExtra("latitude", 0.0)
        val nowLongitude = locationIntent.getDoubleExtra("longitude", 0.0)
        currentPosition =
            com.google.android.gms.maps.model.LatLng(nowLatitude, nowLongitude)

        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.53737528, 127.0055763310), true)
        mapViewContainer.addView(mapView)

        /*val mapView = MapView(this)
        val mapViewContainer = binding.mapView as ViewGroup
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.53737528, 127.0055763310), true)
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

        binding.mapLocationButton.setOnClickListener {
            val locationIntent = intent

            val nowLatitude = locationIntent.getDoubleExtra("latitude", 0.0)
            val nowLongitude = locationIntent.getDoubleExtra("longitude", 0.0)

            val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)

            val uNowPosition = MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633)
            mapView.setMapCenterPoint(uNowPosition, true)
            mapViewContainer.addView(mapView)

            /*val marker = MapPOIItem()
            marker.apply {
                itemName = "서울시청"   // 마커 이름
                mapPoint = MapPoint.mapPointWithGeoCoord(37.5666805, 126.9784147)   // 좌표
                markerType = MapPOIItem.MarkerType.CustomImage          // 마커 모양 (커스텀)
                customImageResourceId = R.drawable.이미지               // 커스텀 마커 이미지
                selectedMarkerType = MapPOIItem.MarkerType.CustomImage  // 클릭 시 마커 모양 (커스텀)
                customSelectedImageResourceId = R.drawable.이미지       // 클릭 시 커스텀 마커 이미지
                isCustomImageAutoscale = false      // 커스텀 마커 이미지 크기 자동 조정
                setCustomImageAnchor(0.5f, 1.0f)    // 마커 이미지 기준점
            }
            mapView.addPOIItem(marker)*/

            /*if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
                val lm: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                try {
                    lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    val uNowPosition = MapPoint.mapPointWithGeoCoord(nowLatitude, nowLongitude)
                    mapView.setMapCenterPoint(uNowPosition, true)
                }catch(e: NullPointerException){
                    Log.e("LOCATION_ERROR", e.toString())
                    ActivityCompat.finishAffinity(this)

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    exitProcess(0)
                }
            }else{
                Toast.makeText(this, "위치 권한이 없습니다.", Toast.LENGTH_SHORT).show()
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE )
            }*/
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

        initRecycler()

        val gridLayoutManager = GridLayoutManager(applicationContext, 1)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.starRv.layoutManager = gridLayoutManager
    }

    private fun initRecycler() {
        recyclerAdapterStar = RecyclerAdapterStar(this)
        binding.starRv.adapter = recyclerAdapterStar
        var shopName : String =""
        var address : String =""
        var phone : String =""

        if (user != null) {
            db.collection(user.uid).document("성북동빵공장").get().addOnSuccessListener { documentSnapshot ->
                val star = documentSnapshot.toObject<StarData>()
                if (star != null) {
                    shopName = star.name
                    address = star.address
                    phone = star.phone
                    Log.d("test", star.name)
                    Log.d("test", star.address)
                    Log.d("test", star.phone)
                }
            }
        }

        starlist.apply {
            //add(StarData(img = R.drawable.bread, shop = "이름", address = "주소", tel = "010"))
            add(StarData(img = R.drawable.bread, name = "${shopName}", address = address , phone = phone))

            recyclerAdapterStar.starlist = starlist
            recyclerAdapterStar.notifyDataSetChanged()
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

    override fun onResume() {
        super.onResume()
        viewModel.setImage(binding.profileImage)
    }


}