package com.example.retrofitex

import android.util.Log
import com.example.retrofitex.data.DailyBoxOfficeList
import com.example.retrofitex.data.SearchDailyBoxOfficeDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface BoxOfficeRepository {
    suspend fun getMovie(
        targetDt: String
    ): List<DailyBoxOfficeList>

}