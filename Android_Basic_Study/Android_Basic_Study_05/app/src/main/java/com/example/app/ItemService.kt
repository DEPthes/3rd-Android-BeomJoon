package com.example.app

import androidx.room.Query

class ItemService {
    @GET("products/search?q=Laptop")
    fun getItem(@Query(”q”))

}