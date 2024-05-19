package com.example.app.data.repository

import com.example.app.data.mapper.ItemMapper
import com.example.app.data.remote.RetrofitClient
import com.example.app.data.remote.api.ItemService
import com.example.app.domain.model.item.ItemEntity
import com.example.app.domain.repository.ItemRepository
import org.json.JSONObject

class ItemRepositoryImpl : ItemRepository {
    private val service = RetrofitClient.getInstance().create(ItemService::class.java)

    override suspend fun getItem(searchItem: String): Result<ItemEntity> {
        val response = service.getItem(searchItem)
        return if (response.isSuccessful) {
            Result.success(ItemMapper.mapperToResponseEntity(response.body()!!))
        } else {
            val errorMsg = JSONObject(response.errorBody()!!.string()).getString("msg")
            Result.failure(Exception(errorMsg))
        }
    }
}