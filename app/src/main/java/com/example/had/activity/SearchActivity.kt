package com.example.had.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.had.KakaoApi
import com.example.had.KakaoApiRetrofitClient
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainSearchView2.setOnClickListener {
            finish()
        }




        binding.textView4.text = intent.getStringExtra("word")

        callKakaoKeyword(intent.getStringExtra("word").toString())

        val adapter = RecyclerAdapterDessert(list)
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
                        list.add(DataDessert(null,"${kakao.value!!.documents[i].place_name}", "3.7km", "4.9", null, "999+"))
                    }
                }

                override fun onFailure(call: Call<KakaoData>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }
}
