package com.example.app.data.mapper

import com.example.app.data.model.ResponseItemDTO
import com.example.app.domain.model.item.ItemEntity

object ItemMapper {
    fun mapperToResponseEntity(item: ResponseItemDTO): List<ItemEntity> {
        return item.products.map {
            ItemEntity(price = it.price, thumbnail = it.thumbnail, title = it.title)
        }
    }
}