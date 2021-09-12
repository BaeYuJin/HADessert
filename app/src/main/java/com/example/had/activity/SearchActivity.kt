package com.example.had.activity

import android.os.Bundle
import android.util.Log
import android.view.View
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

        list.add(DataDessert(null, "학림다방", "서울 종로구 명륜4가 94-2", "4.9"))
        list.add(DataDessert(null, "카페키이로", "서울 종로구 명륜4가 154-2", "4.9"))
        list.add(DataDessert(null, "칠린", "서울 종로구 명륜4가 167-2", "4.9"))
        list.add(DataDessert(null, "블루룸", "서울 종로구 명륜4가 66-3", "4.9"))
        list.add(DataDessert(null, "카페혜화동", "서울 종로구 명륜4가 145", "4.9"))
        list.add(DataDessert(null, "혜화역4번출구카페제이드", "서울 종로구 혜화동 136", "4.9"))
        list.add(DataDessert(null, "서화커피", "서울 종로구 명륜4가 140-9", "4.9"))
        list.add(DataDessert(null, "스노브 대학로점", "서울 종로구 명륜4가 85-1", "4.9"))
        list.add(DataDessert(null, "낫컴플리트", "서울 종로구 명륜2가 150-2", "4.9"))
        list.add(DataDessert(null, "브라운에비뉴", "서울 종로구 명륜2가 237", "4.9"))



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
                        list.add(DataDessert(null,"${kakao.value!!.documents[i].place_name}", "3.7km", "4.9"))
                    }
                    if (count == 0) {
                        binding.textView2.setVisibility(View.VISIBLE)
                        binding.dessertRv.setVisibility(View.INVISIBLE)
                    }
                    else {
                        binding.textView2.setVisibility(View.INVISIBLE)
                        binding.dessertRv.setVisibility(View.VISIBLE)
                    }
                }

                override fun onFailure(call: Call<KakaoData>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }
}
