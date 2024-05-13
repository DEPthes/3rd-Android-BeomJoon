package com.example.retrofitex.presentation.boxoffice

import com.example.retrofitex.data.SearchDailyBoxOfficeDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BoxOfficeService {
    @GET("searchDailyBoxOfficeList.json")
    fun getMovie(
        @Query("key") key: String,
        @Query("targetDt") targetDt: String,
    ): Call<SearchDailyBoxOfficeDTO>
}