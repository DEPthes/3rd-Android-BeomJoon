package com.example.app.data.repository

import android.util.Log
import com.example.app.data.remote.ItemClient
import com.example.app.data.remote.api.ItemService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ItemRepositoryImpl : ItemRepository {
    private val service = ItemClient.getInstance().create(ItemService::class.java)

    override suspend fun getItem(item: String): List<SearchItemList> {
        return withContext(Dispatchers.IO) {
            val call = service.getItem(item).execute()

            if (call.isSuccessful) {
                val response = call.body()
                Log.d("TAG", response.toString())
                response?.map { itemDTO ->
                    SearchItemList(
                        id = itemDTO.id.toString(),
                        title = itemDTO.title,
                        price = itemDTO.price.toString(),
                        thumbnail = itemDTO.thumbnail
                    )
                } ?: emptyList()
            } else {
                emptyList()
            }
        }
    }
}