package com.example.app.data.mapper

import com.example.app.data.model.ResponseItemDTO
import com.example.app.domain.model.item.ResponseItemEntity

object ItemMapper {
    fun mapperToResponseEntity(item: ResponseItemDTO): ResponseItemEntity {
        return item.run {
            ResponseItemEntity(price, thumbnail, title)
        }
    }
}