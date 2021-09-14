package com.example.had.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.example.had.KakaoApi
import com.example.had.KakaoApiRetrofitClient
import com.example.had.R
import com.example.had.adapter.RecyclerAdapterDessert
import com.example.had.databinding.ActivitySearchBinding
import com.example.had.dataclass.DataDessert
import com.example.had.dataclass.KakaoData
import com.example.had.dataclass.Meta
import retrofit2.Call
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private val kakaoApi = KakaoApiRetrofitClient.apiService
    val list = mutableListOf<DataDessert>()
    val adapter = RecyclerAdapterDessert(list)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainSearchView2.setOnClickListener {
            finish()
        }

        binding.textView4.text = intent.getStringExtra("word")

        callKakaoKeyword(intent.getStringExtra("word").toString())

        //val adapter = RecyclerAdapterDessert(list)
        binding.dessertRv.adapter = adapter

    }
    fun callKakaoKeyword(address: String){
        val kakao = MutableLiveData<KakaoData>()

        kakaoApi.getkakaoAddress(KakaoApi.API_KEY, address,  "CE7")
            .enqueue(object:retrofit2.Callback<KakaoData> {
                override fun onResponse(call: Call<KakaoData>, response: Response<KakaoData>) {
                    kakao.value = response.body()
                    var count = if(kakao.value!!.meta.pageable_count>15)
                        14
                    else kakao.value!!.meta.pageable_count


                    Log.d("Test", "Raw: ${response.raw()}")
                    Log.d("Test", "Body: ${response.body()}")
                    Log.d("Test", "${kakao.value!!.meta.pageable_count}")
                    Log.d("Test", "${kakao.value!!.meta.total_count}")

                    for (i in 0 until count){
                        Log.d("Test", "${kakao.value!!.documents[i].place_name}")
                        Log.d("Test", "${kakao.value!!.documents[i].address_name}")
                        Log.d("Test", "${kakao.value!!.documents[i].road_address_name}")
                        Log.d("Test", "${kakao.value!!.documents[i].phone}")
                        list.add(DataDessert(null,"${kakao.value!!.documents[i].place_name}", "${kakao.value!!.documents[i].address_name}", "${kakao.value!!.documents[i].phone}"))
                        if ("${kakao.value!!.documents[i].category_name}" == "음식점 > 카페 > 커피전문점")
                            list.set(i, DataDessert(ContextCompat.getDrawable(applicationContext, R.drawable.coffee)!!,"${kakao.value!!.documents[i].place_name}","${kakao.value!!.documents[i].address_name}", "${kakao.value!!.documents[i].phone}"))
                        else if ("${kakao.value!!.documents[i].category_name}" == "음식점 > 카페 > 테마카페 > 디저트카페")
                            list.set(i, DataDessert(ContextCompat.getDrawable(applicationContext, R.drawable.cake)!!,"${kakao.value!!.documents[i].place_name}","${kakao.value!!.documents[i].address_name}", "${kakao.value!!.documents[i].phone}"))
                        else
                            list.set(i, DataDessert(ContextCompat.getDrawable(applicationContext, R.drawable.bread)!!,"${kakao.value!!.documents[i].place_name}","${kakao.value!!.documents[i].address_name}", "${kakao.value!!.documents[i].phone}"))
                    }
                    if (count == 0) {
                        binding.textView2.setVisibility(View.VISIBLE)
                        binding.dessertRv.setVisibility(View.INVISIBLE)
                    }
                    else {
                        binding.textView2.setVisibility(View.INVISIBLE)
                        binding.dessertRv.setVisibility(View.VISIBLE)
                    }
                    adapter.notifyDataSetChanged()
                }
                override fun onFailure(call: Call<KakaoData>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }
}
