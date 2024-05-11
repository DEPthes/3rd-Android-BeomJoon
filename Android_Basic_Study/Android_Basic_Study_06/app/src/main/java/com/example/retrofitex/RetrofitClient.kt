package com.example.retrofitex

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var instance: Retrofit? = null
    val TAG = "Log Interceptor"
    private val gson = GsonBuilder().setLenient().create()
    private const val baseUrl = "https://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/"

    fun getInstance(): Retrofit {
        val client = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            try {
                val jsonObject = JSONObject(message).optJSONObject("boxOfficeResult")
                    .optJSONArray("dailyBoxOfficeList")
                for (i in 0 until 5) {
                    val movieObject = jsonObject!!.optJSONObject(i)
                    val rank = movieObject.optString("rank")
                    val movieNm = movieObject.optString("movieNm")
                    val openDt = movieObject.optString("openDt")
                    val audiAcc = movieObject.optString("audiAcc")
                    Log.d("Result", "순위: $rank, 영화 제목: $movieNm, 개봉일: $openDt, 누적 관객수: $audiAcc")
                }
            } catch (e: Exception) {
                Log.d(TAG, message)
            }
        }
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        client.addInterceptor(loggingInterceptor)

        if (instance == null) {
            instance = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build()
        }
        return instance!!
    }
}