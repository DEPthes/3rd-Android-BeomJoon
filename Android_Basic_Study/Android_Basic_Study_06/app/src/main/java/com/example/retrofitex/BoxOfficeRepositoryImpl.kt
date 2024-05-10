package com.example.retrofitex

import com.example.retrofitex.data.DailyBoxOfficeList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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