package com.example.had.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.had.KakaoApiRetrofitClient
import com.example.had.R
import com.example.had.adapter.RecyclerAdapterDessert
import com.example.had.adapter.RecyclerAdapterStar
import com.example.had.databinding.ActivityStarBinding
import com.example.had.dataclass.StarData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class StarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStarBinding

    var mBackWait:Long = 0
    private val storage = Firebase.storage
    private val user = Firebase.auth.currentUser
    private val storageRef = storage.reference
    private val db = Firebase.firestore
    private val kakaoApi = KakaoApiRetrofitClient.apiService
    private val starlist = mutableListOf<StarData>()
    private lateinit var recyclerAdapterStar: RecyclerAdapterStar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initRecycler()
    }

    private fun initRecycler() {
        recyclerAdapterStar = RecyclerAdapterStar(this)
        binding.starReView.adapter = recyclerAdapterStar

        if (user != null) {
            db.collection(user.uid).document("성북동빵공장").get().addOnSuccessListener { documentSnapshot ->
                var star = documentSnapshot.toObject<StarData>()
                if (star != null) {
                    Log.d("test", star.name)
                    Log.d("test", star.address)
                    Log.d("test", star.phone)

                    starlist.apply {
                        add(StarData(img = R.drawable.cake, name = star.name , address = star.address , phone = star.phone))
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
                        add(StarData(img = R.drawable.cake, name = star2.name , address = star2.address , phone = star2.phone))
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
                        add(StarData(img = R.drawable.bread, name = star3.name , address = star3.address , phone = star3.phone))
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
                        add(StarData(img = R.drawable.coffee, name = star4.name , address = star4.address , phone = star4.phone))
                        recyclerAdapterStar.starlist = starlist
                        recyclerAdapterStar.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}