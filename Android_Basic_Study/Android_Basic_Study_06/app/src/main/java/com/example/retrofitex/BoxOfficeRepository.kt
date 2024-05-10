package com.example.retrofitex

import com.example.retrofitex.data.DailyBoxOfficeList

interface BoxOfficeRepository {
    suspend fun getMovie(
        targetDt: String
    ): List<DailyBoxOfficeList>

}