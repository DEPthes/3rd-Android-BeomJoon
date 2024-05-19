package com.example.app.domain.repository

import com.example.app.domain.model.item.ItemEntity

interface ItemRepository {
    suspend fun getItem(searchItem: String): Result<List<ItemEntity>>
}