package com.example.had.activity


//import com.example.had.databinding.ActivitySetNowLocationBinding

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.had.FireStorageViewModel
import com.example.had.adapter.RecyclerAdapterStar
import com.example.had.databinding.ActivityMainBinding
import com.example.had.dataclass.StarData
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import noman.googleplaces.*
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


//import com.example.had.databinding.ActivitySetNowLocationBinding
//import java.io.File
//import com.naver.maps.map.NaverMapSdk


class MainActivity : AppCompatActivity() , OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback,
    PlacesListener {

    private lateinit var binding: ActivityMainBinding

    var mBackWait:Long = 0
    private val storage = Firebase.storage
    private val user = Firebase.auth.currentUser
    private val storageRef = storage.reference
    private val db = Firebase.firestore
    private val imageRefChild = storageRef.child("profileImages/${user?.uid}.jpg")
    private val imageRefUrl = storage.getReferenceFromUrl("gs://hadessert-c6192.appspot.com/profileImages/${user?.uid}.jpg")
    private val viewModel: FireStorageViewModel by viewModels()

    private val starlist = mutableListOf<StarData>()
    private lateinit var recyclerAdapterStar: RecyclerAdapterStar

    private var mMap: GoogleMap? = null
    private var currentMarker: Marker? = null

    private val TAG = "googlemap_example"
    private val GPS_ENABLE_REQUEST_CODE = 2001
    private val UPDATE_INTERVAL_MS = 1000 // 1초

    private val FASTEST_UPDATE_INTERVAL_MS = 500 // 0.5초


    // onRequestPermissionsResult에서 수신된 결과에서 ActivityCompat.requestPermissions를 사용한 퍼미션 요청을 구별하기 위해 사용됩니다.
    private val PERMISSIONS_REQUEST_CODE = 100
    var needRequest = false


    // 앱을 실행하기 위해 필요한 퍼미션을 정의합니다.
    var REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) // 외부 저장소


    var mCurrentLocation: Location? = null
    var currentPosition: LatLng? = null


    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var locationRequest: LocationRequest? = null
    private var location: Location? = null

    private var mLayout: View? = null

    var previous_marker : List<Marker> = ArrayList()

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

        window.setFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )

        mLayout = binding.root
        locationRequest = LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(UPDATE_INTERVAL_MS.toLong())
            .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS.toLong())
        val locationBuilder = LocationSettingsRequest.Builder()
        locationBuilder.addLocationRequest(locationRequest)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val mapFragment = supportFragmentManager
            .findFragmentById(com.example.had.R.id.main_map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        previous_marker = ArrayList()
        //val textView: TextView = binding.hotPlaceTextView
        //textView.setOnClickListener { showPlaceInformation(currentPosition!!) }

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
            locaBuilder.setIcon(com.example.had.R.drawable.mainicon)
            locaBuilder.show()
        }

        /*val mapView = MapView(this)
        val mapViewContainer = binding.mapView as ViewGroup
        val locationIntent = intent

        var location: Location? = null
        var currentPosition: com.google.android.gms.maps.model.LatLng? = null

        val nowLatitude = locationIntent.getDoubleExtra("latitude", 0.0)
        val nowLongitude = locationIntent.getDoubleExtra("longitude", 0.0)
        currentPosition =
            com.google.android.gms.maps.model.LatLng(nowLatitude, nowLongitude)

        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.53737528, 127.0055763310), true)
        mapViewContainer.addView(mapView)*/

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

        /*window.setFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )
        mLayout = binding.root
        locationRequest = LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(SetNowLocationActivity.UPDATE_INTERVAL_MS.toLong())
            .setFastestInterval(SetNowLocationActivity.FASTEST_UPDATE_INTERVAL_MS.toLong())
        val locationBuilder = LocationSettingsRequest.Builder()
        locationBuilder.addLocationRequest(locationRequest)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.main_map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)*/

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

        initRecycler()

        val gridLayoutManager = GridLayoutManager(applicationContext, 1)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.starRv.layoutManager = gridLayoutManager
    }

    private fun initRecycler() {
        recyclerAdapterStar = RecyclerAdapterStar(this)
        binding.starRv.adapter = recyclerAdapterStar

        if (user != null) {
            db.collection(user.uid).document("성북동빵공장").get().addOnSuccessListener { documentSnapshot ->
                var star = documentSnapshot.toObject<StarData>()
                if (star != null) {
                    Log.d("test", star.name)
                    Log.d("test", star.address)
                    Log.d("test", star.phone)

                    starlist.apply {
                        add(StarData(img = com.example.had.R.drawable.cake, name = star.name , address = star.address , phone = star.phone))
                        recyclerAdapterStar.starlist = starlist
                        recyclerAdapterStar.notifyDataSetChanged()
                    }
                }
            }
            db.collection(user.uid).document("블랑제메종북악").get().addOnSuccessListener { documentSnapshot ->
                var star2 = documentSnapshot.toObject<StarData>()
                if (star2 != null) {
                    Log.d("test", star2.name)
                    Log.d("test", star2.address)
                    Log.d("test", star2.phone)

                    starlist.apply {
                        add(StarData(img = com.example.had.R.drawable.cake, name = star2.name , address = star2.address , phone = star2.phone))
                        recyclerAdapterStar.starlist = starlist
                        recyclerAdapterStar.notifyDataSetChanged()
                    }
                }
            }
            db.collection(user.uid).document("수연산방").get().addOnSuccessListener { documentSnapshot ->
                var star3 = documentSnapshot.toObject<StarData>()
                if (star3 != null) {
                    Log.d("test", star3.name)
                    Log.d("test", star3.address)
                    Log.d("test", star3.phone)

                    starlist.apply {
                        add(StarData(img = com.example.had.R.drawable.bread, name = star3.name , address = star3.address , phone = star3.phone))
                        recyclerAdapterStar.starlist = starlist
                        recyclerAdapterStar.notifyDataSetChanged()
                    }
                }
            }
            db.collection(user.uid).document("해로커피").get().addOnSuccessListener { documentSnapshot ->
                var star4 = documentSnapshot.toObject<StarData>()
                if (star4 != null) {
                    Log.d("test", star4.name)
                    Log.d("test", star4.address)
                    Log.d("test", star4.phone)

                    starlist.apply {
                        add(StarData(img = com.example.had.R.drawable.coffee, name = star4.name , address = star4.address , phone = star4.phone))
                        recyclerAdapterStar.starlist = starlist
                        recyclerAdapterStar.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        Log.d(TAG, "onMapReady :")
        mMap = googleMap

        //런타임 퍼미션 요청 대화상자나 GPS 활성 요청 대화상자 보이기전에
        //지도의 초기위치를 서울로 이동
        setDefaultLocation()


        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
            hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED
        ) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)
            startLocationUpdates() // 3. 위치 업데이트 시작
        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    REQUIRED_PERMISSIONS[0]
                )
            ) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Snackbar.make(
                    mLayout!!, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.",
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction("확인") { // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                        ActivityCompat.requestPermissions(
                            this, REQUIRED_PERMISSIONS,
                            PERMISSIONS_REQUEST_CODE
                        )
                    }.show()
            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE
                )
            }
        }
        mMap!!.uiSettings.isMyLocationButtonEnabled = true
        // 현재 오동작을 해서 주석처리

        //mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mMap!!.setOnMapClickListener {
            Log.d(
                TAG,
                "onMapClick :"
            )
            currentPosition?.let { it1 -> showPlaceInformation(it1) }
        }
    }

    var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            val locationList = locationResult.locations
            if (locationList.size > 0) {
                location = locationList[locationList.size - 1]
                //location = locationList.get(0);
                currentPosition = LatLng(location!!.latitude, location!!.longitude)
                val markerTitle = getCurrentAddress(currentPosition!!)
                val markerSnippet =
                    "위도:" + location!!.latitude.toString() + " 경도:" + location!!.longitude.toString()
                Log.d(TAG, "onLocationResult : $markerSnippet")


                //현재 위치에 마커 생성하고 이동
                setCurrentLocation(location, markerTitle, markerSnippet)
                mCurrentLocation = location
            }
        }
    }

    private fun startLocationUpdates() {
        if (!checkLocationServicesStatus()) {
            Log.d(TAG, "startLocationUpdates : call showDialogForLocationServiceSetting")
            showDialogForLocationServiceSetting()
        } else {
            val hasFineLocationPermission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED ||
                hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED
            ) {
                Log.d(TAG, "startLocationUpdates : 퍼미션 안가지고 있음")
                return
            }
            Log.d(TAG, "startLocationUpdates : call mFusedLocationClient.requestLocationUpdates")
            mFusedLocationClient!!.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.myLooper()
            )
            if (checkPermission()) mMap!!.isMyLocationEnabled = true
        }
    }

    fun getCurrentAddress(latlng: LatLng): String {
        //지오코더... GPS를 주소로 변환
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address>?
        try {
            addresses = geocoder.getFromLocation(
                latlng.latitude,
                latlng.longitude,
                1
            )
        } catch (ioException: IOException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show()
            return "지오코더 서비스 사용불가"
        } catch (illegalArgumentException: IllegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show()
            return "잘못된 GPS 좌표"
        }
        if (addresses == null || addresses.isEmpty()) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show()
            return "주소 미발견"
        } else {
            val address = addresses[0]
            return address.getAddressLine(0).toString()
        }
    }

    private fun checkLocationServicesStatus(): Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
    }

    fun setCurrentLocation(location: Location?, markerTitle: String?, markerSnippet: String?) {
        if (currentMarker != null) currentMarker!!.remove()
        val currentLatLng = LatLng(
            location!!.latitude, location.longitude
        )
        val markerOptions = MarkerOptions()
        markerOptions.position(currentLatLng)
        markerOptions.title(markerTitle)
        markerOptions.snippet(markerSnippet)
        markerOptions.draggable(true)
        //currentMarker = mMap!!.addMarker(markerOptions)
        val cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng)
        mMap!!.moveCamera(cameraUpdate)
    }

    private fun setDefaultLocation() {
        //디폴트 위치, Seoul
        val DEFAULT_LOCATION = LatLng(37.56, 126.97)
        val markerTitle = "위치정보 가져올 수 없음"
        val markerSnippet = "위치 퍼미션과 GPS 활성 여부 확인하세요"
        if (currentMarker != null) currentMarker!!.remove()
        val markerOptions = MarkerOptions()
        markerOptions.position(DEFAULT_LOCATION)
        markerOptions.title(markerTitle)
        markerOptions.snippet(markerSnippet)
        markerOptions.draggable(true)
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        //currentMarker = mMap!!.addMarker(markerOptions)
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15f)
        mMap!!.moveCamera(cameraUpdate)
    }

    //여기부터는 런타임 퍼미션 처리을 위한 메소드들
    private fun checkPermission(): Boolean {
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED
    }

    /*
    * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
    */
    override fun onRequestPermissionsResult(
        permsRequestCode: Int,
        permissions: Array<String>,
        grandResults: IntArray
    ) {
        super.onRequestPermissionsResult(permsRequestCode, permissions, grandResults)
        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.size == REQUIRED_PERMISSIONS.size) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
            var check_result = true


            // 모든 퍼미션을 허용했는지 체크합니다.
            for (result: Int in grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false
                    break
                }
            }
            if (check_result) {

                // 퍼미션을 허용했다면 위치 업데이트를 시작합니다.
                startLocationUpdates()
            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        REQUIRED_PERMISSIONS[0]
                    )
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        REQUIRED_PERMISSIONS[1]
                    )
                ) {


                    // 사용자가 거부만 선택한 경우에는 앱을 다시 실행하여 허용을 선택하면 앱을 사용할 수 있습니다.
                    Snackbar.make(
                        mLayout!!, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요. ",
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction(
                        "확인"
                    ) { finish() }.show()
                } else {


                    // "다시 묻지 않음"을 사용자가 체크하고 거부를 선택한 경우에는 설정(앱 정보)에서 퍼미션을 허용해야 앱을 사용할 수 있습니다.
                    Snackbar.make(
                        mLayout!!, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ",
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction(
                        "확인"
                    ) { finish() }.show()
                }
            }
        }
    }

    //여기부터는 GPS 활성화를 위한 메소드들
    private fun showDialogForLocationServiceSetting() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("위치 서비스 비활성화")
        builder.setMessage(
            "앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                    + "위치 설정을 수정하실래요?"
        )
        builder.setCancelable(true)
        builder.setPositiveButton("설정") { dialog, id ->
            val callGPSSettingIntent =
                Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivityForResult(
                callGPSSettingIntent,
                GPS_ENABLE_REQUEST_CODE
            )
        }
        builder.setNegativeButton(
            "취소"
        ) { dialog, id -> dialog.cancel() }
        builder.create().show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GPS_ENABLE_REQUEST_CODE ->
                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {
                        Log.d(TAG, "onActivityResult : GPS 활성화 되있음")
                        needRequest = true
                        return
                    }
                }
        }
    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
        if (checkPermission()) {
            Log.d(TAG, "onStart : call mFusedLocationClient.requestLocationUpdates")
            mFusedLocationClient!!.requestLocationUpdates(locationRequest, locationCallback, null)
            if (mMap != null) mMap!!.isMyLocationEnabled = true
        }
    }

    override fun onStop() {
        super.onStop()
        if (mFusedLocationClient != null) {
            Log.d(TAG, "onStop : call stopLocationUpdates")
            mFusedLocationClient!!.removeLocationUpdates(locationCallback)
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

    companion object {
        private val TAG = "googlemap_example"
        private val GPS_ENABLE_REQUEST_CODE = 2001
        private val UPDATE_INTERVAL_MS = 1000 // 1초
        private val FASTEST_UPDAT = 1000
        private val E_INTERVAL_MS = 10000 // 60초

        // onRequestPermissionsResult에서 수신된 결과에서 ActivityCompat.requestPermissions를 사용한 퍼미션 요청을 구별하기 위해 사용됩니다.
        private val PERMISSIONS_REQUEST_CODE = 100
    }

    private fun showPlaceInformation(location: LatLng) {
        mMap!!.clear() //지도 클리어
        //previous_marker?.clear() //지역정보 마커 클리어
        NRPlaces.Builder()
            .listener(this)
            .key("AIzaSyBcgi7r8uJ46JLxHTbWND9c3Ew6wN8zk5A")
            .latlng(location.latitude, location.longitude) //현재 위치
            .radius(500) //500 미터 내에서 검색
            .type(PlaceType.CAFE) //음식점
            .build()
            .execute()
    }

    override fun onPlacesFailure(e: PlacesException?) {
        TODO("Not yet implemented")
    }

    override fun onPlacesStart() {
        TODO("Not yet implemented")
    }

    override fun onPlacesSuccess(places: List<Place> ) {
        runOnUiThread {
            for (place in places) {
                val latLng = LatLng(
                    place.latitude, place.longitude
                )
                val markerSnippet = getCurrentAddress(latLng)
                val markerOptions = MarkerOptions()
                markerOptions.position(latLng)
                markerOptions.title(place.name)
                markerOptions.snippet(markerSnippet)
                val item = mMap!!.addMarker(markerOptions)
                //previous_marker.add(item)
            }

            //중복 마커 제거
            val hashSet = HashSet<Marker>()
            hashSet.addAll(previous_marker!!)
            //previous_marker.clear()
            //previous_marker.addAll(hashSet)
        }
    }

    override fun onPlacesFinished() {
        TODO("Not yet implemented")
    }

}