package com.example.app.data.mapper

import com.example.app.data.model.ResponseItemDTO
import com.example.app.domain.model.item.ItemEntity

object ItemMapper {
    fun mapperToResponseEntity(item: ResponseItemDTO): ItemEntity {
        return item.run {
            ItemEntity(price, thumbnail, title)
        }
    }
}