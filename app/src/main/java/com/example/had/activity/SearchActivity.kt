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
import com.example.test.NaverSearchPlace
import retrofit2.Call
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private val kakaoApi = KakaoApiRetrofitClient.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainSearchView2.setOnClickListener {
            finish()
        }

        val list = mutableListOf<DataDessert>()

        binding.textView4.text = intent.getStringExtra("word")

        /*val thread = Thread {
            var apiExamSearchBlog = NaverSearchPlace()
            apiExamSearchBlog.main(intent.getStringExtra("word").toString(), list)
        }.start()*/
        callKakaoKeyword(intent.getStringExtra("word").toString())

        val adapter = RecyclerAdapterDessert(list)
        binding.dessertRv.adapter = adapter

    }
    fun callKakaoKeyword(address: String){
        val kakao = MutableLiveData<KakaoData>()

        kakaoApi.getkakaoAddress(KakaoApi.API_KEY, address = address)
            .enqueue(object:retrofit2.Callback<KakaoData> {
                override fun onResponse(call: Call<KakaoData>, response: Response<KakaoData>) {
                    kakao.value = response.body()

                    //Log.i("kakao", "${kakao.value!!.documents[0].address_name}")
                    //System.out.println(kakao.value!!.documents[0].address_name)
                    Log.d("Test", "Raw: ${response.raw()}")
                    Log.d("Test", "Body: ${response.body()}")
                }

                override fun onFailure(call: Call<KakaoData>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }
}
