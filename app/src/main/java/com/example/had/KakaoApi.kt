package com.example.had

import com.example.had.dataclass.KakaoData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

class KakaoApi {
    companion object{
        const val BASE_URL = "https://dapi.kakao.com/"
        const val API_KEY = "KakaoAK 0563ada753bfc38e59b5e5da13b6b8f5"
    }
}

interface KakaoApiService {
    @GET("v2/local/search/keyword.json") // keyword.json의 정보를 받아옴
    fun getkakaoAddress(
        @Header("Authorization") key : String,  // 카카오 API 인증키
        @Query("query") query: String,         // 검색을 원하는 질의어
        @Query("category_group_code") category_group_code: String,
    ): Call<KakaoData>
}
