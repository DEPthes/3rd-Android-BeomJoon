package com.example.retrofitex.data

data class BoxOfficeResult(
    val boxofficeType: String,
    val showRange: String,
    val dailyBoxOfficeList: List<DailyBoxOfficeList>
)
