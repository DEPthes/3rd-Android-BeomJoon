package com.example.retrofitex

import android.util.Log
import com.example.retrofitex.data.DailyBoxOfficeList
import com.example.retrofitex.data.SearchDailyBoxOfficeDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import retrofit2.http.Body

class BoxOfficeRepositoryImpl : BoxOfficeRepository {
    val RestAPIKey = "9f89cd3eb4352ed4638674b75b5f8218"
    private val service = RetrofitClient.getInstnace().create(BoxOfficeService::class.java)

    override suspend fun getMovie(targetDt: String): List<DailyBoxOfficeList> {
        return withContext(Dispatchers.IO) {
            val call = service.getMovie(RestAPIKey, targetDt).execute()

            if (call.isSuccessful) {
                val response = call.body()
                response?.boxOfficeResult?.dailyBoxOfficeList ?: emptyList()
            } else {
                emptyList()
            }
        }
    }
}