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

        if (intent.getStringExtra("word").toString()=="혜화 카페") {
            list.add(DataDessert(ContextCompat.getDrawable(this, R.drawable.coffee)!!, "학림다방", "서울 종로구 명륜4가 94-2", "02-742-2877"))
            list.add(DataDessert(ContextCompat.getDrawable(this, R.drawable.coffee)!!, "카페키이로", "서울 종로구 명륜4가 154-2", "02-747-0848"))
            list.add(DataDessert(ContextCompat.getDrawable(this, R.drawable.coffee)!!, "칠린", "서울 종로구 명륜4가 167-2", ""))
            list.add(DataDessert(ContextCompat.getDrawable(this, R.drawable.coffee)!!, "블루룸", "서울 종로구 명륜4가 66-3", ""))
            list.add(DataDessert(ContextCompat.getDrawable(this, R.drawable.coffee)!!, "카페혜화동", "서울 종로구 명륜4가 145", "02-6497-3347"))
            list.add(DataDessert(ContextCompat.getDrawable(this, R.drawable.coffee)!!, "혜화역4번출구카페제이드", "서울 종로구 혜화동 136", "02-6083-0601"))
            list.add(DataDessert(ContextCompat.getDrawable(this, R.drawable.coffee)!!, "서화커피", "서울 종로구 명륜4가 140-9", "070-7543-9201"))
            list.add(DataDessert(ContextCompat.getDrawable(this, R.drawable.cake)!!, "스노브 대학로점", "서울 종로구 명륜4가 85-1", ""))
            list.add(DataDessert(ContextCompat.getDrawable(this, R.drawable.coffee)!!, "낫컴플리트", "서울 종로구 명륜2가 150-2", "02-747-3290"))
            list.add(DataDessert(ContextCompat.getDrawable(this, R.drawable.coffee)!!, "브라운에비뉴", "서울 종로구 명륜2가 237", "02-762-8334"))
        }
        else if (intent.getStringExtra("word").toString()=="수원 카페") {
            list.add(DataDessert(ContextCompat.getDrawable(this, R.drawable.coffee)!!, "정지영커피로스터즈 장안문점", "경기 수원시 팔달구 장안동 22-15", "070-7537-0120"))
            list.add(DataDessert(ContextCompat.getDrawable(this, R.drawable.cake)!!, "카페디아즈", "경기 수원시 팔달구 인계동 1119-8", "031-235-0712"))
            list.add(DataDessert(ContextCompat.getDrawable(this, R.drawable.coffee)!!, "책발전소 광교점", "경기 수원시 영통구 원천동 593", "031-548-4522"))
            list.add(DataDessert(ContextCompat.getDrawable(this, R.drawable.coffee)!!, "콜링우드", "경기 수원시 팔달구 북수동 145-1", ""))
            list.add(DataDessert(ContextCompat.getDrawable(this, R.drawable.coffee)!!, "정지영커피로스터즈 화홍문점", "경기 수원시 팔달구 북수동 33-7", ""))
            list.add(DataDessert(ContextCompat.getDrawable(this, R.drawable.coffee)!!, "카페드단아한 광교본점", "경기 수원시 영통구 이의동 1369-2", "031-214-6066"))
            list.add(DataDessert(ContextCompat.getDrawable(this, R.drawable.coffee)!!, "경안당", "경기 수원시 팔달구 장안동 124", "010-3289-3962"))
            list.add(DataDessert(ContextCompat.getDrawable(this, R.drawable.coffee)!!, "행궁맨션", "경기 수원시 팔달구 장안동 53-8", ""))
            list.add(DataDessert(ContextCompat.getDrawable(this, R.drawable.coffee)!!, "카페 도안", "경기 수원시 팔달구 인계동 1118-7", "070-8884-1204"))
            list.add(DataDessert(ContextCompat.getDrawable(this, R.drawable.coffee)!!, "홍라드", "경기 수원시 팔달구 북수동 344-1", "010-9241-3736"))
        }
        else if (intent.getStringExtra("word").toString()=="케이크") {
            list.add(DataDessert(ContextCompat.getDrawable(this, R.drawable.cake)!!, "딩가케이크", "서울 마포구 연남동 252-18", ""))
            list.add(DataDessert(ContextCompat.getDrawable(this, R.drawable.cake)!!, "시루케이크", "서울 마포구 합정동 355-8", "070-4177-7700"))
            list.add(DataDessert(ContextCompat.getDrawable(this, R.drawable.cake)!!, "케이크", "대구 수성구 만촌동 859-13", "053-742-7873"))
            list.add(DataDessert(ContextCompat.getDrawable(this, R.drawable.cake)!!, "메리어케이크", "경기 안양시 만안구 안양동 695-198", ""))
            list.add(DataDessert(ContextCompat.getDrawable(this, R.drawable.cake)!!, "칠요일의케이크", "서울 강남구 청담동 44-17", ""))
            list.add(DataDessert(ContextCompat.getDrawable(this, R.drawable.cake)!!, "맑음케이크 대화점", "경기 고양시 일산서구 대화동 2289-12", ""))
            list.add(DataDessert(ContextCompat.getDrawable(this, R.drawable.cake)!!, "케이크무드", "서울 동작구 상도동 356-12", "010-3774-3524"))
            list.add(DataDessert(ContextCompat.getDrawable(this, R.drawable.cake)!!, "오른케이크", "전북 전주시 완산구 다가동4가 39-2", "063-284-3999"))
            list.add(DataDessert(ContextCompat.getDrawable(this, R.drawable.cake)!!, "봄날애케이크", "경기 의정부시 용현동 35", "010-2939-2424"))
            list.add(DataDessert(ContextCompat.getDrawable(this, R.drawable.cake)!!, "오리올케이크", "서울 용산구 용산동2가 1-40", "02-318-5212"))
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
                    adapter.notifyDataSetChanged()
                }
                override fun onFailure(call: Call<KakaoData>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }
}
